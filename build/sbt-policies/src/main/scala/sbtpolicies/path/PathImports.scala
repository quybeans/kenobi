// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.path

// scalastyle:off underscore.import
import sbt._
// scalastyle:on underscore.import

private[sbtpolicies] trait PathImports {

  object Paths {
    lazy val build = file("build")
    lazy val modules = file("modules")
    lazy val app = file("app")

    lazy val config = file("config")
    lazy val appConfig: File = config / "application.conf"
  }
}
