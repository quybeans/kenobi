// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq

import pureconfig.ConfigFieldMapping
import pureconfig.ProductHint

import uniq.persistence.PersistenceConfig

final case class FacebookConfig(
  webhook: FacebookWebhookConfig
)

final case class FacebookWebhookConfig(
  verificationToken: String
)

final case class UniqConfig(
  persistence: PersistenceConfig,
  facebook: FacebookConfig
)

object UniqConfig {

  private implicit def productHint[T]: ProductHint[T] = {
    ProductHint[T](ConfigFieldMapping(identity))
  }

  val value: UniqConfig = pureconfig.loadConfigOrThrow[UniqConfig]("uniq")
}
