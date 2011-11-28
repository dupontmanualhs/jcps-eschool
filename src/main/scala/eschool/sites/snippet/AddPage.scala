package eschool.sites.snippet

import scala.collection.JavaConversions._
import scala.collection.immutable.ListMap

import eschool.sites.model.{Page, Site}
import eschool.users.model.IUser
import eschool.users.model.jdo.User
import net.liftweb.util.FieldError
import net.liftweb.common._
import net.liftweb.http.S
import xml._
import eschool.utils.snippet.EditorScreen
import bootstrap.liftweb.DataStore

class AddPage(userSiteAndMaybePage: (User, Site, Option[Page])) extends EditorScreen {
  object currentUser extends ScreenVar[User](IUser.getCurrentOrRedirect)
  
  val (user: User, site: Site, maybePage: Option[Page]) = userSiteAndMaybePage
  println("user: " + user.toString)
  println("site: " + site.toString)
  println("page: " + maybePage.toString)
  
  if (currentUser.get.getId != user.getId) {
    S.error("You do not have permission to add a page to this site.")
    S.redirectTo(S.referer openOr "/index")
  }
  
  val pathToParent: List[String] = maybePage match {
    case Some(page) => "sites" :: page.path()
    case None => "sites" :: user.getUsername :: site.ident :: Nil
  }
  val parent: Either[Site, Page] = maybePage match {
    case Some(page) => Right(page)
    case None => Left(site)
  }

  val ident = text("Page Path: " + pathToParent.mkString("/", "/", "/"), "",
      validateIdent _,
      (s: String) => boxStrToListFieldError(Page.uniqueIdent(parent, s)))
  val name = text("Page Name", "",
      validatePage _,
      (s: String) => boxStrToListFieldError(Page.uniqueName(parent, s)))
  val content = mceTextarea("Content", "", 30, 80)

  def finish() {
	val newPage: Page = new Page()
	newPage.name = name.get
    newPage.content = content.get
    parent match {
      case Left(site: Site) => {
        site.children = site.children :+ newPage
      }
      case Right(page: Page) => {
    	page.children = page.children :+ newPage
      }
    }
    DataStore.pm.makePersistent(newPage)
  }
}