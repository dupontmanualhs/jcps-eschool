package eschool.users.snippet

import net.liftweb.common._
import net.liftweb.http.{S, LiftScreen}

import eschool.users.model.User
import net.liftweb.util.FieldError
import xml.Text
import net.liftweb.mongodb.record.field._

object UserPassword extends LiftScreen {
  object user extends ScreenVar[User](User.getCurrentOrRedirect())
  val currentPswd = password("Current Password", "")
  val newPswd = password("New Password", "")
  val reEnterPswd = password("Re-enter New Password", "")

  def checkCurrentPassword(): List[FieldError] = {
    if (User.authenticate(user, currentPswd.get).isDefined) {
      Nil
    } else {
      Text("The current password is incorrect.")
    }
  }

  def checkNewPasswordsMatch(): List[FieldError] = {
    if (newPswd.get == reEnterPswd.get) Nil else Text("New passwords do not match.")
  }

  override def validations = checkCurrentPassword _ +: checkNewPasswordsMatch _ +: super.validations

  def finish() {
    user.password.set(new Password(newPswd.get, ""))
    user.save(true)
    Text("New password set.")
  }
}