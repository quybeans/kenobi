// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.moment

import scala.scalajs.js

import uniq.scalajs.moment.internal.Moment

object DateTime {

  private[this] def prettify(string: String): String = {
    if (Moment().isSame(Moment(string), "d")) {
      Moment(string).fromNow(

      )
    } else {
      Moment(string).calendar(js.undefined, opts = CalendarOptions(
        lastDay = Some("[Yesterday]"),
        lastWeek = Some("ddd hh:mma"),
        sameElse = Some("MMM Do")
      ))
    }
  }

  def apply(string: String): String = prettify(string)
}
