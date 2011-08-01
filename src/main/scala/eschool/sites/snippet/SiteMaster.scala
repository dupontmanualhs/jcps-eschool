package eschool.sites.snippet

import xml.NodeSeq
import eschool.users.model.User
import eschool.utils.Helpers.pluralizeInformal
import net.liftweb.common._
import eschool.sites.model.Site
import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.LocPath._
import net.liftweb.http._
import net.liftweb.sitemap.{*, Menu}
import net.liftweb.sitemap.Loc._

import com.foursquare.rogue.Rogue._

class SiteMaster(path: List[String]) {
  def render(in: NodeSeq): NodeSeq = {
    path match {
      case Nil =>
    }

    val user = S.request match {
      case Full(req) => req.path.wholePath match {
        case "sites" :: Nil => User.getCurrentOrRedirect()
        case _ => listWithUser.toLoc.currentValue.openOr {
          S.error("You got to the site list page with no user set. This shouldn't have happened.")
          S.redirectTo("/error")
        }
      }
      case _ => {
        S.error("You got to the site list page without a request. This shouldn't have happened.")
        S.redirectTo("/error")
      }
    }
    val currentUser_? = User.getCurrent.isDefined && User.getCurrent.get.id.get == user.id.get
    val header: String = (if (currentUser_?) {
      "Your"
    } else {
      user.displayName + "'s"
    }) + " Sites"
    val sites = Site where (_.owner eqs user.id.get) fetch()
    val userHasSites: String = (if (currentUser_?) "You have" else user.displayName + " has") +
      (if (sites.isEmpty) " no sites." else " the following " + pluralizeInformal(sites.length, "site") + ":")
    val listOfSites = if (sites.isEmpty) {
      <br/>
    } else {
      <ul>
      { sites.flatMap(
        (s: Site) =>
        <li><a href={ "/sites/%s/%s".format(user.username, s.ident)}>{ s.name }</a></li>
      )}
      </ul>
    }
    val cssFunc = ".header *" #> header &
        ".userHasSites" #> userHasSites &
        ".listOfSites" #> listOfSites
    val funcWithCreate = if (currentUser_?) { // TODO: should be based on permissions
      cssFunc
    } else {
      cssFunc & ".createLink" #> NodeSeq.Empty
    }
    funcWithCreate(in)
  }
}