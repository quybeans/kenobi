// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client

import scala.scalajs.js.annotation.JSExportTopLevel

import japgolly.scalajs.react.extra.router.BaseUrl
import japgolly.scalajs.react.extra.router.Router
import org.scalajs.dom

import uniq.client.routing.Routing

object Client {

  @JSExportTopLevel("uniq.client.Client.main")
  def main(): Unit = {
    val router = Router(BaseUrl.fromWindowOrigin_/, Routing.config)
    router().renderIntoDOM(dom.document.getElementById("app"))
  }
}
