// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val core = crossProject
  .crossType(CrossType.Full)
  .in(file("."))

lazy val coreJVM = core.jvm
lazy val coreJS = core.js
