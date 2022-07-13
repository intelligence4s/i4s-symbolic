import Dependencies._
import sbt.url

ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.github.intelligence4s"
ThisBuild / organizationName := "Intelligence4s"

ThisBuild / versionScheme := Some("early-semver")

lazy val root = (project in file("."))
  .aggregate(web, server)
  .settings(
    name := "i4s-symbolic",
    licenses := Seq("MIT" -> url("https://www.mit.edu/~amini/LICENSE.md")),
    homepage := Some(url("https://github.com/intelligence4s/i4s-symbolic")),
    pomIncludeRepository := (_ => false),
    scmInfo := Some(ScmInfo(url("https://github.com/intelligence4s/i4s-symbolic"),"scm:git:git@github.com:intelligence4s/i4s-symbolic.git")),
    developers := List(
      Developer(
        id = "thebrenthaines",
        name = "Brent Haines",
        email = "thebrenthaines@yahoo.com",
        url = url("https://github.com/intelligence4s")
      )
    ),
    publishMavenStyle := true
  )

lazy val common = (project in file("common"))
  .settings(
    name := "i4s-symbolic-common",
    resolvers ++= Seq(
      "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
      ("Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release").withAllowInsecureProtocol(true)
    ),
    libraryDependencies ++=
      neo4jDependencies ++
      nlpDependencies ++
      Seq(
        enumeratum,
        logback,
        scalaTest
      )
  )

lazy val server = (project in file("server"))
  .enablePlugins(RevolverPlugin, BuildInfoPlugin, SbtWeb)
  .dependsOn(common % "compile->compile;test->test")
  .settings(
    name := "i4s-symbolic-server",
    fork := true,
    libraryDependencies ++=
      akkaDependencies ++
      sangriaDependencies ++
      Seq(
        akkaHttpJson,
        circe,
        enumeratum,
        logback,
        pureconfig,
        scalaTest
      ),
    Compile / scalacOptions ++= scalacConfig ,
    Compile / javacOptions ++= Seq(
      "-Xlint:unchecked",
      "-Xlint:deprecation",
    ),
    buildInfoKeys := Seq[BuildInfoKey](
      name, version, scalaVersion, sbtVersion,
      libraryDependencies
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.2" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework"),

/*
    scalaJSProjects := Seq(web),
    Assets / pipelineStages := Seq(scalaJSPipeline),
    // triggers scalaJSPipeline when using compile or continuous compilation
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      "com.vmunier" %% "scalajs-scripts" % "1.2.0"
    ),
    Assets / WebKeys.packagePrefix := "public/",
    Runtime / managedClasspath += (Assets / packageBin).value,

*/
    buildInfoOptions += BuildInfoOption.BuildTime,
    buildInfoPackage := "i4s.symbolic.server",
  )

lazy val slinkyVersion = "0.7.2"

