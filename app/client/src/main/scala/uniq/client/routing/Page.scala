// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.routing

sealed abstract class Page {
  def isAuthenticationRequired: Boolean
}

sealed abstract class PrivatePage extends Page {
  final val isAuthenticationRequired = true
}

sealed abstract class PublicPage extends Page {
  final val isAuthenticationRequired = false
}

object Page {
  case object Login extends PublicPage
  case object Home extends PrivatePage
}
