// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.library

private[sbtpolicies] sealed abstract case class LibraryCollection private[library](
  private val libs: Map[String, Library]
) {

  def ++(other: LibraryCollection): LibraryCollection = {
    LibraryCollection(libs ++ other.libs)
  }

  def get(libId: String): Library = {
    libs.getOrElse(libId, throw new RuntimeException(s"Dependency not found: $libId."))
  }
}

private[sbtpolicies] object LibraryCollection {

  private[library] def apply(libs: Map[String, Library]) = {
    new LibraryCollection(libs) {}
  }

  def apply(build: LibraryCollectionBuilder => Unit): LibraryCollection = {
    val builder = new LibraryCollectionBuilder
    build(builder)
    builder.result()
  }
}
