// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.CtorType.ChildArg
import japgolly.scalajs.react.component.Js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Button {

  @JSImport("antd", "Button")
  @js.native
  object RawComponent extends js.Object

  private class Props(
    val `type`: String,
    val onClick: js.Function1[ReactMouseEvent, Unit],
    val className: js.UndefOr[String],
    val loading: Boolean,
    val size: String,
    val shape: js.UndefOr[String],
    val icon: js.UndefOr[String]
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    `type`: String = "default",
    onClick: ReactMouseEvent => Callback = _ => Callback.empty,
    className: js.UndefOr[String] = js.undefined,
    loading: Boolean = false,
    size: String = "default",
    shape: js.UndefOr[String] = js.undefined,
    icon: js.UndefOr[String] = js.undefined
  )(children: ChildArg*): Js.UnmountedWithRawType[_, _, _] = {
    val props = new Props(
      `type` = `type`,
      onClick = e => onClick(e).runNow(),
      className = className,
      loading = loading,
      size = size,
      shape = shape,
      icon = icon
    )
    component(props)(children: _*)
  }
}
