package eschool.users.snippet


/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: 6/15/11
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */

import net.liftweb.common._
import net.liftweb.http.{S, LiftScreen}

import eschool.users.model.User

object UserPassword extends LiftScreen {
  val user = User.current.is.open_!
  val currentPswd = password("Current Password", "")
  val newPswd = password("New Password", "")
  val reEnterPswd = password("Re-enter New Password", "")

  def finish() {

    User.authenticate(user, currentPswd) match {
      case Full(u) => {
        if (newPswd.is.length < 5) {
          S.notice("Password must be at least 5 characters!")    // can someone try to make these notices appear on the password page
        } else if (newPswd.is == reEnterPswd.is) {              //  instead of sending the user back to the settings page?
            user.password.set(newPswd.is)
            user.save()
        } else {S.notice("Passwords don't match!")}
      }
      case _ => S.error("Incorrect password")
    }
  }
}