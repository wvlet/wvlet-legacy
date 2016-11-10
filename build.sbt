import ReleaseTransformations._


val buildSettings = Seq[Setting[_]](
  scalaVersion := "2.12.0",
  crossScalaVersions := Seq("2.11.8", "2.12.0"),
  organization := "org.wvlet",
  description := "A framework for structured data mapping",
  crossPaths := true,
  publishMavenStyle := true,
  // For performance testing, ensure each test run one-by-one
  concurrentRestrictions in Global := Seq(Tags.limit(Tags.Test, 1)),
  incOptions := incOptions.value.withNameHashing(true),
  logBuffered in Test := false,
  updateOptions := updateOptions.value.withCachedResolution(true),
  sonatypeProfileName := "org.wvlet",
  pomExtra := {
  <url>https://github.com/xerial/wvlet</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/wvlet/wvlet.git</connection>
      <developerConnection>scm:git:git@github.com:wvlet/wvlet.git</developerConnection>
      <url>github.com/wvlet/wvlet.git</url>
    </scm>
    <developers>
      <developer>
        <id>leo</id>
        <name>Taro L. Saito</name>
        <url>http://xerial.org/leo</url>
      </developer>
    </developers>
  },
  // Use sonatype resolvers
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  // Release settings
  releaseTagName := { (version in ThisBuild).value },
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    ReleaseStep(action = Command.process("publishSigned", _), enableCrossBuild = true),
    setNextVersion,
    commitNextVersion,
    ReleaseStep(action = Command.process("sonatypeReleaseAll", _), enableCrossBuild = true),
    pushChanges
  ),
  releaseCrossBuild := true
)

lazy val wvlet =
  Project(id = "wvlet", base = file(".")).settings(
    buildSettings,
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  ).aggregate(wvletCore, wvletObj, wvletOpts, wvletTest, wvletConfig, wvletJmx)

val wvletLog = "org.wvlet" %% "wvlet-log" % "1.1"

lazy val wvletJmx =
  Project(id = "wvlet-jmx", base = file("wvlet-jmx")).settings(
    buildSettings,
    description := "A library for exposing Scala object data through JMX",
    libraryDependencies ++= Seq(
      wvletLog,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  ).dependsOn(wvletObj, wvletTest % "test->compile")

lazy val wvletConfig =
  Project(id = "wvlet-config", base = file("wvlet-config")).settings(
    buildSettings,
    description := "wvlet configuration module",
    libraryDependencies ++= Seq(
      "org.yaml" % "snakeyaml" % "1.14"
      //"org.wvlet" %% "airframe" % "0.8-SNAPSHOT" % "test"
    )
  ).dependsOn(wvletObj, wvletTest % "test->compile")

lazy val wvletCore =
  Project(id = "wvlet-core", base = file("wvlet-core")).settings(
    buildSettings,
    description := "wvlet core module",
    libraryDependencies ++= Seq(
      wvletLog,
      "org.msgpack" % "msgpack-core" % "0.8.7",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
    )
  ).dependsOn(wvletObj, wvletTest % "test->compile")

lazy val wvletObj =
  Project(id = "wvlet-obj", base = file("wvlet-obj")).settings(
    buildSettings,
    description := "wvlet object schema inspector",
    libraryDependencies ++= Seq(
      wvletLog,
      "org.scala-lang" % "scalap" % scalaVersion.value,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value
    )
  ).dependsOn(wvletTest % "test->compile")

lazy val wvletOpts =
  Project(id = "wvlet-opts", base = file("wvlet-opts")).settings(
    buildSettings,
    description := "wvlet command-line option parser",
    libraryDependencies ++= Seq(
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
    )
  ) dependsOn(wvletObj, wvletTest % "test->compile")

lazy val wvletTest =
  Project(id = "wvlet-test", base = file("wvlet-test")).settings(
    buildSettings,
    description := "wvlet testing module",
    libraryDependencies ++= Seq(
      wvletLog,
      "org.scalatest" %% "scalatest" % "3.0.0",
      "org.scalacheck" %% "scalacheck" % "1.12.6"
    )
  )