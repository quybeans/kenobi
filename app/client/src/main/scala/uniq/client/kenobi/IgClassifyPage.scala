// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.kenobi

import scala.scalajs.js
import scala.util.Success

import io.circe.parser.parse
import japgolly.scalajs.react.extra.router.RouterCtl
import org.scalajs.dom
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.raw.Event

import uniq.client.model.IgAccount
import uniq.client.model.IgResult
import uniq.client.model.TeachingForm
import uniq.client.model.TopicOption
import uniq.client.routing.Page
import uniq.scalajs.antd.Button
import uniq.scalajs.antd.CheckboxGroup
import uniq.scalajs.antd.Icon
import uniq.scalajs.antd.Pagination
import uniq.scalajs.antd.Radio
import uniq.scalajs.antd.RadioGroup
import uniq.scalajs.antd.Spin

// scalastyle:off underscore.import
import scala.scalajs.js.JSConverters._

import io.circe.syntax._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import uniq.client.kenobi.circe.igEncoder
// scalastyle:on underscore.import

object IgClassifyPage {

  private final case class Props(routerCtl: RouterCtl[Page])

  private final case class State(
    igAccount: Option[IgAccount] = None,
    currentIndex: Option[Int] = None,
    accounts: List[String] = List.empty,
    inputForm: Option[TeachingForm] = None,
    result: Map[String, String] = Map.empty
  )

  private final class Backend(scope: BackendScope[Props, State]) {

    private[IgClassifyPage] def onComponentDidMount: Callback = {
      loadAccountList >> loadForm
    }

    private[IgClassifyPage] def onComponentUpdate(
      prevState: State,
      nextState: State
    ): Callback = {
      Callback.when(prevState.currentIndex != nextState.currentIndex)(
        getAccount(nextState.accounts(nextState.currentIndex.getOrElse(0)))
      )
    }

    private[this] def submitResult: Callback = {
      for {
        state <- scope.state
        result <- {
          if (state.result.nonEmpty) {
            val classifyResult = IgResult(
              igPK = state.igAccount.get.id,
              examiner = dom.window.localStorage.getItem("username"),
              result = state.result.map { case (key, value) =>
                key + ":" + value
              }.mkString(";")
            ).asJson
            sendResult(classifyResult.noSpaces) >> onNextAccount
          } else {
            Callback(dom.window.alert("You did not select any options."))
          }
        }
      } yield result
    }

    private[this] def sendResult(body: String): Callback = {
      Callback {
        val xhr = new XMLHttpRequest()
        xhr.open("POST", "/classify")
        xhr.setRequestHeader("Content-Type", "application/json; charset=utf-8")
        xhr.onload = { (_: Event) =>
          parse(xhr.responseText).toTry match {
            case Success(json) => circe.igAccountResponseDecoder.decodeJson(json).map { account =>
              val igAccount = IgAccount(
                id = account.user.pk.toString,
                fullName = account.user.fullName.getOrElse(""),
                username = account.user.username,
                profilePicUrl = account.user.profilePicUrl.getOrElse(""),
                bio = account.user.bio
              )

              scope.modState(_.copy(igAccount = Some(igAccount))).runNow()
            }

            case _ => println("Error while getting account Info.")
          }
        }

        xhr.send(body)
      }
    }

    private[this] def getAccount(userId: String): Callback = {
      Callback {
        val xhr = new XMLHttpRequest()
        xhr.open("GET", s"http://i.instagram.com/api/v1/users/$userId/info/")
        xhr.onload = { (_: Event) =>
          parse(xhr.responseText).toTry match {
            case Success(json) => circe.igAccountResponseDecoder.decodeJson(json).map { account =>
              val igAccount = IgAccount(
                id = account.user.pk.toString,
                fullName = account.user.fullName.getOrElse(""),
                username = account.user.username,
                profilePicUrl = account.user.profilePicUrl.getOrElse(""),
                bio = account.user.bio
              )

              scope.modState(_.copy(igAccount = Some(igAccount))).runNow()
            }

            case _ => println("Error while getting account Info.")
          }
        }

        xhr.send()
      }
    }

    private[this] def loadForm: Callback = {
      Callback {
        val xhr = new XMLHttpRequest()
        xhr.open("GET", s"/kenobi")
        xhr.onload = { (_: Event) =>
          parse(xhr.responseText).toTry match {
            case Success(json) => circe.formDecoder.decodeJson(json).map { form =>
              scope.modState(_.copy(inputForm = Some(form))).runNow()
            }

            case _ => println("Error while loading form.")
          }
        }

        xhr.send()
      }
    }

