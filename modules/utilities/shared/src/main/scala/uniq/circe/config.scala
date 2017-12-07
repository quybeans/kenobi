// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.circe

import io.circe.generic.extras.Configuration

object config {
  implicit val snakeCase: Configuration = Configuration.default.withSnakeCaseKeys
}
