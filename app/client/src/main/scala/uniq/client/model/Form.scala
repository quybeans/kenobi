// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.client.model

final case class TeachingForm(
  forms: Topic*
)

final case class Topic(
  name: String,
  label: String,
  allowMultipleValue: Boolean,
  values: List[TopicOption]
)

final case class TopicOption(
  label: String,
  value: String
)

final case class IgResult(
  igPK: String,
  examiner: String,
  result: String
)
