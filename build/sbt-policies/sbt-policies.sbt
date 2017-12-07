// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val `policies-core` = project in file("core")

lazy val `sbt-policies` = (project in file("."))
  .dependsOn(`policies-core`)
  .aggregate(`policies-core`)
  .settings(
    sbtPlugin := true,

    // Dependency management
    addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.3"),
    addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC12"),

    // Templating
    addSbtPlugin("com.typesafe.sbt" % "sbt-twirl" % "1.3.13"),

    // Language integrations
    addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.20"),

    // Development
    addSbtPlugin("me.scf37.overwatch" % "sbt-overwatch" % "1.0.3"),
    addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1"),

    // Packaging
    addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.9.0" exclude ("org.scala-js", "sbt-scalajs"))
  )
