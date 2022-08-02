import Dependencies._

name := "i4s-symbolic-web"

resolvers ++= Seq(
  "jitpack" at "https://jitpack.io"
)

Compile / npmDependencies ++= Seq(
  "history" -> "4.10.1",
  "react" -> "16.13.1",
  "react-dom" -> "16.13.1",
  "react-proxy" -> "1.1.8",
  "react-router-dom" -> "5.2.0",
  "tw-elements" -> "1.0.0-alpha12",
  "@popperjs/core" -> "2.9.1",
  "aws-sdk" -> "2.892.0"
)


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
  "@popperjs/core" -> "2.9.1"
)


lazy val slinkyVersion = "0.7.2"

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
  "com.github.fdietze.scala-js-d3v4" %% "scala-js-d3v4_sjs1" % "master-SNAPSHOT",
  "org.scalatest" %%% "scalatest" % "3.2.9" % Test
)


scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))

Compile / scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint"
)

// The `file("Symbolic.graphql")` is a path suffix for some file in `src/main/graphql`
Compile / caliban / calibanSettings += calibanSetting(file("Symbolic.graphql"))(cs =>
  cs.packageName("i4s.symbolic.web.graphql")
    .scalarMapping(
      "Uuid" -> "java.util.UUID"
    )
)

webpack / version := "4.44.2"
startWebpackDevServer / version := "3.11.2"

webpackResources := baseDirectory.value / "webpack" * "*"
webpackDevServerPort := 9000

fastOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-fastopt.config.js")
fullOptJS / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-opt.config.js")
Test / webpackConfigFile := Some(baseDirectory.value / "webpack" / "webpack-core.config.js")

fastOptJS / webpackDevServerExtraArgs := Seq("--inline", "--hot")

Test / requireJsDomEnv := true

addCommandAlias("dev", ";fastOptJS::startWebpackDevServer;~fastOptJS")
addCommandAlias("build", "fullOptJS::webpack")

scalaJSUseMainModuleInitializer := true
Compile / mainClass := Some("i4s.symbolic.web.Main")

buildInfoKeys := Seq[BuildInfoKey](
  name, version, scalaVersion, sbtVersion,
  libraryDependencies
)
buildInfoOptions += BuildInfoOption.BuildTime
buildInfoPackage := "i4s.symbolic"
