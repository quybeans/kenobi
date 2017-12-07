// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.kenobi

import io.circe.Decoder
import io.circe.Encoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveDecoder
import io.circe.generic.extras.semiauto.deriveEncoder

import uniq.client.model.IgAccountResponse
import uniq.client.model.IgPost
import uniq.client.model.IgPosts
import uniq.client.model.IgResult
import uniq.client.model.TeachingForm
import uniq.client.model.Topic
import uniq.client.model.{TopicOption => FormOption}

object circe {
  implicit val snakeCase: Configuration = Configuration.default.withSnakeCaseKeys

  implicit val postDecoder: Decoder[IgPost] = deriveDecoder
  implicit val postsDecoder: Decoder[IgPosts] = deriveDecoder

  implicit val igAccountResponseDecoder: Decoder[IgAccountResponse] = deriveDecoder
  implicit val igUserResponseDecoder: Decoder[IgAccountResponse.User] = deriveDecoder

  implicit val formDecoder: Decoder[TeachingForm] = deriveDecoder
  implicit val topicDecoder: Decoder[Topic] = deriveDecoder
  implicit val optionDecoder: Decoder[FormOption] = deriveDecoder

  implicit val igEncoder: Encoder[IgResult] = deriveEncoder
}
