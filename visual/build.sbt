import Dependencies._

name := "i4s-symbolic-visual"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

javaCppVersion := "1.5.8-SNAPSHOT"
javaCppPresetLibs ++= Seq("opencv" -> "4.6.0", "ffmpeg" -> "5.1")

fork := true

libraryDependencies ++=
  neo4jDependencies ++
  Seq(
    i4sOpenCV,
    "org.scala-lang" % "scala-reflect" % scalaV,
    scalaTest
  )
