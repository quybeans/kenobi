// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val utilities = crossProject
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      %%("circe-generic-extras")
    )
  )
  .jvmSettings(
    libraryDependencies ++= Seq(
      %%("cats-effect"),
      %%("scalaz-concurrent")
    )
  )

lazy val utilitiesJVM = utilities.jvm
lazy val utilitiesJS = utilities.js
