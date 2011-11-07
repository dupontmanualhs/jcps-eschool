package eschool.sites.snippet

import scala.collection.JavaConversions._
import scala.collection.immutable.ListMap

import eschool.sites.model.{Page, PageUtil, Site, SiteUtil}
import eschool.users.model.{User, UserUtil}
import net.liftweb.util.FieldError
import net.liftweb.common._
import net.liftweb.http.S
import xml._
import eschool.utils.snippet.EditorScreen
import bootstrap.liftweb.DataStore

class AddPage(userSiteAndMaybePage: (User, Site, Option[Page])) extends EditorScreen {
  object currentUser extends ScreenVar[User](UserUtil.getCurrentOrRedirect)
  
  val (user: User, site: Site, maybePage: Option[Page]) = userSiteAndMaybePage
  if (currentUser.get.getId != user.getId) {
    S.error("You do not have permission to add a page to this site.")
    S.redirectTo(S.referer openOr "/index")
  }
  
  val pathToParent: List[String] = maybePage match {
    case Some(page) => "sites" :: PageUtil.getPath(page)
    case None => "sites" :: user.getUsername :: site.getIdent :: Nil
  }
  val parent: Either[Site, Page] = maybePage match {
    case Some(page) => Right(page)
    case None => Left(site)
  }
  object newPage extends ScreenVar[Page](new Page())

  val ident = text("Page Path: " + pathToParent.mkString("/", "/", "/"), "",
      validateIdent _,
      (s: String) => boxStrToListFieldError(PageUtil.uniqueIdent(parent, s)))
  val name = text("Page Name", "",
      validatePage _,
      (s: String) => boxStrToListFieldError(PageUtil.uniqueName(parent, s)))
  val content = mceTextarea("Content", "", 30, 80)

  def finish() {
    newPage.setIdent(ident.get)
    newPage.setName(name.get)
    newPage.setContent(content.get)
    DataStore.pm.makePersistent(newPage)
    parent match {
      case Left(site) => site.getChildren.put(ident, newPage)
      case Right(page) => page.getChildren.put(ident, newPage)
    }

  }
}