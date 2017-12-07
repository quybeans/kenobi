// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence.repository

import cats.data.NonEmptyList
import cats.effect.IO
import doobie.util.composite.Composite
import doobie.util.fragment.Fragment
import doobie.util.fragments
import doobie.util.param.Param
import doobie.util.query.Query0
import doobie.util.transactor.Transactor
import doobie.util.update.Update

import uniq.model.Model
import uniq.model.ModelId

// scalastyle:off underscore.import
import cats.implicits._
import doobie.implicits._
// scalastyle:on underscore.import

abstract class BaseRepository[A <: ModelId: Composite: Param, B <: Model: Composite](
  val table: String,
  transactor: Transactor[IO]
) {

  private[this] def getQuery(id: A): Query0[(A, B)] = {
    (fr"SELECT * FROM " ++ Fragment.const(table) ++ fr"WHERE id = $id").query[(A, B)]
  }

  def add(records: (A, B)*): IO[Int] = {
    val recordLength = Composite[A].length + Composite[B].length
    val placeholders = ("?" * recordLength).mkString(", ")
    val sql = s"INSERT INTO $table VALUES ($placeholders)"

    Update[(A, B)](sql)
      .updateMany(records.toList)
      .transact(transactor)
  }

  def get(id: A): IO[B] = {
    getQuery(id)
      .unique
      .map { case (_, model) => model }
      .transact(transactor)
  }

  def getOptional(id: A): IO[Option[B]] = {
    getQuery(id)
      .option
      .map(_.map { case (_, model) => model })
      .transact(transactor)
  }

  def getMulti(ids: A*): IO[Map[A, B]] = {
    NonEmptyList.fromList(ids.toList).fold {
      IO.pure(Map.empty[A, B])
    } { ids =>
      fragments
        .in(fr"SELECT * FROM " ++ Fragment.const(table) ++ fr"WHERE id", ids)
        .query[(A, B)]
        .list
        .map(_.toMap)
        .transact(transactor)
    }
  }

  def remove(id: A): IO[Int] = {
    (fr"DELETE FROM " ++ Fragment.const(table) ++ fr"WHERE id = $id")
      .update
      .run
      .transact(transactor)
  }
}
