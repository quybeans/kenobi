// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.model

final case class TeachingForm(
  forms: Topic*
)

final case class Topic(
  name: String,
  label: String,
  allowMultipleValue: Boolean,
  values: List[FormOption]
)

final case class FormOption(
  label: String,
  value: String
)
