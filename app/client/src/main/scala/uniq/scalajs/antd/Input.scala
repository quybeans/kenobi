// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.component.Js
import japgolly.scalajs.react.raw.ReactNode
import japgolly.scalajs.react.vdom.VdomElement

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Input {

  @JSImport("antd", "Input")
  @js.native
  object RawComponent extends js.Object

  final class Props(
    val `type`: String,
    val placeholder: js.UndefOr[String],
    val className: js.UndefOr[String],
    val id: js.UndefOr[String],
    val value: js.UndefOr[String],
    val defaultValue: js.UndefOr[String],
    val size: String,
    val disabled: Boolean,
    val prefix: js.UndefOr[ReactNode],
    val suffix: js.UndefOr[ReactNode],
    val onPressEnter: js.Function1[ReactKeyboardEvent, Unit],
    val onKeyDown: js.Function1[ReactKeyboardEvent, Unit],
    val onChange: js.Function1[ReactMouseEventFromInput, Unit],
    val autoFocus: Boolean
  ) extends js.Object

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    `type`: String = "text",
    className: js.UndefOr[String] = js.undefined,
    id: js.UndefOr[String] = js.undefined,
    autoFocus: Boolean = false,
    placeholder: js.UndefOr[String] = js.undefined,
    value: js.UndefOr[String] = js.undefined,
    defaultValue: js.UndefOr[String] = js.undefined,
    size: String = "default",
    disabled: Boolean = false,
    prefix: js.UndefOr[VdomElement] = js.undefined,
    suffix: js.UndefOr[VdomElement] = js.undefined,
    onPressEnter: ReactKeyboardEvent => Callback = _ => Callback.empty,
    onKeyDown: ReactKeyboardEvent => Callback = _ => Callback.empty,
    onChange: ReactMouseEventFromInput => Callback = _ => Callback.empty
  ): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      `type` = `type`,
      autoFocus = autoFocus,
      className = className,
      id = id,
      placeholder = placeholder,
      value = value,
      defaultValue = defaultValue,
      size = size,
      disabled = disabled,
      prefix = prefix.map(_.rawElement),
      suffix = suffix.map(_.rawElement),
      onPressEnter = e => onPressEnter(e).runNow(),
      onKeyDown = e => onKeyDown(e).runNow(),
      onChange = e => onChange(e).runNow()
    ))
  }
}
