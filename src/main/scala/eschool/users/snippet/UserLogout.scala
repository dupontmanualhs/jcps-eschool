package eschool.users.snippet

import xml.NodeSeq

import net.liftweb.common._
import net.liftweb.http.S

import eschool.users.model.{User, UserUtil}
import net.liftweb.sitemap.Loc.Snippet

object UserLogout {
  def render(in: NodeSeq) = {
    UserUtil.getCurrent match {
      case Full(u) => UserUtil.logout()
      case _ => ()
    }
    S.redirectTo("/index")
  }
}