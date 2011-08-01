package eschool.users.snippet

import net.liftweb.common._
import net.liftweb.http.{S, LiftScreen}

import eschool.users.model.User
import net.liftweb.util.FieldError
import xml.Text
import net.liftweb.mongodb.record.field._

object UserPassword extends LiftScreen {
  object user extends ScreenVar[User](User.getCurrentOrRedirect())
  val currentPswd = password("Current Password", "", checkCurrentPassword _)
  val newPswd = password("New Password", "", valMinLen(5, "The new password must longer than 5 characters."))
  val reEnterPswd = password("Re-enter New Password", "")

  def checkCurrentPassword(s: String): List[FieldError] = {
    User.authenticate(user, s) match {
      case Full(user) => Nil
      case _ => Text("The current password is incorrect.")
    }
  }

  def checkNewPasswordsMatch(): List[FieldError] = {
    if (newPswd.get == reEnterPswd.get) Nil else Text("New passwords do not match.")
  }

  override def validations = checkNewPasswordsMatch _ +: super.validations

  def finish() {
    user.password.set(new Password(newPswd.get, ""))
    user.save(true)
    Text("New password set.")
  }
}