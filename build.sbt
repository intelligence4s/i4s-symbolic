import sbt.url

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.github.intelligence4s"
ThisBuild / organizationName := "Intelligence4s"

ThisBuild / versionScheme := Some("early-semver")

lazy val root = (project in file("."))
  .aggregate(web, server, nlp, visual)
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

lazy val common = crossProject(JSPlatform, JVMPlatform)
  .withoutSuffixFor(JVMPlatform)
  .in(file("common"))
  .settings(
    name := "i4s-symbolic-common"
  )
  .jvmSettings(libraryDependencies += "org.scala-js" %% "scalajs-stubs" % "1.1.0" % "provided")
  .jsSettings()

lazy val visual = (project in file("visual"))
  .dependsOn(common.jvm)

lazy val nlp = (project in file("nlp"))
  .dependsOn(common.jvm)

lazy val server = (project in file("server"))
  .enablePlugins(RevolverPlugin, BuildInfoPlugin, SbtWeb)
  .dependsOn(common.jvm, nlp % "compile->compile;test->test")

lazy val web = (project in file("web"))
  .enablePlugins(BuildInfoPlugin, CalibanPlugin, ScalaJSBundlerPlugin, ScalaJSWeb, ScalaJSPlugin)
  .dependsOn(common.js)
