val SCALA_2_13 = "2.13.8"
val SCALA_3    = "3.3.1"

val AIRFRAME_VERSION    = sys.env.getOrElse("AIRFRAME_VERSION", "23.9.3")
val AIRSPEC_VERSION     = "23.9.3"
val SCALAJS_DOM_VERSION = "2.4.0"
val TRINO_VERSION       = "427"

ThisBuild / scalaVersion := SCALA_3
ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")

Global / onChangedBuildSource := ReloadOnSourceChanges

val buildSettings = Seq[Setting[_]](
  organization        := "org.wvlet.lang",
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
    "org.wvlet.airframe" %% "airspec" % AIRSPEC_VERSION % Test
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
    ).aggregate(lang, core, api.jvm, api.js, apiClient, server, plugin, main)

lazy val lang =
  project
    .in(file("wvlet-lang"))
    .settings(
      buildSettings,
      name        := "wvlet-lang",
      description := "wvlet language",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %% "airframe-log" % AIRFRAME_VERSION,
        "org.wvlet.airframe" %% "airframe-sql" % AIRFRAME_VERSION,
        // Add a reference implementation of the compiler
        "org.scala-lang" %% "scala3-compiler" % SCALA_3 % Test
      )
    )

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
    .dependsOn(api.jvm)

lazy val main =
  project
    .in(file("wvlet-main"))
    .enablePlugins(PackPlugin)
    .settings(
      buildSettings,
      name        := "wvlet-main",
      description := "wv command line interface",
      packMain    := Map("wv" -> "wvlet.lang.cli.WvletMain"),
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %% "airframe-launcher" % AIRFRAME_VERSION
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
        "org.wvlet.airframe" %% "airframe-http-netty" % AIRFRAME_VERSION,
        "org.wvlet.airframe" %% "airframe-launcher"   % AIRFRAME_VERSION,
        // Add DuckDB test
        "org.duckdb" % "duckdb_jdbc" % "0.9.0"
      ),
      Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat
    )
    .dependsOn(core, plugin, api.jvm, apiClient, plugin)

lazy val plugin =
  project
    .in(file("wvlet-plugin"))
    .settings(
      buildSettings,
      name        := "wvlet-plugin",
      description := "wvlet standard plugins",
      libraryDependencies ++= Seq(
        // For Trino plugin
        "io.trino"            % "trino-cli"     % TRINO_VERSION,
        "io.trino"            % "trino-client"  % TRINO_VERSION,
        "io.trino"            % "trino-jdbc"    % TRINO_VERSION,
        "io.trino"            % "trino-spi"     % TRINO_VERSION,
        "org.wvlet.airframe" %% "airframe-jdbc" % AIRFRAME_VERSION,
        "org.xerial.snappy"   % "snappy-java"   % "1.1.10.5"
      )
    )
    .dependsOn(api.jvm, core)

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
      name             := "wvlet-api",
      description      := "wvlet API interface and model classes",
      libraryDependencies ++= Seq(
        "org.wvlet.airframe" %%% "airframe-http"    % AIRFRAME_VERSION,
        "org.wvlet.airframe" %%% "airframe-metrics" % AIRFRAME_VERSION
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
        "wvlet.dataflow.api.internal.coordinator:rpc:CoordinatorRPC",
        "wvlet.dataflow.api.internal.worker:rpc:WorkerRPC",
        "wvlet.dataflow.api.v1:rpc:WvletRPC"
      ),
      airframeHttpVersion := AIRFRAME_VERSION
    ).dependsOn(api.jvm)

import org.scalajs.linker.interface.ModuleSplitStyle

val publicDev  = taskKey[String]("output directory for `npm run dev`")
val publicProd = taskKey[String]("output directory for `npm run build`")

lazy val ui = project
  .in(file("wvlet-ui"))
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .enablePlugins(AirframeHttpPlugin)
  .settings(
    buildSettings,
    airframeHttpClients := Seq(
      "wvlet.dataflow.api.frontend:rpc:FrontendRPC"
    ),
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      linkerConfig(_)
    },
    Compile / fullLinkJS / scalaJSLinkerConfig ~= {
      linkerConfig(_)
        // Workaround for the error:
        // Surfaces.scala(235:12:Return): java.io.Serializable expected but java.lang.Class found for tree of type org.scalajs.ir.Trees$Apply
        .withCheckIR(false)
    },
    Test / fullLinkJS / scalaJSLinkerConfig ~= {
      linkerConfig(_)
        .withCheckIR(false)
    },
    externalNpm := {
      import java.nio.file.{Files, Paths}
      val yarnProg = Files.exists(Paths.get("/opt/homebrew/bin/yarn")) match {
        // Workaround for IntelliJ sbt project loader on Mac OS X
        case true  => "/opt/homebrew/bin/yarn"
        case false => "yarn"
      }
      // Use Yarn instead of npm
      scala.sys.process.Process(List(yarnProg, "--silent"), baseDirectory.value).!
      baseDirectory.value
    },
    libraryDependencies ++= Seq(
      "org.wvlet.airframe" %%% "airframe"         % AIRFRAME_VERSION,
      "org.wvlet.airframe" %%% "airframe-http"    % AIRFRAME_VERSION,
      "org.wvlet.airframe" %%% "airframe-rx-html" % AIRFRAME_VERSION
    ),
    publicDev  := s"target/scala-${scalaVersion.value}/ui-fastopt",
    publicProd := s"target/scala-${scalaVersion.value}/ui-opt"
    // Adding a build step here didn't work well with 'yarn build'
    // publicProd := linkerOutputDirectory((Compile / fullLinkJS).value).relativeTo(baseDirectory.value).get.getPath()
  )
  .dependsOn(api.js)

import org.scalajs.linker.interface.{StandardConfig, OutputPatterns}
def linkerConfig(config: StandardConfig): StandardConfig = {
  config
    .withSourceMap(true)
    .withModuleKind(ModuleKind.ESModule)
    .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("wvlet.dataflow.ui")))
}

def linkerOutputDirectory(v: Attributed[org.scalajs.linker.interface.Report]): File = {
  v.get(scalaJSLinkerOutputDirectory.key).getOrElse {
    throw new MessageOnlyException(
      "Linking report was not attributed with output directory. " +
        "Please report this as a Scala.js bug."
    )
  }
}
