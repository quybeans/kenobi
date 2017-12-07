// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.kenobi

import scala.util.Success

import japgolly.scalajs.react.extra.router.RouterCtl
import org.scalajs.dom.XMLHttpRequest
import org.scalajs.dom.raw.Event

import uniq.client.model.IgAccount
import uniq.client.model.IgPost
import uniq.client.model.TeachingForm
import uniq.client.routing.Page
import uniq.scalajs.antd.Spin

// scalastyle:off underscore.import
import io.circe.parser._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import uniq.client.kenobi.circe._
// scalastyle:on underscore.import

object PostsBox {

  private final case class Props(
    routerCtl: RouterCtl[Page],
    userId: String
  )

  private final case class State(
    posts: List[IgPost] = List.empty,
    igAccount: Option[IgAccount] = None,
    inputForm: Option[TeachingForm] = None,
    isLoading: Boolean = false
  )

  private final class Backend(scope: BackendScope[Props, State]) {

    private[this] def loadPosts(userId: String): Callback = {
      scope.modState(_.copy(isLoading = true)) >> Callback {
        val xhr = new XMLHttpRequest()
        xhr.open("GET", s"/instagram/$userId")
        xhr.onload = { (_: Event) =>
          parse(xhr.responseText).toTry match {
            case Success(json) => circe.postsDecoder.decodeJson(json).map { posts =>
              scope.modState(_.copy(posts = posts.data, isLoading = false)).runNow()
            }

            case _ => scope.modState(_.copy(posts = List.empty, isLoading = false)).runNow()
          }
        }

        xhr.send()
      }
    }


    def onComponentDidMount(userId: String): Callback = {
      loadPosts(userId)
    }

    def onComponentWillReceiveProps(currentUserId: String, nextUserId: String): Callback = {
      Callback.when(currentUserId != nextUserId)(loadPosts(nextUserId))
    }

    def render(prop: Props, state: State): VdomElement = {
      <.div(
        <.div(
          ^.cls := "fl w-70 bg-black-10 mb3",
          <.div(
            ^.cls := "vh-100 flex flex-wrap flex-auto overflow-x-scroll justify-center",
            state.posts.toVdomArray { post =>
              renderPost(post)
            }
          )
        ).unless(state.isLoading),
        <.div(
          ^.cls := "w-70 tc",
          Spin(tip = "Loading...").when(state.isLoading)
        )
      )
    }

    private def renderPost(post: IgPost) = {
      <.div(
        ^.cls := "w5 bg-white pa2 br2 ma2 tc",
        post.urls.headOption.whenDefined { url =>
          <.img(
            ^.cls := "h5",
            ^.src := url
          )
        },
        post.caption.whenDefined { caption =>
          <.p(
            ^.cls := "mt1 fw3 f6",
            caption
          )
        }
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props]("PostsBox")
    .initialState(State())
    .renderBackend[Backend]
    .componentDidMount(b => b.backend.onComponentDidMount(b.props.userId))
    .componentWillReceiveProps(b =>
      b.backend.onComponentWillReceiveProps(b.currentProps.userId, b.nextProps.userId)
    )
    .build

  def apply(
    router: RouterCtl[Page],
    userId: String
  ): ScalaComponent.Unmounted[_, _, _] = {
    component.ctor(Props(router, userId))
  }
}
