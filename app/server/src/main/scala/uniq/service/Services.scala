// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.service

import cats.effect.IO
import doobie.util.transactor.Transactor

import uniq.persistence.repository.IgAccountRepository

final class Services(
  transactor: Transactor[IO]
) {
  private[this] val kenobiRepo = new IgAccountRepository(transactor)
  val kenobiService = new KenobiService(kenobiRepo)
}
