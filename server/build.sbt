import Dependencies._

name := "i4s-symbolic-server"

fork := true

libraryDependencies ++=
  akkaDependencies ++
    sangriaDependencies ++
    Seq(
      akkaHttpJson,
      circe,
      logback,
      pureconfig,
      scalaTest
    )

Compile / scalacOptions ++= Seq(
  "-target:11",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint"
)

Compile / javacOptions ++= Seq(
  "-Xlint:unchecked",
  "-Xlint:deprecation"
)
buildInfoKeys := Seq[BuildInfoKey](
  name, version, scalaVersion, sbtVersion,
  libraryDependencies
)
addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full)
addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")
testFrameworks += new TestFramework("munit.Framework")

/*
scalaJSProjects := Seq(web)

Assets / pipelineStages := Seq(scalaJSPipeline)
// triggers scalaJSPipeline when using compile or continuous compilation

Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value

libraryDependencies ++= Seq(
  "com.vmunier" %% "scalajs-scripts" % "1.2.0"
)

Assets / WebKeys.packagePrefix := "public/"

Runtime / managedClasspath += (Assets / packageBin).value

*/

buildInfoOptions += BuildInfoOption.BuildTime
buildInfoPackage := "i4s.symbolic.server"
