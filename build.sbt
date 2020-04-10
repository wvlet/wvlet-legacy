val SCALA_2_12 = "2.12.11"
val SCALA_2_13 = "2.13.1"

val AIRFRAME_VERSION = "20.4.0"

scalaVersion in ThisBuild := SCALA_2_12

val buildSettings = Seq[Setting[_]](
  crossScalaVersions := Seq(SCALA_2_12, SCALA_2_13),
  organization := "org.wvlet",
  description := "A framework for managing data flows",
  crossPaths := true,
  publishMavenStyle := true,
  logBuffered in Test := false,
  updateOptions := updateOptions.value.withCachedResolution(true),
  sonatypeProfileName := "org.wvlet",
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://wvlet.org/wvlet")),
  scmInfo := Some(
    ScmInfo(
      browseUrl = url("https://github.com/wvlet/wvlet"),
      connection = "scm:git@github.com:wvlet/wvlet.git"
    )
  ),
  developers := List(
    Developer(id = "leo", name = "Taro L. Saito", email = "leo@xerial.org", url = url("http://xerial.org/leo"))
  ),
  publishTo := sonatypePublishToBundle.value,
  libraryDependencies ++= Seq(
    "org.wvlet.airframe" %% "airspec" % AIRFRAME_VERSION % "test"
  ),
  testFrameworks += new TestFramework("wvlet.airspec.Framework")
)

lazy val projectJVM = project.aggregate(core)
lazy val projectJS  = project.aggregate()

lazy val wvlet =
  project
    .in(file("."))
    .settings(
      buildSettings,
      name := "wvlet",
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    ).aggregate(core)

lazy val core =
  project
    .in(file("wvlet-core"))
    .settings(
      buildSettings,
      name := "wvlet-core",
      description := "wvlet core module",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %% "airframe" % AIRFRAME_VERSION
      )
    )
