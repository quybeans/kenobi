// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.CtorType.ChildArg
import japgolly.scalajs.react.component.Js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object RadioGroup {

  @JSImport("antd", "Radio.Group")
  @js.native
  object RawComponent extends js.Object

  private final class Props(
    val name: String,
    val size: String,
    val defaultValue: js.UndefOr[String],
    val onChange: js.Function1[ReactMouseEventFromInput, Unit],
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    name: String,
    size: String = "default",
    defaultValue: js.UndefOr[String],
    onChange: ReactMouseEventFromInput => Callback = _ => Callback.empty
  )(children: ChildArg*): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      name = name,
      size = size,
      defaultValue = defaultValue,
      onChange = (e) => onChange(e).runNow()
    ))(children: _*)
  }
}
