import sbt._

object Dependencies {
  lazy val akkaVersion = "2.6.17"
  val akkaHttpVersion = "10.2.7"

  lazy val akka = "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  lazy val akkaTest = "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.6"
  lazy val neo4jDriver = "org.neo4j.driver" % "neo4j-java-driver" % "4.3.4"
  lazy val neo4sCypherDsl = "io.github.neo4s" %% "neo4s-cypher-dsl" % "0.5.0-SNAPSHOT"
  lazy val neo4sQuerySupport = "io.github.neo4s" %% "neo4s-query-support" % "0.1.0-SNAPSHOT"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9" % Test

}
