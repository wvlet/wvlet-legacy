val SCALA_2_13          = "2.13.4"

val AIRFRAME_VERSION    = "21.9.0"
val SCALAJS_DOM_VERSION = "1.2.0"

scalaVersion in ThisBuild := SCALA_2_13

Global / onChangedBuildSource := ReloadOnSourceChanges

val buildSettings = Seq[Setting[_]](
  crossScalaVersions := Seq(SCALA_2_13),
  organization := "org.wvlet.flow",
  description := "A framework for building functional data flows",
  crossPaths := true,
  publishMavenStyle := true,
  Test / logBuffered := false,
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
  sonatypeProfileName := "org.wvlet",
  publishTo := sonatypePublishToBundle.value,
  libraryDependencies ++= Seq(
    "org.wvlet.airframe" %% "airspec" % AIRFRAME_VERSION % "test"
  ),
  testFrameworks += new TestFramework("wvlet.airspec.Framework")
)

lazy val wvlet =
  project
    .in(file("."))
    .settings(
      buildSettings,
      name := "wvlet",
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    ).aggregate(core, api, server, main)

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

lazy val main =
  project
    .in(file("wvlet-main"))
    .settings(
      buildSettings,
      name := "wvlet-main",
      description := "wvlet main module",
      libraryDependencies ++= Seq(
        )
    ).dependsOn(server)

lazy val server =
  project
    .in(file("wvlet-server"))
    .settings(
      buildSettings,
      name := "wvlet-server",
      description := "wvlet server",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %% "airframe-http-finagle" % AIRFRAME_VERSION,
        "org.wvlet.airframe" %% "airframe-launcher"     % AIRFRAME_VERSION
      )
    )
    .dependsOn(core, api)

lazy val api =
    project
    .enablePlugins(BuildInfoPlugin)
    .in(file("wvlet-api"))
    .settings(
      buildSettings,
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoOptions += BuildInfoOption.BuildTime,
      buildInfoPackage := "wvlet.flow.api",
      name := "wvlet-api",
      description := "wvlet API interface and model classes",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe-http" % AIRFRAME_VERSION
      )
    )
