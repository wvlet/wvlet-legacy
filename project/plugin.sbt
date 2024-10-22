// Ignore binary incompatible errors for libraries using scala-xml.
// sbt-scoverage upgraded to scala-xml 2.1.0, but other sbt-plugins and Scala compilier 2.12 uses scala-xml 1.x.x
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % "always"

// ThisBuild / resolvers ++= Resolver.sonatypeOssRepos("snapshots")
val AIRFRAME_VERSION = sys.env.getOrElse("AIRFRAME_VERSION", "24.8.0")

addSbtPlugin("org.xerial.sbt"     % "sbt-sonatype"             % "3.11.3")
addSbtPlugin("com.github.sbt"     % "sbt-pgp"                  % "2.2.1")
addSbtPlugin("org.scoverage"      % "sbt-scoverage"            % "2.1.1")
addSbtPlugin("org.scalameta"      % "sbt-scalafmt"             % "2.5.2")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.3.2")
addSbtPlugin("com.eed3si9n"       % "sbt-buildinfo"            % "0.12.0")
addSbtPlugin("org.wvlet.airframe" % "sbt-airframe"             % AIRFRAME_VERSION)

// For generating Lexer/Parser from ANTLR4 grammar (.g4)
// addSbtPlugin("com.simplytyped" % "sbt-antlr4" % "0.8.3")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.10.0")

addDependencyTreePlugin

// For Scala.js
val SCALAJS_VERSION = sys.env.getOrElse("SCALAJS_VERSION", "1.16.0")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % SCALAJS_VERSION)
libraryDependencies ++= (
  Seq("org.scala-js" %% "scalajs-env-jsdom-nodejs" % "1.1.0")
)
addSbtPlugin("org.scalablytyped.converter" % "sbt-converter" % "1.0.0-beta44")

// For setting explicit versions for each commit
addSbtPlugin("com.github.sbt" % "sbt-dynver" % "5.0.1")

// Documentation
addSbtPlugin("org.scalameta" % "sbt-mdoc" % "2.5.4")

addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.20")

scalacOptions ++= Seq("-deprecation", "-feature")
