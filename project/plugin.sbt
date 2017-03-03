addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.0")
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.7")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-M14-7")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.1.0")

// Dependency tree check
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

// For web application
addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

// For Scala.js
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.14")
addSbtPlugin("com.vmunier" % "sbt-web-scalajs" % "1.0.3")
addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.3.0")
scalacOptions ++= Seq("-deprecation", "-feature")
