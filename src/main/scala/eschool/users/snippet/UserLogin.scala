package eschool.users.snippet

import xml.Elem

import net.liftweb.common._
import net.liftweb.http.{S, LiftScreen}

import eschool.users.model.User

object UserLogin extends LiftScreen {
  val username = field("Username", "")
  val passwd = password("Password", "")

  def finish() {
    User.authenticate(username, passwd) match {
      case Full(u) => {
        u.login()
        S.redirectTo("/index")
      }
      case _ => S.error("Incorrect username or password")
    }
  }
}
