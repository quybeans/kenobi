// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.model

abstract class ModelId extends Product with Serializable {
  def idString: String
}
