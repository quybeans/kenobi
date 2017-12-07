// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.component.Js
import japgolly.scalajs.react.raw.ReactNode

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Alert {

  @JSImport("antd", "Alert")
  @js.native
  object RawComponent extends js.Object

  private final class Props(
    val `type`: String,
    val closable: js.UndefOr[Boolean],
    val closeText: js.UndefOr[ReactNode],
    val message: ReactNode,
    val description: js.UndefOr[ReactNode],
    val onClose: js.Function0[Unit],
    val showIcon: Boolean,
    val banner: Boolean
  ) extends js.Object

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    `type`: String = "info",
    closable: js.UndefOr[Boolean] = js.undefined,
    closeText: js.UndefOr[ReactNode] = js.undefined,
    message: ReactNode,
    description: js.UndefOr[ReactNode] = js.undefined,
    onClose: Callback = Callback.empty,
    showIcon: Boolean = false,
    banner: Boolean = false
  ): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      `type` = `type`,
      closable = closable,
      closeText = closeText,
      message = message,
      description = description,
      onClose = () => onClose.runNow(),
      showIcon = showIcon,
      banner = banner
    ))
  }
}
