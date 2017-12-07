// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.library

private[sbtpolicies] sealed abstract case class Library private(
  org: Option[String],
  name: String,
  rev: String
) {

  def withRevision(newRev: String): Library = {
    Library(org, name, newRev)
  }
}

private[sbtpolicies] object Library {

  private[library] def apply(org: Option[String], name: String, rev: String): Library = {
    new Library(org, name, rev) {}
  }
}
