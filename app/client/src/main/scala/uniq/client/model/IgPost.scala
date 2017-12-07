// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.model

final case class IgPost(
  id: String,
  mediaType: String,
  urls: List[String],
  caption: Option[String],
)

final case class IgPosts(
  data: List[IgPost]
)
