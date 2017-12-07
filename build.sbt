// Copyright (C) 2016-2017 Ark Maxim, Inc.

// Build projects

lazy val `sbt-policies` = project in (Paths.build / "sbt-policies")

lazy val buildProjects: List[ProjectReference] = List(
  `sbt-policies`
)

// Module projects

lazy val coreJVM = project in (Paths.modules / "core")
lazy val coreJS = project in (Paths.modules / "core")

lazy val utilitiesJVM = project in (Paths.modules / "utilities")
lazy val utilitiesJS = project in (Paths.modules / "utilities")

lazy val persistence = project in (Paths.modules / "persistence")

lazy val moduleProjects: List[ProjectReference] = List(
  coreJVM, coreJS,
  utilitiesJVM, utilitiesJS,
  persistence
)

// App projects

lazy val server = project in (Paths.app / "server")
lazy val client = project in (Paths.app / "client")

lazy val appProjects: List[ProjectReference] = List(
  server,
  client
)

lazy val allProjects = buildProjects ++
  moduleProjects ++
  appProjects

aggregateProjects(allProjects: _*)

(overwatch in Global) := {
  (reStart in server).toTask("").value
  (overwatch in Global).value
  spray.revolver.Actions.stopAppWithStreams(streams.value, (thisProjectRef in server).value)
}

overwatchConfiguration in Global := Map(
  overwatchFilter((sourceDirectory in Compile in client).value, "**/*.scala") -> (fastOptJS in Compile in client),
  overwatchFilter((sourceDirectory in Compile in server).value, "**/*.{scala,html}") -> (reboot in server)
)

addCommandAlias(
  "migrate",
  "server/runMain uniq.persistence.migration.Migration"
)

addCommandAlias(
  "dev",
  ";migrate;client/fastOptJS::startWebpackDevServer;overwatch"
)
