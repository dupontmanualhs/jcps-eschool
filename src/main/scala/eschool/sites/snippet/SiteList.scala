package eschool.sites.snippet

import xml.NodeSeq
import net.liftweb.http.S
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST._
import eschool.users.model.User
import net.liftweb.common._
import eschool.sites.model.Site

object SiteList {
  def render(in: NodeSeq): NodeSeq = {
    val user: User = User.getCurrentOrRedirect()
    val sites = Site.findAll("owner" -> user.asJValue)
    if (sites.isEmpty) {
      <p>You do not have any sites, yet.</p>
    } else {
      <ul>{ sites.flatMap((s: Site) => <li>{ s.name }</li>) }</ul>
    }
  }
}