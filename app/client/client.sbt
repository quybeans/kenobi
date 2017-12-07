// Copyright (C) 2016-2017 Ark Maxim, Inc.

lazy val client = (project in file("."))
  .settings(
    libraryDependencies ++= Seq(
      %%%("scalajs-react-core"),
      %%%("scalajs-react-extra"),
      %%%("circe-generic"),
      %%%("circe-generic-extras"),
      %%%("circe-parser")
    ),

    scalacOptions ++= Seq(
      "-P:scalajs:sjsDefinedByDefault"
    ),

    emitSourceMaps := false,

    npmDependencies in Compile ++= Seq(
      npm("antd"),
      npm("react"),
      npm("react-apollo"),
      npm("react-dom"),
      npm("tachyons"),
      npm("moment")
    ),

    npmDevDependencies in Compile ++= Seq(
      npm("css-loader"),
      npm("extract-text-webpack-plugin"),
      npm("less"),
      npm("less-loader"),
      npm("style-loader")
    ),

    version in webpack := v("webpack"),
    webpackBundlingMode := BundlingMode.LibraryOnly(),
    webpackConfigFile in fastOptJS := Some(sourceDirectory.value / "webpack" / "client-fastopt-webpack-config.js"),
    webpackEmitSourceMaps := false,

    version in startWebpackDevServer := v("webpack-dev-server"),
    webpackDevServerPort in fastOptJS := 6789,
    webpackDevServerExtraArgs in fastOptJS := Seq("--inline", "--hot")
  )
  .enablePlugins(
    ScalaJSBundlerPlugin
  )
