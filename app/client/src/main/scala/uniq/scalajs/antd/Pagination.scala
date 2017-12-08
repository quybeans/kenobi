// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Js
// scalastyle:on underscore.import

object Pagination {

  @JSImport("antd", "Pagination")
  @js.native
  object RawComponent extends js.Object

  private final class Props(
    val current: Int,
    val total: Int,
    val showQuickJumper: Boolean,
    val simple: Boolean,
    val onChange: js.Function2[Int, Int, Unit],
    val defaultPageSize: Int
  ) extends js.Object

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    current: Int,
    total: Int = 0,
    showQuickJumper: Boolean = false,
    simple: Boolean = false,
    onChange: (Int, Int) => Callback,
    defaultPageSize: Int = 1
  ): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      current, total, showQuickJumper, simple, (p, ps) => onChange(p, ps).runNow(), defaultPageSize
    ))
  }
}
