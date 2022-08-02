import Dependencies._

name := "i4s-symbolic-nlp"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
  ("Artifactory" at "http://artifactory.cs.arizona.edu:8081/artifactory/sbt-release").withAllowInsecureProtocol(true)
)

libraryDependencies ++=
  neo4jDependencies ++
    nlpDependencies ++
    Seq(
      logback,
      scalaTest
    )
