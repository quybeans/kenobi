// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.antd

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.CtorType.ChildArg
import japgolly.scalajs.react.component.Js
import japgolly.scalajs.react.raw.ReactNode

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Modal {

  val DefaultModalWidth = 400

  @JSImport("antd", "Modal")
  @js.native
  object RawComponent extends js.Object

  final class Props(
    val title: js.UndefOr[ReactNode],
    val visible: Boolean,
    val onOk: js.Function0[Unit],
    val onCancel: js.Function0[Unit],
    val okText: String,
    val cancelText: String,
    val maskClosable: Boolean,
    val footer: js.UndefOr[ReactNode],
    val width: Int,
    val wrapClassName: js.UndefOr[String],
    val style: Map[String, String]
  ) extends js.Object

  private val component = JsComponent[Props, Children.Varargs, Null](RawComponent)

  def apply( // scalastyle:ignore parameter.number
    title: js.UndefOr[ReactNode] = js.undefined,
    visible: Boolean,
    onOk: Callback = Callback.empty,
    onCancel: Callback = Callback.empty,
    okText: String = "OK",
    cancelText: String = "Cancel",
    maskClosable: Boolean = true,
    footer: js.UndefOr[ReactNode] = js.undefined,
    width: Int = DefaultModalWidth,
    wrapClassName: js.UndefOr[String] = js.undefined,
    style: Map[String, String] = Map.empty
  )(children: ChildArg*): Js.UnmountedWithRawType[_, _, _] = {
    component(new Props(
      title = title,
      visible = visible,
      onOk = () => onOk.runNow(),
      onCancel = () => onCancel.runNow(),
      okText = okText,
      cancelText = cancelText,
      maskClosable = maskClosable,
      footer = footer,
      width = width,
      wrapClassName = wrapClassName,
      style = style
    ))(children: _*)
  }
}
