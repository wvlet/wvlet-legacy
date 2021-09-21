val SCALA_2_13 = "2.13.6"

val AIRFRAME_VERSION    = "21.9.0"
val SCALAJS_DOM_VERSION = "1.2.0"

ThisBuild / scalaVersion := SCALA_2_13

Global / onChangedBuildSource := ReloadOnSourceChanges

val buildSettings = Seq[Setting[_]](
  crossScalaVersions  := Seq(SCALA_2_13),
  organization        := "org.wvlet.flow",
  description         := "A framework for building functional data flows",
  crossPaths          := true,
  publishMavenStyle   := true,
  Test / logBuffered  := false,
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
  publishTo           := sonatypePublishToBundle.value,
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
      name            := "wvlet",
      publishArtifact := false,
      publish         := {},
      publishLocal    := {}
    ).aggregate(core, api, apiClient, server, main)

lazy val core =
  project
    .in(file("wvlet-core"))
    .settings(
      buildSettings,
      name        := "wvlet-core",
      description := "wvlet core module",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %% "airframe" % AIRFRAME_VERSION
      )
    )

lazy val main =
  project
    .in(file("wvlet-main"))
    .enablePlugins(PackPlugin)
    .settings(
      buildSettings,
      name        := "wvlet-main",
      description := "wvlet main module",
      packMain    := Map("wvlet-server" -> "wvlet.flow.server.ServerMain"),
      libraryDependencies ++= Seq(
      )
    ).dependsOn(server)

lazy val server =
  project
    .in(file("wvlet-server"))
    .settings(
      buildSettings,
      name        := "wvlet-server",
      description := "wvlet server",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %% "airframe-http-grpc" % AIRFRAME_VERSION,
        "org.wvlet.airframe" %% "airframe-launcher"  % AIRFRAME_VERSION
      )
    )
    .dependsOn(core, api, apiClient)

lazy val api =
  project
    .enablePlugins(BuildInfoPlugin)
    .in(file("wvlet-api"))
    .settings(
      buildSettings,
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoOptions += BuildInfoOption.BuildTime,
      buildInfoPackage := "wvlet.flow.api",
      name             := "wvlet-api",
      description      := "wvlet API interface and model classes",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe-http" % AIRFRAME_VERSION
      )
    )

lazy val apiClient =
  project
    .enablePlugins(AirframeHttpPlugin)
    .in(file("wvlet-api-client"))
    .settings(
      buildSettings,
      name        := "wvlet-api-client",
      description := "wvlet API interface client",
      airframeHttpClients := Seq(
        "wvlet.flow.api.internal.coordinator:grpc:CoordinatorGrpc",
        "wvlet.flow.api.internal.worker:grpc:WorkerGrpc",
        "wvlet.flow.api.v1:grpc:WvletGrpc"
      ),
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe-http-grpc" % AIRFRAME_VERSION
      )
    ).dependsOn(api)
