package eschool.users.snippet

import xml.NodeSeq
import net.liftweb.common._
import eschool.users.model.User
import net.liftweb.sitemap.SiteMap

object UserLoginInfo {
  def render(in: NodeSeq) = {
    User.getCurrent match {
      case Full(u) => <span>Logged in as: { u.displayName }
                            (<span class="lift:Menu.item?name=users.logout">Logout</span>)</span>
      case _ => <span><span class="lift:Menu.item?name=users.login">Login</span></span>
    }
  }
}
