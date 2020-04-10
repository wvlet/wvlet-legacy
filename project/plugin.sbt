addSbtPlugin("org.xerial.sbt"     % "sbt-sonatype"             % "3.9.2")
addSbtPlugin("com.jsuereth"       % "sbt-pgp"                  % "2.0.1")
addSbtPlugin("org.scoverage"      % "sbt-scoverage"            % "1.6.1")
addSbtPlugin("org.scalameta"      % "sbt-scalafmt"             % "2.3.2")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
addSbtPlugin("com.eed3si9n"       % "sbt-buildinfo"            % "0.9.0")

scalacOptions ++= Seq("-deprecation", "-feature")
