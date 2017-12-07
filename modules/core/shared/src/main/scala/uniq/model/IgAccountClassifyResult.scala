// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.model

object IgAccountClassifyResult {


  final case class IgResultId(idString: String) extends ModelId

  final case class IgResult(
    igPK: String,
    examiner: String,
    result: String
  ) extends Model
}
