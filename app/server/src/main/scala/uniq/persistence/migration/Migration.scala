// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence.migration

import scala.util.Try

import uniq.UniqConfig

object Migration extends App {

  private val runner = new MigrationRunner(UniqConfig.value.persistence)

  Try(args(0)).toOption match {
    case Some("clean") => runner.clean()
    case _ => runner.migrate()
  }
}
