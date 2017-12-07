// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.authentication

import japgolly.scalajs.react.extra.router.RouterCtl
import org.scalajs.dom.ext.KeyCode

import uniq.client.routing.Page
import uniq.scalajs.antd.Alert
import uniq.scalajs.antd.Button
import uniq.scalajs.antd.Icon
import uniq.scalajs.antd.Input

import org.scalajs.dom

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object LoginBox {

  private final case class Props(
    router: RouterCtl[Page]
  )

  private final case class State(
    username: String = "",
    errorMessage: String = "",
    isLoggingIn: Boolean = false
  )

  private final class Backend(scope: BackendScope[Props, State]) {

    private[this] def onUsernameInputChange(event: ReactEventFromInput): Callback = {
      val username = event.target.value
      scope.modState(_.copy(username = username))
    }

    private[this] def onInputKeyDown(event: ReactKeyboardEvent): Callback = {
      Callback.when(event.keyCode == KeyCode.Enter)(logIn)
    }

    private[this] def onLoginButtonClick(event: ReactMouseEvent): Callback = {
      event.preventDefaultCB >> logIn
    }

    private[this] val logIn: Callback =
      for {
        state <- scope.state
        result <- {
          val username = state.username

          if (username.isEmpty) {
            scope.modState(_.copy(errorMessage = "Enter username."))
          } else {
            Callback(dom.window.localStorage.setItem("username", username)) >> onLogInSuccess
          }
        }
      } yield {
        result
      }

    private[this] val onLogInSuccess: Callback = {
      for {
        props <- scope.props
        result <- props.router.set(Page.Home)
      } yield {
        result
      }
    }

    def render(state: State): VdomTag = {
      <.div(
        ^.cls := "vh-100 dt w-100 bg-near-white",
        <.div(
          ^.cls := "dtc v-mid tc white ph3 ph4-l",
          renderErrorMessage(state.errorMessage).when(state.errorMessage.nonEmpty),
          <.div(
            ^.cls := "bg-gray ma center w5 shadow-4 pa4 bg-white",
            <.img(
              ^.cls := "w4",
              ^.src := "images/uniq-logo.png"
            ),
            Input(
              placeholder = "Username",
              className = "mt3",
              size = "large",
              prefix = Icon("user").vdomElement,
              onChange = onUsernameInputChange,
              onKeyDown = onInputKeyDown,
              value = state.username,
              autoFocus = true
            ),
            Button(
              `type` = "primary",
              className = "mt3 w-100",
              loading = state.isLoggingIn,
              onClick = onLoginButtonClick
            )("Go")
          )
        )
      )
    }

    private[this] def renderErrorMessage(message: String) = {
      <.div(
        ^.cls := "ma center w5 mb2",
        Alert(
          `type` = "error",
          message = message,
          showIcon = true
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("LoginBox")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply(router: RouterCtl[Page]): ScalaComponent.Unmounted[_, _, _] = {
    component.ctor(Props(router))
  }
}
