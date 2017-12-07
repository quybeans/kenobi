// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val `sbt-policies` = RootProject(file("../build/sbt-policies"))
lazy val `uniq-build` = (project in file(".")).dependsOn(`sbt-policies`)
