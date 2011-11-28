package eschool.users.snippet

import xml.NodeSeq

import net.liftweb.common._

import eschool.users.model.User

object UserLoginInfo {
  def render(in: NodeSeq) = {
    User.getCurrent match {
      case Full(u) => <span>Logged in as: { u.displayName }
                            (<a href="/users/logout">Logout</a>)</span>
      case _ => <span><a href="/users/login">Login</a></span>
    }
  }
}
