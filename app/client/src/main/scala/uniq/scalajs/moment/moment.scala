// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs

import scala.scalajs.js
import scala.scalajs.js.UndefOr

// scalastyle:off underscore.import
import scala.scalajs.js.JSConverters._
// scalastyle:on underscore.import

package object moment {

  private[moment] final class CalendarOptions(
    val sameDay: UndefOr[String],
    val nextDay: UndefOr[String],
    val lastDay: UndefOr[String],
    val lastWeek: UndefOr[String],
    val sameElse: UndefOr[String]
  ) extends js.Object

  private[moment] object CalendarOptions {

    def apply(
      sameDay: Option[String] = None,
      nextDay: Option[String] = None,
      lastDay: Option[String] = None,
      lastWeek: Option[String] = None,
      sameElse: Option[String] = None
    ): CalendarOptions = new CalendarOptions(
      sameDay = sameDay.orUndefined,
      nextDay = nextDay.orUndefined,
      lastDay = lastDay.orUndefined,
      lastWeek = lastWeek.orUndefined,
      sameElse = sameElse.orUndefined
    )
  }

  @js.native
  trait Date extends js.Object {
    def toDate(): js.Date = js.native
    def toISOString(): String = js.native
    def fromNow(): String = js.native
    def format(format: String): String = js.native
    def isSame(date: Date, unit: String): Boolean = js.native
    def calendar(reference: js.UndefOr[Date], opts: CalendarOptions): String = js.native
  }
}
