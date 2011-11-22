package eschool.users.snippet

import xml.NodeSeq

import net.liftweb.common._
import net.liftweb.http.S

import eschool.users.model.IUser
import eschool.users.model.jdo.User
import net.liftweb.sitemap.Loc.Snippet

object UserLogout {
  def render(in: NodeSeq) = {
    IUser.getCurrent match {
      case Full(u) => IUser.logout()
      case _ => ()
    }
    S.redirectTo("/index")
  }
}