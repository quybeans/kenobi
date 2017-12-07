// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence.repository

import cats.effect.IO
import doobie.util.transactor.Transactor

import uniq.model.user.User
import uniq.model.user.UserId

// scalastyle:off underscore.import
import uniq.persistence.repository.implicits._
// scalastyle:on underscore.import

final class UserRepository(
  transactor: Transactor[IO]
) extends BaseRepository[UserId, User](
  "users",
  transactor
)
