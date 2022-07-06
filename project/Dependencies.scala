import sbt._

object Dependencies {

  lazy val circe = "io.circe" %% "circe-generic" % "0.14.2"

  lazy val enumeratum = "com.beachape" %% "enumeratum" % "1.7.0"

  lazy val akkaDependencies = {
    val akkaVersion = "2.6.8"
    val akkaHttpVersion = "10.2.9"

    Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
    )
  }

  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.6"

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

}
