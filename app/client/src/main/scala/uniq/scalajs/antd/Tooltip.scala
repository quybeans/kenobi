// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.Children
import japgolly.scalajs.react.CtorType.ChildArg
import japgolly.scalajs.react.JsComponent
import japgolly.scalajs.react.component.Js

object Tooltip {

  @JSImport("antd", "Tooltip")
  @js.native
  object RawComponent extends js.Object

  final class Props(
    val title: String,
    val placement: String
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](RawComponent)

  def apply(
    title: String,
    placement: String = "top"
  )(children: ChildArg*): Js.UnmountedWithRawType[_, _, _] = {
    component(
      new Props(title, placement)
    )(children: _*)
  }
}
