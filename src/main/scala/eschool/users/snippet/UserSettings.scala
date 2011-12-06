 package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}
import eschool.utils.record.Helpers.toBox
import eschool.users.model.User
import bootstrap.liftweb.DataStore

object UserSettings extends LiftScreen {
  object user extends ScreenVar[User](DataStore.pm.detachCopy(User.getCurrentOrRedirect()))
  val preferred = field[String]("Preferred", user.preferred.getOrElse(""))
  val email = field[String]("Email", user.email.getOrElse(""))

  def finish() {
    if (preferred.get.trim == "") {
      user.get.preferred = None
    } else {
      user.get.preferred = Some(preferred.get)
    }
    user.get.email = email.get
    DataStore.pm.makePersistent(user.get)
  }
}