lazy val web = (project in file("web"))
  .enablePlugins(BuildInfoPlugin, CalibanPlugin, ScalaJSBundlerPlugin, ScalaJSWeb, ScalaJSPlugin)
  .dependsOn(common)
  .settings(
    name := "i4s-symbolic-web",
    resolvers ++= Seq(
      "jitpack" at "https://jitpack.io"
    ),
    Compile / npmDependencies ++= Seq(
      "history" -> "4.10.1",
      "react" -> "16.13.1",
      "react-dom" -> "16.13.1",
      "react-proxy" -> "1.1.8",
      "react-router-dom" -> "5.2.0",
      "tw-elements" -> "1.0.0-alpha12",
      "@popperjs/core" -> "2.9.1",
      "aws-sdk" -> "2.892.0",
    ),

    Compile / npmDevDependencies ++= Seq(
      "file-loader" -> "6.2.0",
      "style-loader" -> "2.0.0",
      "css-loader" -> "5.2.6",
      "html-webpack-plugin" -> "4.5.1",
      "copy-webpack-plugin" -> "6.4.0",
      "webpack-merge" -> "5.8.0",
      "tailwindcss" -> "2.2.16",
      "postcss" -> "8.4.13",
      "autoprefixer" -> "10.4.7",
      "postcss-loader" -> "4.2.0",
      "tw-elements" -> "1.0.0-alpha12",
      "@popperjs/core" -> "2.9.1",
    ),

    libraryDependencies ++= Seq(
      "me.shadaj" %%% "slinky-core" % slinkyVersion,
      "me.shadaj" %%% "slinky-web" % slinkyVersion,
      "me.shadaj" %%% "slinky-hot" % slinkyVersion,
      "me.shadaj" %%% "slinky-history" % slinkyVersion,
      "me.shadaj" %%% "slinky-react-router" % slinkyVersion,
      "net.exoego" %%% "aws-sdk-scalajs-facade" % "0.33.0-v2.892.0",
      "com.github.ghostdogpr" %%% "caliban-client" % "1.4.0",
      "com.softwaremill.sttp.client3" %%% "core" % "3.5.2",
      "com.softwaremill.sttp.client3" %%% "monix" % "3.5.2",
      "com.github.fdietze.scala-js-d3v4" % "scala-js-d3v4_sjs1_2.12" % "master-SNAPSHOT",
      "org.scalatest" %%% "scalatest" % "3.2.9" % Test
    ),

    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
    Compile / scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlog-reflective-calls",
      "-Xlint"
    ),

    // The `file("Aletheia.graphql")` is a path suffix for some file in `src/main/graphql`
    Compile / caliban / calibanSettings += calibanSetting(file("Aletheia.graphql"))(cs =>
      cs.packageName("i4s.web.graphql")
        .scalarMapping(
          "Uuid" -> "java.util.UUID"
        )
    ),

    webpack / version := "4.44.2",
    startWebpackDevServer / version := "3.11.2",

    webpackResources := baseDirectory.value / "webpack" * "*",
    webpackDevServerPort := 9000,

    fastOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
    fullOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),
    Test / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-core.config.js"),

    fastOptJS / webpackDevServerExtraArgs := Seq("--inline", "--hot"),

    Test / requireJsDomEnv := true,

    addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS"),
    addCommandAlias("build", "fullOptJS::webpack"),

    scalaJSUseMainModuleInitializer := true,
    Compile / mainClass := Some("i4s.symbolic.web.Main"),

    buildInfoKeys := Seq[BuildInfoKey](
      name, version, scalaVersion, sbtVersion,
      libraryDependencies
    ),
    buildInfoOptions += BuildInfoOption.BuildTime,
    buildInfoPackage := "i4s.symbolic"
  )

  val scalacConfig = Seq(
    "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
    "-encoding", "utf-8",                // Specify character encoding used by source files.
    "-explaintypes",                     // Explain type errors in more detail.
    "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
    "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
    "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
    "-language:higherKinds",             // Allow higher-kinded types
    "-language:implicitConversions",     // Allow definition of implicit functions called views
    "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
    "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
    //      "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
    "-Xfuture",                          // Turn on future language features.
    "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
    "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
    "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
    "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
    "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
    "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
    "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
    "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
    "-Xlint:option-implicit",            // Option.apply used implicit view.
    "-Xlint:package-object-classes",     // Class or object defined in package object.
    "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
    "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
    "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
    "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
    "-Xlint:unsound-match",              // Pattern match may not be typesafe.
    "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
    "-Ypartial-unification",             // Enable partial unification in type constructor inference
    "-Ywarn-dead-code",                  // Warn when dead code is identified.
    "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
    "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
    "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
    "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
    "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    "-Ywarn-numeric-widen",              // Warn when numerics are widened.
    "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
    "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
    "-Ywarn-unused:locals",              // Warn if a local definition is unused.
    "-Ywarn-unused:params",              // Warn if a value parameter is unused.
    "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
    "-Ywarn-unused:privates",            // Warn if a private member is unused.
    "-Ywarn-value-discard",               // Warn when non-Unit expression results are unused.
    "-Xlog-reflective-calls",
  )