package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}
import eschool.utils.record.Helpers.toBox
import eschool.users.model.User

object UserSettings extends LiftScreen {
  val user = User.getCurrentOrRedirect()
  val preferred = field("Preferred", user.preferred.get.getOrElse(""))
  val email = field("Email", user.email.get.getOrElse(""))

  def finish() {
    user.preferred(toBox(preferred.get))
    user.email(toBox(email.get))
    user.save(true)
  }
}