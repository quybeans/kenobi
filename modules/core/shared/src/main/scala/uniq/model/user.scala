// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.model

object user {

  final case class UserId(idString: String) extends ModelId

  sealed abstract class UserRole extends Product with Serializable
  case object NormalUserRole extends UserRole
  case object AdminUserRole extends UserRole

  final case class User(
    username: String,
    passwordHash: String,
    role: UserRole,
    passwordChangeRequired: Boolean
  ) extends Model
}
