// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalaz

import scalaz.concurrent.Task

import cats.effect.IO
import cats.~>

object task {

  implicit val scalazTaskToCatsIO: Task ~> IO = new (Task ~> IO) {

    final def apply[A](task: Task[A]): IO[A] = {
      IO.async { callback =>
        task.unsafePerformAsync { result =>
          callback(result.toEither)
        }
      }
    }
  }

  implicit final class ScalazTaskOps[A](val task: Task[A]) extends AnyVal {

    def toCatsIO: IO[A] = {
      scalazTaskToCatsIO(task)
    }
  }
}
