// Copyright (C) 2016-2017 Ark Maxim, Inc.

package sbtpolicies.minikube

import scala.util.Try

import sbt.Logger

// scalastyle:off underscore.import
import sbt._
// scalastyle:on underscore.import

private[sbtpolicies] trait MinikubeImports {

  object Minikube {

    private def execute(command: String): Option[String] = {
      Try((command !! Logger.Null).stripLineEnd).toOption
    }

    private[Minikube] sealed abstract class BaseException extends RuntimeException {
      def message: String
      override def getMessage: String = message
    }

    case object MinikubeNotStartedException extends BaseException {
      def message: String = "Minikube not started."
    }

    final case class FirstNodePortNotFoundException(
      service: String,
      namespace: String
    ) extends BaseException {
      def message: String = s"""Could not get the first node port of service "$service" in namespace "$namespace"."""
    }

    lazy val ip: String = {
      execute("minikube ip").getOrElse(throw MinikubeNotStartedException)
    }

    def firstNodePort(service: String, namespace: String): String = {
      execute(s"kubectl -n $namespace get service $service -o jsonpath={.spec.ports[0].nodePort}")
        .getOrElse(throw FirstNodePortNotFoundException(service, namespace))
    }

    sealed abstract class Environment(namespace: String) {

      lazy val envVars: Map[String, String] = {
        Map(
          "POSTGRES_HOST" -> ip,
          "POSTGRES_PORT" -> firstNodePort("postgres", namespace)
        )
      }
    }

    case object Development extends Environment("uniq")
    case object Test extends Environment("uniq-test")
  }
}
