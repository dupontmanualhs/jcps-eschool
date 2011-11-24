package eschool

import eschool.utils.Helpers.getTemplate
import bootstrap.liftweb.DataStore
import users.model.IUser
import users.model.jdo.User
import sites.model.IPage
import sites.model.jdo.{QSite, Site, Page}
import net.liftweb.common.Box.option2Box

import net.liftweb.sitemap.{*, **, Menu, ConvertableToMenu}
import xml.NodeSeq
import net.liftweb.http.{RedirectResponse, Templates, S}
import net.liftweb.common.{Failure, Full, Box, Empty}
import net.liftweb.sitemap.Loc._

package object sites {
  val siteLoc = new DataLoc[User]("Sites", new Link[User](List("sites")),
      "Sites", Empty, If(() => IUser.loggedIn_?, "You must log in to access your sites.")) {
    override def overrideValue: Box[User] = IUser.getCurrent
    override def calcTemplate: Box[NodeSeq] = Templates(List("sites", "list"))
  }

  def menus: Array[ConvertableToMenu] = Array(
    Menu(siteLoc,
      Menu.i("Create Site") / "sites" / "createSite" >>
        Hidden >> If(() => IUser.loggedIn_?, "You must log in to create a new site."),
      Menu.params[(User, Site, Page)]("Edit Page", "Edit Page",
        parseUserSiteAndPage _, encodeUserSiteAndPage _) / "sites" / "edit" / * / * / * / ** >>
        Template(() => getTemplate(List("sites", "editPage"))) >>
        Hidden >> If(() => IUser.loggedIn_?, "You must be logged in to edit pages."),
      Menu.params[(User, Site, Option[Page])]("Add Page", "Add Page",
        parseUserSiteAndMaybePage _, encodeUserSiteAndMaybePage _) / "sites" / "add" / * / * / ** >>
        Template(() => getTemplate(List("sites", "addPage"))) >>
        Hidden >> If(() => IUser.loggedIn_?, "You must be logged in to add pages.")),
    Menu.param[User]("User's Sites", "User's Sites",
        parseUser _, _.getUsername) / "sites" / * >>
        Template(() => getTemplate(List("sites", "list"))) >>
        Hidden,
    Menu.params[(User, Site)]("Page Map", "Page Map",
        parseUserAndSite _, encodeUserAndSite _) / "sites" / * / * >>
        Template(() => getTemplate(List("sites", "pageMap"))) >>
        Hidden,
    Menu.params[(User, Site, Page)]("Page", "Page",
        parseUserSiteAndPage _, encodeUserSiteAndPage _) / "sites" / * / * / * / ** >>
        Template(() => getTemplate(List("sites", "page"))) >>
        Hidden
  )

  def parseUser(name: String): Box[User] = IUser.getByUsername(name) match {
    case Empty => Failure("There is no user with the username " + name)
    case other => other 
  }

  def parseUserAndSite(userAndSite: List[String]): Box[(User, Site)] = userAndSite match {
    case username :: siteIdent :: Nil => parseUser(username) match {
      case Full(user) => {
        val cand = QSite.candidate
        DataStore.pm.query[Site].filter(cand.owner.eq(user).and(cand.ident.eq(siteIdent))).executeOption() match {
          case Some(site) => Full((user, site))
          case _ => Failure("There is no site with the name " + siteIdent)
        }
      }
      case other => other.asInstanceOf[Box[(User, Site)]]
    }
    case _ => Failure("How did this even match the pattern?")
  }

  def encodeUserAndSite(userAndSite: (User, Site)): List[String] = {
    val (user: User, site: Site) = userAndSite
    List(user.getUsername, site.getIdent)
  }

  def parseUserSiteAndPage(userSiteAndPage: List[String]): Box[(User, Site, Page)] = {
    userSiteAndPage match {
      case username :: siteIdent :: pagePath => parseUserAndSite(List(username, siteIdent)) match {
        case Full((user, site)) => IPage.fromSiteAndPath(site, pagePath) match {
          case Full(page) => Full((user, site, page))
          case _ => Failure("There is no page with the given path.")
        }
        case other => other.asInstanceOf[Box[(User, Site, Page)]]
      }
      case _ => Failure("How did this even match the pattern?")
    }
  }

  def encodeUserSiteAndPage(userSiteAndPage: (User, Site, Page)): List[String] = {
    val (user: User, site: Site, page: Page) = userSiteAndPage
    IPage.getPath(page)
  }

  def parseUserSiteAndMaybePage(userSiteAndMaybePage: List[String]): Box[(User, Site, Option[Page])] = {
    userSiteAndMaybePage match {
      case username :: siteIdent :: pagePath => parseUserAndSite(List(username, siteIdent)) match {
        case Full((user, site)) => pagePath match {
          case Nil => Full((user, site, None))
          case _ => IPage.fromSiteAndPath(site, pagePath) match {
            case Full(page) => Full((user, site, Some(page)))
            case _ => Failure("There is no page with the given path.")
          }
        }
        case other => other.asInstanceOf[Box[(User, Site, Option[Page])]]
      }
      case _ => Failure("How did this even match the pattern?")
    }
  }

  def encodeUserSiteAndMaybePage(userSiteAndMaybePage: (User, Site, Option[Page])): List[String] = {
    val (user: User, site: Site, maybePage: Option[Page]) = userSiteAndMaybePage
    maybePage match {
      case Some(page) => encodeUserSiteAndPage((user, site, page))
      case None => encodeUserAndSite((user, site))
    }
  }
}