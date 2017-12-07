// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.component.Js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object CheckboxGroup {

  @JSImport("antd", "Checkbox.Group")
  @js.native
  object RawComponent extends js.Object

  private final class Props(
    val defaultValue: js.Array[String],
    val options: js.Array[js.Object with js.Dynamic],
    val onChange: js.Function1[js.Array[String], Unit]
  ) extends js.Object

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    defaultValue: js.Array[String] = new js.Array,
    options: js.Array[js.Object with js.Dynamic],
    onChange: js.Array[String] => Callback = _ => Callback.empty
  ): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      defaultValue,
      options,
      (e) => onChange(e).runNow()
    ))
  }
}
