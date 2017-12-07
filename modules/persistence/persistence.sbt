// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val coreJVM = LocalProject("coreJVM")
lazy val utilitiesJVM = LocalProject("utilitiesJVM")

lazy val persistence = (project in file("."))
  .dependsOn(
    coreJVM,
    utilitiesJVM
  )
  .settings(
    libraryDependencies ++= Seq(
      %("flyway-core"),

      %("postgresql"),

      %%("doobie-core"),
      %%("doobie-postgres")
    )
  )
