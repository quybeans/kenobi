// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence.migration

import org.flywaydb.core.Flyway
import org.postgresql.ds.PGSimpleDataSource

import uniq.persistence.PersistenceConfig

final class MigrationRunner(config: PersistenceConfig) {

  private[this] val postgresConfig = config.postgres

  private[this] val dataSource = new PGSimpleDataSource
  dataSource.setServerName(postgresConfig.host)
  dataSource.setPortNumber(postgresConfig.port)
  dataSource.setDatabaseName(postgresConfig.database)
  dataSource.setUser(postgresConfig.user)
  dataSource.setPassword(postgresConfig.password)

  private[this] val flyway = new Flyway
  flyway.setDataSource(dataSource)

  def migrate(): Unit = {
    flyway.migrate()
  }

  def clean(): Unit = {
    flyway.clean()
  }
}
