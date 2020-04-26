addSbtPlugin("org.xerial.sbt"     % "sbt-sonatype"  % "3.9.2")
addSbtPlugin("com.jsuereth"       % "sbt-pgp"       % "2.0.1")
addSbtPlugin("org.scoverage"      % "sbt-scoverage" % "1.6.1")
addSbtPlugin("org.scalameta"      % "sbt-scalafmt"  % "2.3.2")
addSbtPlugin("com.eed3si9n"       % "sbt-buildinfo" % "0.9.0")
addSbtPlugin("org.wvlet.airframe" % "sbt-airframe"  % "20.4.1")
addSbtPlugin("com.dwijnand"       % "sbt-dynver"    % "4.0.0")

// For Scala.js
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % "1.0.1")
addSbtPlugin("ch.epfl.scala"      % "sbt-scalajs-bundler"      % "0.17.0")

libraryDependencies += "org.scala-js" %% "scalajs-env-jsdom-nodejs" % "1.0.0"

addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

scalacOptions ++= Seq("-deprecation", "-feature")
