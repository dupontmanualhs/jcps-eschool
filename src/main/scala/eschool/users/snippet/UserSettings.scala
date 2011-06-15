package eschool.users.snippet

import net.liftweb.http.{S, LiftScreen}

object UserSettings extends LiftScreen {
  val placeHolder = field("This is nothing", "Don't bother")

  def finish() {
    S.notice("Type whatever you want. This doesn't do anything, yet.")
  }
}