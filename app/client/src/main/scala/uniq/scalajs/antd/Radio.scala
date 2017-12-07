// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.CtorType.ChildArg
import japgolly.scalajs.react.component.Js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Radio {

  @JSImport("antd", "Radio")
  @js.native
  object RawComponent extends js.Object

  private final class Props(
    val value: String,
    val disable: Boolean,
    val checked: Boolean
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    value: String,
    disable: Boolean = false,
    checked: Boolean = false
  )(children: ChildArg*): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(value, disable, checked))(children: _*)
  }
}