    private[this] def loadAccountList: Callback = {
      Callback {
        val xhr = new XMLHttpRequest()
        xhr.open("GET", s"/accounts")
        xhr.onload = { (_: Event) =>
          parse(xhr.responseText).toTry match {
            case Success(json) => json.as[List[String]].map { accounts =>
              (scope.modState(_.copy(
                accounts = accounts,
                currentIndex = Some(0)
              )) >> getAccount(accounts.head)).runNow()
            }

            case _ => println("Error while loading accounts list.")
          }
        }

        xhr.send()
      }
    }

    private[this] def onChangeAccount(index: Int): Callback = {
      scope.modState(_.copy(igAccount = None)) >>
      scope.modState { oldState =>
        oldState.copy(
          currentIndex = Some(index),
          result = Map.empty
        )
      } >> resetForm
    }

    private[this] def resetForm: Callback = {
      scope.modState(_.copy(inputForm = None)) >> loadForm
    }

    private[this] def onNextAccount: Callback = {
      scope.modState(_.copy(igAccount = None)) >>
      scope.modState { oldState =>
        if (oldState.currentIndex.get >= oldState.accounts.size) {
          oldState
        } else {
          oldState.copy(currentIndex = Some(oldState.currentIndex.get + 1))
        }
      }
    }

    def render(props: Props, state: State): VdomElement = {
      <.div(
        <.div(
          ^.cls := "fl w-30 bg-near-black vh-100 pt5 relative",
          state.currentIndex.whenDefined { index =>
            <.div(
              ^.cls := "mb3 white tc input-black",
              Pagination(
                current = index,
                total = state.accounts.size,
                showQuickJumper = true,
                simple = true,
                onChange = (i, _) => onChangeAccount(i)
              )
            )
          },
          state.igAccount.whenDefined(renderIgAccountInfo).when(state.igAccount.nonEmpty),
          <.div(
            ^.cls := "white-90 f4 w5 center tc",
            Spin(tip = "Loading...").when(state.igAccount.isEmpty),
          ),
          <.div(
            ^.cls := "pa3",
            renderForm(state)
          ),
        ),
        state.currentIndex.whenDefined { index =>
          PostsBox(props.routerCtl, state.accounts(index))
        }
      )
    }

    private[this] def onChangeRadio(
      name: String,
      e: ReactEventFromInput
    ): Callback = {
      val value = e.target.value
      scope.modState { oldState =>
          oldState.copy(
          result = oldState.result + (name -> value)
        )
      }
    }

    private[this] def onChangeCheckbox(
      name: String,
      checkedValues: js.Array[String]
    ): Callback = {
      scope.modState { oldState =>
        oldState.copy(
          result = oldState.result + (name -> checkedValues.mkString(","))
        )
      }
    }

    private def renderOptions(
      name: String,
      allowMultiple: Boolean,
      values: List[TopicOption]
    ) = {
      <.div(
        ^.cls := "white",
        RadioGroup(
          name = name,
          defaultValue = js.undefined,
          onChange = event => onChangeRadio(name, event)
        )(values.toVdomArray { value =>
            Radio(value = value.value)(value.label)
          }
        ).unless(allowMultiple),
        CheckboxGroup(
          options = {
            values.map { topicOption =>
              js.Dynamic.literal(
                "label" -> topicOption.label,
                "value" -> topicOption.value
              )
            }.toJSArray
          },
          onChange = values => onChangeCheckbox(name, values)
        ).when(allowMultiple)
      )
    }

    private def renderForm(state: State) = {
      <.form(
        ^.id := "igForm",
        <.div(
          ^.cls := "bg-gray ba br3 pa3",
          state.inputForm.whenDefined { _.forms.toVdomArray { form =>
            <.div(
              ^.cls := "mt2",
              <.p(
                ^.cls := "white-90 f4",
                form.label + ":"
              ),
                renderOptions(form.name, form.allowMultipleValue, form.values)
            )
          }},
          <.div(
            ^.cls := "mt2",
            Button(onClick = _ => submitResult)("Submit")
          )
        )
      )
    }

    private def renderIgAccountInfo(account: IgAccount) = {
      <.div(
        ^.cls := "white-90 f4 w5 center tc",
        <.img(
          ^.cls := "ba b--white-30 br-100",
          ^.src := account.profilePicUrl
        ),
        <.p(
          ^.cls := "fw6",
          "@" + account.username
        ),
        <.p(
          ^.cls := "fw2",
          account.fullName
        ),

        account.bio.whenDefined { bio =>
          <.span(bio)
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("IgClassifyPage")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(_.backend.onComponentDidMount)
    .componentDidUpdate(be => be.backend.onComponentUpdate(be.prevState, be.currentState))
    .build

  def apply(router: RouterCtl[Page]): ScalaComponent.Unmounted[_, _, _] = {
    component.ctor(Props(router))
  }
}

