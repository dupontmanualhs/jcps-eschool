package eschool.sites.snippet

import xml.NodeSeq
import eschool.users.model.User
import eschool.utils.Helpers.pluralizeInformal
import net.liftweb.common._
import eschool.sites.model.Site
import net.liftweb.http.{RequestVar, S}
import net.liftweb.json.JsonDSL._
import net.liftweb.util._
import net.liftweb.util.Helpers._

object SiteList {
  object reqUser extends RequestVar[Box[User]](Empty)

  def render(in: NodeSeq): NodeSeq = {
    val user: User = reqUser openOr {
      S.error(<p>You somehow tried to get a user's site list without being logged in or specifying a user.</p>
              <p>That shouldn't happen</p>)
      S.redirectTo("/error")
    }
    val currentUser_? = reqUser.get == User.getCurrent
    val header: String = if (currentUser_?) {
      "Your"
    } else {
      user.displayName + "'s"
    } + " Sites"
    val sites = Site.findAll("owner" -> user.id.asJValue)
    val userHasSites: String = (if (currentUser_?) "You have" else user.displayName + " has") +
      (if (sites.isEmpty) " no sites." else " the following " + pluralizeInformal(sites.length, "site") + ":")
    val listOfSites = if (sites.isEmpty) {
      <br/>
    } else {
      <ul>{ sites.flatMap((s: Site) => <li>{ s.name }</li>) }</ul>
    }
    (".header *" #> header &
     ".userHasSites" #> userHasSites &
     ".listOfSites" #> listOfSites)(in)
  }
}