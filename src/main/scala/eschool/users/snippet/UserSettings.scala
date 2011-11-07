package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}
import eschool.utils.record.Helpers.toBox
import eschool.users.model.{User, UserUtil}

object UserSettings extends LiftScreen {
  object user extends ScreenVar[User](UserUtil.getCurrentOrRedirect())
  val preferred = field("Preferred", user.getPreferred())
  val email = field("Email", user.getEmail())

  def finish() {
    user.setPreferred(preferred.get)
    user.setEmail(email.get)
  }
}