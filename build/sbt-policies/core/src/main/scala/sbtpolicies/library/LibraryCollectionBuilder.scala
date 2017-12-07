// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.library

private[sbtpolicies] final class LibraryCollectionBuilder private[library] {

  private[this] val builder = Map.newBuilder[String, Library]

  def lib(org: String, name: String, rev: String): Library = {
    Library(Some(org), name, rev)
  }

  def lib(name: String, rev: String): Library = {
    Library(None, name, rev)
  }

  def add(entry: (String, Library)): Unit = {
    val (libId, lib) = entry
    builder += libId -> lib
    ()
  }

  def add(org: String, name: String, rev: String): Unit = {
    add(name -> lib(org, name, rev))
  }

  def add(name: String, rev: String): Unit = {
    add(name -> lib(name, rev))
  }

  def using(orgOrRev: String)(apply: String => Unit): Unit = {
    apply(orgOrRev)
  }

  def using(org: String, rev: String)(apply: (String, String) => Unit): Unit = {
    apply(org, rev)
  }

  private[library] def result(): LibraryCollection = {
    LibraryCollection(builder.result())
  }
}
