// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence

final case class PostgresConfig(
  host: String,
  port: Int,
  database: String,
  user: String,
  password: String
)

final case class PersistenceConfig(
  postgres: PostgresConfig
)
