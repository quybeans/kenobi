// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.component.Js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Icon {

  @JSImport("antd", "Icon")
  @js.native
  object RawComponent extends js.Object

  private class Props(
    val `type`: String,
    val className: js.UndefOr[String]
  ) extends js.Object

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply(
    name: String,
    className: js.UndefOr[String] = js.undefined
  ): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      `type` = name,
      className = className
    ))
  }
}
