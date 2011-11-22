package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}
import eschool.utils.record.Helpers.toBox
import eschool.users.model.IUser
import eschool.users.model.jdo.User

object UserSettings extends LiftScreen {
  object user extends ScreenVar[User](IUser.getCurrentOrRedirect())
  val preferred = field("Preferred", user.getPreferred)
  val email = field[String]("Email", user.getEmail.get)

  def finish() {
    user.setPreferred(preferred.get)
    user.setEmail(email.get)
  }
}