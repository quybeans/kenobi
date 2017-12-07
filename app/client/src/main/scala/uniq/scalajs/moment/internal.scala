// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.scalajs.moment

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

private[moment] object internal {

  @js.native
  trait Moment extends js.Object {
    def apply(): Date = js.native
    def apply(string: String): Date = js.native
    def apply(string: String, format: String): Date = js.native
  }

  @JSImport("moment", JSImport.Namespace)
  @js.native
  object Moment extends Moment
}
