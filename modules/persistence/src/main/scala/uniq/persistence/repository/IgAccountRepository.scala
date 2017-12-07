// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence.repository

import cats.effect.IO
import doobie.util.fragment.Fragment
import doobie.util.transactor.Transactor

import uniq.model.IgAccountClassifyResult.IgResult
import uniq.model.IgAccountClassifyResult.IgResultId

// scalastyle:off underscore.import
import uniq.persistence.repository.implicits._
import doobie.implicits._
// scalastyle:on underscore.import

final class IgAccountRepository(
  transactor: Transactor[IO]
) extends BaseRepository[IgResultId, IgResult](
  "ig_account_classify_result",
  transactor
) {
  def update(
    record: (IgResultId, IgResult)
  ): IO[Int] = {
    val result = record._2.result
    val id = record._1
    val sql = fr"UPDATE" ++ Fragment.const(table) ++
      fr"SET result = $result" ++
      fr"WHERE id = ${id.idString}"

    sql.update.run.transact(transactor)
  }
}

