package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}
import eschool.utils.record.Helpers.toBox
import eschool.users.model.User

object UserSettings extends LiftScreen {
  object user extends ScreenVar[User](User.getCurrentOrRedirect())
  val preferred = field("Preferred", user.preferred)
  val email = field[String]("Email", user.email.get)

  def finish() {
    user.preferred = preferred.get
    user.email = email.get
  }
}