// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val persistence = LocalProject("persistence")

lazy val server = (project in file("."))
  .dependsOn(persistence)
  .settings(
    libraryDependencies ++= Seq(
      compilerPlugin(%("scalameta-paradise") cross CrossVersion.full),

      %("slf4j-simple"),

      %%("pureconfig"),

      %%("frees"),

      %%("circe-generic"),

      %%("sangria"),
      %%("sangria-circe"),

      %%("akka-http"),
      %%("akka-http-circe")
    ),

    envVars in Compile ++= Minikube.Development.envVars,
    envVars in reStart ++= Minikube.Development.envVars,
    envVars in Test ++= Minikube.Test.envVars,

    mainClass in Compile := Some("uniq.server.Server"),

    reboot := {
      reStart.toTask("").value
    }
  )
  .enablePlugins(SbtTwirl)
