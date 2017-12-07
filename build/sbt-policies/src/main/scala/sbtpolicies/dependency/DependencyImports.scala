// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.dependency

import sbt.ModuleID

private[sbtpolicies] trait DependencyImports {

  import DependencyFunctions._ // scalastyle:ignore import.grouping underscore.import

  def %(libId: String): ModuleID = javaModule(libId, None)
  def %(libId: String, rev: String): ModuleID = javaModule(libId, Some(rev))

  def %%(libId: String): ModuleID = scalaModule(libId, None)
  def %%(libId: String, rev: String): ModuleID = scalaModule(libId, Some(rev))

  def %%%(libId: String): ModuleID = scalaJsModule(libId, None)
  def %%%(libId: String, rev: String): ModuleID = scalaJsModule(libId, Some(rev))

  def npm(libId: String): (String, String) = npmModule(libId, None)
  def npm(libId: String, rev: String): (String, String) = npmModule(libId, Some(rev))

  def v(libId: String): String = npmModule(libId, None)._2
}
