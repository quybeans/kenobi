// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq

import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveEncoder

import uniq.model.IgAccountClassifyResult.IgResult
import uniq.model.IgPost
import uniq.model.IgPosts
import uniq.model.TeachingForm
import uniq.model.Topic
import uniq.model.FormOption

package object circe {

  implicit val snakeCase: Configuration = Configuration.default.withSnakeCaseKeys

  implicit val postEncoder: Encoder[IgPost] = deriveEncoder
  implicit val postsEncoder: Encoder[IgPosts] = deriveEncoder

  implicit val teachingFormEncoder: Encoder[TeachingForm] = deriveEncoder
  implicit val formEncoder: Encoder[Topic] = deriveEncoder
  implicit val optionEncoder: Encoder[FormOption] = deriveEncoder

  implicit val igResultEncoder: Encoder[IgResult] = deriveEncoder
  implicit val igResultDecoder: Decoder[IgResult] = deriveDecoder
}
