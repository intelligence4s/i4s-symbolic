import Dependencies._

name := "i4s-symbolic-visual"

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

javaCppVersion := "1.5.8-SNAPSHOT"
javaCppPresetLibs ++= Seq("opencv" -> "4.6.0", "ffmpeg" -> "5.1")

fork := true

libraryDependencies ++=
  Seq(
    "org.bytedeco" % "javacv-platform" % "1.5.8-SNAPSHOT",
//    javaCV,
    scalaTest
  )
