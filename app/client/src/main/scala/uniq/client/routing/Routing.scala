// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.routing

import japgolly.scalajs.react.CallbackTo
import japgolly.scalajs.react.extra.router.Redirect
import japgolly.scalajs.react.extra.router.RouterConfig
import japgolly.scalajs.react.extra.router.RouterConfigDsl
import org.scalajs.dom
import uniq.client.authentication.LoginPage
import uniq.client.kenobi.IgClassifyPage

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.Implicits._
// scalastyle:on underscore.import

object Routing {

  val config: RouterConfig[Page] = RouterConfigDsl[Page].buildConfig { dsl =>
    import dsl._ // scalastyle:ignore import.grouping underscore.import

    def isLoggedIn: CallbackTo[Boolean] = {
      CallbackTo(dom.window.localStorage.getItem("user") == "admin")
    }

    def onNotLoggedIn(page: Page): Option[Action] = {
      Some(redirectToPage(Page.Login)(Redirect.Replace))
    }

    val publicRoutes = {
      (emptyRule
        | staticRoute("#/login", Page.Login) ~> renderR(LoginPage.apply)
      )
    }

    val privateRoutes = {
      (emptyRule
        | staticRoute("#/home", Page.Home) ~> renderR(IgClassifyPage.apply)
      ) .addCondition(isLoggedIn)(onNotLoggedIn)
    }

    (trimSlashes
      | publicRoutes
      | privateRoutes
    )
    .notFound(redirectToPage(Page.Home)(Redirect.Replace))
    .renderWith { (_, resolution) =>
      resolution.render()
    }
  }
}
