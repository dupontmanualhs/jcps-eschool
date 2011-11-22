package eschool.sites.snippet

import eschool.users.model.IUser
import eschool.users.model.jdo.{QUser, User}
import eschool.utils.Helpers.{pluralizeInformal, getTemplate}
import net.liftweb.common._
import eschool.sites.model.{QSite, Site}
import net.liftweb.util.Helpers._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.LocPath._
import net.liftweb.sitemap.{*, Menu}
import net.liftweb.sitemap.Loc._

import bootstrap.liftweb.DataStore

import net.liftweb.http._
import java.lang.Boolean
import xml.NodeSeq

class SiteMaster(path: List[String]) {
  def render(in: NodeSeq): NodeSeq = {
    path match {
      case Nil => listSites(IUser.getCurrentOrRedirect())
      case "createSite" :: Nil => {
        <head_merge><title>Create a Site</title></head_merge>
        <div class="lift:CreateSite"></div>
      }
      case username :: pathToPage => {
        val cand = QUser.candidate
        DataStore.pm.query[User].filter(cand.username.eq(username)).executeOption() match {
	        case None => {
	          S.error("There is no user with the username: " + username)
	          S.redirectTo(S.referer openOr "/")
	        }
	        case Some(user) => listSites(user)
        }
      }
    }
  }

  def listSites(user: User): NodeSeq = {
    val currentUser_? : Boolean = IUser.getCurrent.isDefined &&
        IUser.getCurrent.get.getId == user.getId
    val header: String = (if (currentUser_?) {
      "Your"
    } else {
      user.displayName + "'s"
    }) + " Sites"
    val cand = QSite.candidate
    val sites = DataStore.pm.query[Site].filter(cand.owner.eq(user)).executeList()
    val userHasSites: String = (if (currentUser_?) "You have" else user.displayName + " has") +
      (if (sites.isEmpty) " no sites." else " the following " + pluralizeInformal(sites.length, "site") + ":")
    val listOfSites = if (sites.isEmpty) {
      <br/>
    } else {
      <ul>
      { sites.flatMap(
        (s: Site) =>
        <li><a href={ "/sites/%s/%s".format(user.getUsername, s.ident)}>{ s.name }</a></li>
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