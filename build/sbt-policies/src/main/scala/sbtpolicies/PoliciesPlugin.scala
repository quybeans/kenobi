// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies

import spray.revolver.AppProcess

// scalastyle:off underscore.import
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
// scalastyle:on underscore.import

object PoliciesPlugin extends AutoPlugin {

  override lazy val trigger: PluginTrigger = allRequirements
  override lazy val requires: Plugins = plugins.JvmPlugin

  object autoImport extends Imports {
    lazy val reboot = taskKey[AppProcess]("Reboot application.")
  }

  import autoImport._ // scalastyle:ignore import.grouping underscore.import

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    scalaVersion := {
      if (baseDirectory.value.relativeTo(Paths.build).isDefined) {
        (scalaVersion in pluginCrossBuild).value
      } else {
        "2.12.4"
      }
    },

    javaOptions ++= Seq(
      s"-Dconfig.file=${Paths.appConfig.getAbsolutePath}"
    ),

    scalacOptions ++= Seq(
      "-deprecation",
      "-feature"
    ),

    fork := !isScalaJSProject.value
  )
}
