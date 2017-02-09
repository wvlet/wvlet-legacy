import ReleaseTransformations._

scalaVersion in ThisBuild := "2.12.1"

val buildSettings = Seq[Setting[_]](
  crossScalaVersions := Seq("2.11.8", "2.12.1"),
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
  ).aggregate(wvletCore, wvletTest)

val wvletLog = "org.wvlet" %% "wvlet-log" % "1.1"

lazy val wvletCore =
  Project(id = "wvlet-core", base = file("wvlet-core")).settings(
    buildSettings,
    description := "wvlet core module",
    libraryDependencies ++= Seq(
      wvletLog,
      "org.wvlet" %% "object-schema" % "1.0",
      "org.msgpack" % "msgpack-core" % "0.8.11",
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.scala-lang" % "scalap" % scalaVersion.value,
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
    )
  ).dependsOn(wvletTest % "test->compile")

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
