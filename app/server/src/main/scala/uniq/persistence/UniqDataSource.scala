// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence

import javax.sql.DataSource

import org.postgresql.ds.PGSimpleDataSource

object UniqDataSource {

  def apply(persistenceConfig: PersistenceConfig): DataSource = {
    val postgresConfig = persistenceConfig.postgres
    val dataSource = new PGSimpleDataSource

    dataSource.setServerName(postgresConfig.host)
    dataSource.setPortNumber(postgresConfig.port)
    dataSource.setDatabaseName(postgresConfig.database)
    dataSource.setUser(postgresConfig.user)
    dataSource.setPassword(postgresConfig.password)

    dataSource
  }
}
