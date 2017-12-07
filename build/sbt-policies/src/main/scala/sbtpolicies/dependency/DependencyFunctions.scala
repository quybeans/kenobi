// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.dependency

import org.scalajs.sbtplugin.ScalaJSCrossVersion
import sbt.CrossVersion
import sbt.ModuleID
import sbtpolicies.library.Library

private[dependency] object DependencyFunctions {

  private def moduleGetter[A](
    get: (String, Library) => A
  ) = { (libId: String, optionalRev: Option[String]) =>
    val lib = optionalRev.foldLeft {
      Dependencies.libraries.get(libId)
    } { (lib, rev) =>
      lib.withRevision(rev)
    }

    get(libId, lib)
  }

  private def transformGetter[A, B](getter: (String, Option[String]) => A, f: A => B) = {
    Function.untupled(Function.tupled(getter).andThen(f))
  }

  lazy val javaModule: (String, Option[String]) => ModuleID = moduleGetter { (libId, lib) =>
    val org = lib.org.getOrElse {
      throw new RuntimeException(s"Dependency $libId lacks an organization value.")
    }

    ModuleID(org, lib.name, lib.rev)
  }

  lazy val scalaModule: (String, Option[String]) => ModuleID = transformGetter(
    javaModule,
    (_: ModuleID).cross(CrossVersion.binary)
  )

  lazy val scalaJsModule: (String, Option[String]) => ModuleID = transformGetter(
    javaModule,
    (_: ModuleID).cross(ScalaJSCrossVersion.binary)
  )

  lazy val npmModule: (String, Option[String]) => (String, String) = moduleGetter { (_, lib) =>
    lib.name -> lib.rev
  }
}
