// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.Children
import japgolly.scalajs.react.JsComponent
import japgolly.scalajs.react.component.Js

object Spin {

  @JSImport("antd", "Spin")
  @js.native
  object RawComponent extends js.Object

  final class Props(
    val size: String,
    val spinning: Boolean,
    val tip: js.UndefOr[String],
    val delay: js.UndefOr[Int],
    val wrapperClassName: js.UndefOr[String]
  ) extends js.Object

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply(
    size: String = "default",
    spinning: Boolean = true,
    tip: js.UndefOr[String] = js.undefined,
    delay: js.UndefOr[Int] = js.undefined,
    className: js.UndefOr[String] = js.undefined
  ): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      size = size,
      spinning = spinning,
      tip = tip,
      delay = delay,
      wrapperClassName = className
    ))
  }
}
