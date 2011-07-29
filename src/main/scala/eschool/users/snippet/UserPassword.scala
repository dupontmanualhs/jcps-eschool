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
  val newPswd = password("New Password", "")
  val reEnterPswd = password("Re-enter New Password", "")

  def checkCurrentPassword(pswd: String): List[FieldError] = {
    if (user.password.isMatch(pswd)) {
      Nil
    } else {
      Text("The current password is incorrect.")
    }
  }

  def checkNewPasswordsMatch(): List[FieldError] = {
    if (newPswd.get == reEnterPswd.get) Nil else Text("New passwords do not match.")
  }

  def checkLength(): List[FieldError] = {
    if(newPswd.get.length() < 4) Text("New password is too short.") else Nil
  }

  override def validations = checkNewPasswordsMatch _ +: checkLength _  +: super.validations

  def finish() {
    user.password.set(new Password(newPswd.get, ""))
    user.save(true)
    Text("New password set.")
  }
}