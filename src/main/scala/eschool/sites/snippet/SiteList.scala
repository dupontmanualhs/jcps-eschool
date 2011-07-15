package eschool.sites.snippet

import xml.NodeSeq
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST._
import eschool.users.model.User
import net.liftweb.common._
import eschool.sites.model.Site
import net.liftweb.http.{RequestVar, S}

object SiteList {
  object reqUser extends RequestVar[Box[User]](Empty)

  def render(in: NodeSeq): NodeSeq = {
    val user: User = reqUser openOr {
      S.error(<p>You somehow tried to get a user's site list without being logged in or specifying a user.</p>
              <p>That shouldn't happen</p>)
      S.redirectTo("/error")
    }
    val sites = Site.findAll("owner" -> user.id.asJValue)
    if (sites.isEmpty) {
      <p>You do not have any sites, yet.</p>
    } else {
      <ul>{ sites.flatMap((s: Site) => <li>{ s.name }</li>) }</ul>
    }
  }
}