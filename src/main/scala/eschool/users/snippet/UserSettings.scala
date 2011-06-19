package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}
import eschool.users.model.User

object UserSettings extends LiftScreen {
  val user = User.current.is.open_!
  val last = field("Last", user.last.is)
  val preferred = field("Preferred", user.preferred.is)
  val email = field("Email", user.email.is)



  def finish() {
    user.last(last.is)
    user.preferred(preferred.is)
    user.email(email.is)
    user.save()
  }
}