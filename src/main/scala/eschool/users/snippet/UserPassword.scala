package eschool.users.snippet

import net.liftweb.common._
import net.liftweb.http.{S, LiftScreen}

import eschool.users.model.User

object UserPassword extends LiftScreen {
  val user = User.getCurrentOrRedirect()
  val currentPswd = password("Current Password", "")
  val newPswd = password("New Password", "")
  val reEnterPswd = password("Re-enter New Password", "")

  def finish() {
    User.authenticate(user, currentPswd) match {
      case Full(u) => {
        if (newPswd.is.length < 5) {
          S.notice("Password must be at least 5 characters!")    // can someone try to make these notices appear on the password page
        } else if (newPswd.get == reEnterPswd.get) {              //  instead of sending the user back to the settings page?

          user.password.set(newPswd.get)
            user.save(true)
        } else {S.notice("Passwords don't match!")}
      }
      case _ => S.error("Incorrect password")
    }
  }
}