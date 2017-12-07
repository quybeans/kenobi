// Copyright (C) 2016-2017 Ark Maxim, Inc.

package uniq.persistence.repository

import doobie.util.composite.Composite
import uniq.model.user.AdminUserRole
import uniq.model.user.NormalUserRole
import uniq.model.user.UserRole

object implicits {

  implicit val userRoleComposite: Composite[UserRole] = {
    Composite[Int].imap {
      case 0 => NormalUserRole
      case 1 => AdminUserRole
      case _ => throw new RuntimeException("Unexpected user role value.")
    } {
      case NormalUserRole => 0
      case AdminUserRole => 1
    }
  }
}
