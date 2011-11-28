package eschool.users.snippet

import xml.NodeSeq

import net.liftweb.common._

import eschool.users.model.IUser
import eschool.users.model.jdo.User

object UserLoginInfo {
  def render(in: NodeSeq) = {
    IUser.getCurrent match {
      case Full(u) => <span>Logged in as: { IUser.displayName(u) }
                            (<a href="/users/logout">Logout</a>)</span>
      case _ => <span><a href="/users/login">Login</a></span>
    }
  }
}
