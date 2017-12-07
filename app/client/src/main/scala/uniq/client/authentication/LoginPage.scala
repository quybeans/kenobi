// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.authentication

import japgolly.scalajs.react.BackendScope
import japgolly.scalajs.react.extra.router.RouterCtl
import uniq.client.routing.Page

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object LoginPage {

  final case class Props(
    router: RouterCtl[Page]
  )

  private final class Backend(scope: BackendScope[Props, Unit]) {
    def render(props: Props): VdomTag = {
      <.div(
        ^.cls := "vh-100 dt w-100 bg-near-white",
        LoginBox(props.router)
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("LoginPage")
    .stateless
    .renderBackend[Backend]
    .build

  def apply(router: RouterCtl[Page]): ScalaComponent.Unmounted[_, _, _] = {
    component.ctor(Props(router))
  }
}
