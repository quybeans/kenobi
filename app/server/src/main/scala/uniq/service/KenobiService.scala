// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.service

import cats.effect.IO

import uniq.model.FormOption
import uniq.model.IgAccountClassifyResult.IgResult
import uniq.model.IgAccountClassifyResult.IgResultId
import uniq.model.TeachingForm
import uniq.model.Topic
import uniq.persistence.repository.IgAccountRepository

final class KenobiService(
  igAccountRepository: IgAccountRepository
) {

  def classifyAccount(
    result: IgResult
  ): IO[Int] = {
    val id = IgResultId(s"${result.examiner}_${result.igPK}")
    igAccountRepository.getOptional(id).flatMap {
      case Some(_) => igAccountRepository.update(id -> result)
      case None => igAccountRepository.add(id -> result)
    }
  }

  def createForm: TeachingForm = {
    TeachingForm(
      Topic(
        name = "gender",
        label = "Gender",
        allowMultipleValue = false,
        values = List(
          FormOption(value = "male", label = "Male"),
          FormOption(value = "female", label = "Female"),
          FormOption(value = "other", label = "Other")
        )
      ),

      Topic(
        name = "topic",
        label = "Topic",
        allowMultipleValue = true,
        values = List(
          FormOption(value = "fashion", label = "Fashion"),
          FormOption(value = "beauty", label = "Beauty (Cosmetic)"),
          FormOption(value = "f&b", label = "Food and Beverage"),
          FormOption(value = "lifestyle", label = "Life Style"),
          FormOption(value = "fitness", label = "Sport & Fitness"),
          FormOption(value = "travel", label = "Travel"),
          FormOption(value = "other", label = "Other")
        )
      )
    )
  }
}


