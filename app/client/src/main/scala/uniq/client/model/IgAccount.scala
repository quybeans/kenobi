// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.model

final case class IgAccount(
  id: String,
  username: String,
  fullName: String,
  profilePicUrl: String,
  bio: Option[String]
)

final case class IgAccountResponse(
  user: IgAccountResponse.User
)

object IgAccountResponse {
  final case class User(
    pk: Long,
    fullName: Option[String],
    bio: Option[String],
    username: String,
    profilePicUrl: Option[String]
  )
}
