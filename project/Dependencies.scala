import sbt._

object Dependencies {

  lazy val zHttpDependencies = {
    val zHttpVersion = "1.0.0.0-RC27"

    Seq(
      "io.d11" %% "zhttp" % zHttpVersion,
      "io.d11" %% "zhttp-test" % zHttpVersion % Test,
    )
  }

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

  lazy val enumeratum = "com.beachape" %% "enumeratum" % "1.7.0"

  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.6"

  lazy val neo4jDependencies = Seq(
    "org.neo4j.driver" % "neo4j-java-driver" % "4.3.4",
    "io.github.neo4s" %% "neo4s-cypher-dsl" % "0.5.0-SNAPSHOT",
    "io.github.neo4s" %% "neo4s-query-support" % "0.1.0-SNAPSHOT"
  )

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.9" % Test

  lazy val nlpDependencies = {
    val procVer = "8.4.6"
    Seq(
      "org.clulab" %% "processors-main" % procVer,
      "org.clulab" %% "processors-corenlp" % procVer,
      "edu.stanford.nlp" % "stanford-corenlp" % "4.3.1",
      "edu.stanford.nlp" % "stanford-corenlp" % "4.3.1" classifier "models"
    )
  }

}
