import sbt._

object Dependencies {

  lazy val circe = "io.circe" %% "circe-generic" % "0.14.2"

  lazy val enumeratum = "com.beachape" %% "enumeratum" % "1.7.0"

  lazy val http4sDependencies = {
    val http4sVersion = "0.23.12"
    Seq(
      "org.http4s"      %% "http4s-ember-server" % http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % http4sVersion,
      "org.http4s"      %% "http4s-circe"        % http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % http4sVersion
    )
  }

  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.6"

  lazy val munitDependencies = {
    val munitVersion = "0.7.29"
    val munitCatsEffectVersion = "1.0.7"

    Seq(
    "org.scalameta" %% "munit" % munitVersion % Test,
    "org.typelevel" %% "munit-cats-effect-3" % munitCatsEffectVersion % Test,
    )
  }


  lazy val neo4jDependencies = Seq(
  "org.neo4j.driver" % "neo4j-java-driver" % "4.3.4",
  "io.github.neo4s" %% "neo4s-cypher-dsl" % "0.5.0-SNAPSHOT",
  "io.github.neo4s" %% "neo4s-query-support" % "0.1.0-SNAPSHOT"
  )

  lazy val nlpDependencies = {
    val procVer = "8.4.6"
    Seq(
    "org.clulab" %% "processors-main" % procVer,
    "org.clulab" %% "processors-corenlp" % procVer,
    "edu.stanford.nlp" % "stanford-corenlp" % "4.3.1",
    "edu.stanford.nlp" % "stanford-corenlp" % "4.3.1" classifier "models"
    )
  }

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9" % Test

  lazy val zioDependencies = {
    val zioVersion = "1.0.13"
    val zioConfigVersion = "2.0.4"

    Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-config" % zioConfigVersion,
      //      "dev.zio" %% "zio-config-typesafe" % zioConfigVersion,
      "dev.zio" %% "zio-test" % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
    )
  }

}
