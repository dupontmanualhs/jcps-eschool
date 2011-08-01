package eschool.sites.snippet

import eschool.users.model.User
import eschool.utils.Helpers.{pluralizeInformal, getTemplate}
import net.liftweb.common._
import eschool.sites.model.Site
import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.LocPath._
import net.liftweb.sitemap.{*, Menu}
import net.liftweb.sitemap.Loc._

import com.foursquare.rogue.Rogue._
import net.liftweb.http._
import java.lang.Boolean
import xml.NodeSeq

class SiteMaster(path: List[String]) {
  def render(in: NodeSeq): NodeSeq = {
    path match {
      case Nil => listSites(User.getCurrentOrRedirect())
      case "createSite" :: Nil => {
        <head_merge><title>Create a Site</title></head_merge>
        <div class="lift:CreateSite"></div>
      }
      case username :: pathToPage => User where (_.username eqs username) get() match {
        case None => {
          S.error("There is no user with the username: " + username)
          S.redirectTo(S.referer openOr "/")
        }
        case Some(user) => listSites(user)
      }
    }
  }

  def listSites(user: User): NodeSeq = {
    val currentUser_? : Boolean = User.getCurrent.isDefined &&
        User.getCurrent.get.id.get == user.id.get
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
    val createSite = if (currentUser_?) {
      <p><a href="/sites/createSite" class="createLink">Create a New Site</a></p>
    } else {
      NodeSeq.Empty
    }
    (".header *" #> header &
     ".userHasSites" #> userHasSites &
     ".listOfSites" #> listOfSites &
     ".createSite" #> createSite)(getTemplate(List("sites", "list")))
  }
}