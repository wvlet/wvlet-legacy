val SCALA_2_12 = "2.12.11"
val SCALA_2_13 = "2.13.1"

val AIRFRAME_VERSION = "20.4.1"

scalaVersion in ThisBuild := SCALA_2_12

Global / onChangedBuildSource := ReloadOnSourceChanges

val buildSettings = Seq[Setting[_]](
  crossScalaVersions := Seq(SCALA_2_12, SCALA_2_13),
  organization := "org.wvlet.dataflow",
  description := "A framework for managing data flows",
  crossPaths := true,
  publishMavenStyle := true,
  logBuffered in Test := false,
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

lazy val projectJVM = project.aggregate(core, server, main, apiJVM)
lazy val projectJS  = project.aggregate(apiJS, ui)

lazy val wvlet =
  project
    .in(file("."))
    .settings(
      buildSettings,
      name := "wvlet",
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    ).aggregate(core, apiJVM, apiJS, server, main, ui)

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
    .dependsOn(core, apiJVM)

lazy val api =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .enablePlugins(BuildInfoPlugin)
    .in(file("wvlet-api"))
    .settings(
      buildSettings,
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoOptions += BuildInfoOption.BuildTime,
      buildInfoPackage := "wvlet.dataflow.api",
      name := "wvlet-api",
      description := "wvlet API interface and model classes",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe-http" % AIRFRAME_VERSION
      )
    )

lazy val apiJVM = api.jvm
lazy val apiJS  = api.js

lazy val ui =
  project
    .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, AirframeHttpPlugin)
    .in(file("wvlet-ui"))
    .settings(
      buildSettings,
      name := "wvlet-ui",
      description := "wvlet web UI",
      airframeHttpClients := Seq("wvlet.dataflow.api:scalajs"),
      airframeHttpGeneratorOption := "-l debug",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe"         % AIRFRAME_VERSION,
        "org.wvlet.airframe" %%% "airframe-http-rx" % AIRFRAME_VERSION,
        "org.scala-js"       %%% "scalajs-dom"      % "1.0.0"
      ),
      scalaJSUseMainModuleInitializer := true,
      webpackConfigFile := Some(baseDirectory.value / "webpack.config.js"),
      npmDependencies in Compile += "monaco-editor" -> "0.20.0",
      npmDevDependencies in Compile ++= Seq(
        "import-loader"                -> "1.0.1",
        "expose-loader"                -> "0.7.5",
        "style-loader"                 -> "1.1.3",
        "file-loader"                  -> "5.1.0",
        "css-loader"                   -> "3.4.2",
        "monaco-editor-webpack-plugin" -> "1.9.0",
        "webpack-merge"                -> "4.2.2"
      ),
      useYarn := true,
      webpackEmitSourceMaps := false,
      webpackBundlingMode := BundlingMode.LibraryOnly()
    )
    .dependsOn(apiJS)
