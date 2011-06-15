package eschool.users.snippet

import xml.NodeSeq

import net.liftweb.common._
import net.liftweb.http.S

import eschool.users.model.User
import net.liftweb.sitemap.Loc.Snippet

object UserLogout {
  def render(in: NodeSeq) = {
    User.current.is match {
      case Full(u) => u.logout()
      case _ => ()
    }
    S.redirectTo("/index")
  }
}