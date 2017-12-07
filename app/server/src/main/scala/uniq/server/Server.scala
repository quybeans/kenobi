// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.server

import java.io.File

import scala.io.Source

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.ContentTypes
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes
import akka.stream.ActorMaterializer
import cats.effect.IO
import doobie.util.transactor.Transactor

import uniq.UniqConfig
import uniq.model.IgAccountClassifyResult.IgResult
import uniq.model.IgPost
import uniq.model.IgPosts
import uniq.persistence.UniqDataSource
import uniq.service.Services
import uniq.view.html

// scalastyle:off underscore.import
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.syntax._

import uniq.circe._
// scalastyle:on underscore.import

object Server extends App {

  private implicit val system: ActorSystem = ActorSystem("sangria-server")
  private implicit val materializer: ActorMaterializer = ActorMaterializer()

  private val dataSource = UniqDataSource(UniqConfig.value.persistence)
  private val transactor: Transactor[IO] = Transactor.fromDataSource[IO].apply(dataSource)

  import system.dispatcher // scalastyle:ignore import.grouping

  private val config = UniqConfig.value

  private val indexRoute = (pathEndOrSingleSlash & get) {
    redirectToTrailingSlashIfMissing(StatusCodes.MovedPermanently) {
      complete {
        HttpEntity(ContentTypes.`text/html(UTF-8)`, html.index().body)
      }
    }
  }

  private[this] val services = new Services(transactor)

  private val assetsDir = new File("../client/target/scala-2.12/scalajs-bundler/main").getCanonicalPath
  private val assetsRoute = concat(
    get {
      getFromDirectory(assetsDir)
    },
    (get & pathPrefix("images")) {
      getFromResourceDirectory("images")
    },
    (get & path("instagram" / """\d+""".r)) { id =>
      complete {
        val posts = Source.fromFile(new File(s"../server/target/scala-2.12/classes/data/crawled/${id}_posts.txt"))
          .getLines().toList.map { line =>
            val raw = line.split("\t")
            IgPost(
              id = raw.head,
              mediaType = raw(1),
              urls = raw(2).split(",").toList,
              caption = raw.lastOption
            )
          }

          IgPosts(posts).asJson
      }
    }
  )

  private val kenobiRoute = (get & path("kenobi")) {
    complete(
      services.kenobiService.createForm.asJson
    )
  } ~ (get & path("accounts")) {
    complete {
      val followers = Source.fromFile(new File(s"../server/target/scala-2.12/classes/data/28600663_followers.txt"))
      followers.getLines().slice(0, 8000).toList.map { line =>
        line.split("\t").head
      }.asJson
    }
  } ~ (post & path("classify")) {
    entity(as[IgResult]) { result =>
      complete {
        services.kenobiService
          .classifyAccount(result)
          .unsafeRunSync().asJson
      }
    }
  }

  private val route = concat(
    indexRoute,
    assetsRoute,
    kenobiRoute
  )

  Http().bindAndHandle(
    handler = route,
    interface = "0.0.0.0",
    port = 6790 // scalastyle:ignore magic.number
  )
}
