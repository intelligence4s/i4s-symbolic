import Dependencies._
import sbt.url

ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.github.intelligence4s"
ThisBuild / organizationName := "Intelligence4s"

ThisBuild / versionScheme := Some("early-semver")

lazy val root = (project in file("."))
  .aggregate(web, common)
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


lazy val slinkyVersion = "0.7.2"

lazy val web = (project in file("web"))
  .enablePlugins(BuildInfoPlugin, CalibanPlugin, ScalaJSBundlerPlugin, ScalaJSWeb, ScalaJSPlugin)
  .settings(
    name := "i4s-symbolic-web",
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
      "org.scalatest" %%% "scalatest" % "3.2.9" % Test
    ),

    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),

    Compile / scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-target:11",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlog-reflective-calls",
      "-Xlint"
    ),

    // The `file("Aletheia.graphql")` is a path suffix for some file in `src/main/graphql`
    Compile / caliban / calibanSettings += calibanSetting(file("Aletheia.graphql"))(cs =>
      cs.packageName("transparency.web.graphql")
        .scalarMapping(
          "Uuid" -> "java.util.UUID"
        )
    ),

    webpack / version := "4.44.2",
    startWebpackDevServer / version := "3.11.2",

    webpackResources := baseDirectory.value / "webpack" * "*",
    webpackDevServerPort := 8000,

    fastOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js"),
    fullOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js"),
    Test / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-core.config.js"),

    fastOptJS / webpackDevServerExtraArgs := Seq("--inline", "--hot"),

    Test / requireJsDomEnv := true,

    addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS"),
    addCommandAlias("build", "fullOptJS::webpack"),

    scalaJSUseMainModuleInitializer := true,
    Compile / mainClass := Some("transparency.web.Main"),

    buildInfoKeys := Seq[BuildInfoKey](
      name, version, scalaVersion, sbtVersion,
      libraryDependencies
    ),
    buildInfoOptions += BuildInfoOption.BuildTime,
    buildInfoPackage := "transparency.aletheia.web"
  )
