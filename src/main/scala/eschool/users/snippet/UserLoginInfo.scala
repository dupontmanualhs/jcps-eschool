package eschool.users.snippet

import xml.NodeSeq

import net.liftweb.common._

import eschool.users.model.User

object UserLoginInfo {
  def render(in: NodeSeq) = {
    User.current.is match {
      case Full(u) => <span>Logged in as: { u.displayName }
                            (<a href="/users/logout.html">Logout</a>)</span>
      case _ => <span><a href="/users/login.html">Login</a></span>
    }
  }
}
