package eschool.sites.model

import scala.collection.JavaConverters._

import net.liftweb.common._
import bootstrap.liftweb.DataStore
import eschool.sites.model.jdo.{QPage, Page, Site}
import scala.xml.NodeSeq
import eschool.utils.Helpers

object IPage {
  /**
   * Produces the path to page (including the username and site-ident)
   */
  def fromSiteAndPath(site: Site, path: List[String]): Box[Page] = path match {
    case Nil => Failure("How did I get here without a page path?")
    case topPageIdent :: restOfPath => {
      def followPath(current: Option[Page], path: List[String]): Box[Page] = current match {
        case Some(page) => path match {
          case Nil => Full(page)
          case next :: rest => followPath(getChildren(page).find(_.getIdent == next), rest)
        }
        case _ => Failure("There is no page with the given path.")
      }
      val topPage: Option[Page] = site.getChildren.find(_.getIdent == topPageIdent)
      followPath(topPage, restOfPath)
    }
  }

  private def errorIfSome(badPage: Option[Page], problem: String): Box[String] = {
    badPage match {
      case Some(page) => Full("There is already a page with that %s.".format(problem))
      case None => Empty
    }
  }

  /**
   * validates a possible identifier for a page to make sure a page
   *   with the given identifier doesn't already exist as a child of
   *   the given Site
   * returns Nil if okay, and an appropriate error, if not
   */
  def uniqueIdent(parent: Either[Site, Page], possIdent: String): Box[String] = {
    println(parent.toString + " " + possIdent)
    val cand = QPage.candidate
    parent match {
      case Left(parentSite: Site) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentSite.eq(parentSite)).filter(cand.ident.eq(possIdent)).executeOption()
        errorIfSome(possPage, "identifier")
      }
      case Right(parentPage: Page) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentPage.eq(parentPage)).filter(cand.ident.eq(possIdent)).executeOption()
        errorIfSome(possPage, "identifier")
      }
    }
  }

  /**
   * validates a possible name for a page to make sure a page
   *   with the given name doesn't already exist as a child of
   *   the given Site
   * returns Nil if okay, and an appropriate error, if not
   */
  def uniqueName(parent: Either[Site, Page], possName: String): Box[String] = {
    val cand = QPage.candidate
    parent match {
      case Left(parentSite: Site) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentSite.eq(parentSite)).filter(cand.name.eq(possName)).executeOption()
        errorIfSome(possPage, "name")
      }
      case Right(parentPage: Page) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentPage.eq(parentPage)).filter(cand.name.eq(possName)).executeOption()
        errorIfSome(possPage, "name")
      }
    }
  }
  
  def setContent(page: Page, html: NodeSeq) {
    page.setContent(html.toString)
  }
  
  def getContent(page: Page): NodeSeq = {
    Helpers.string2nodeSeq(page.getContent())
  }
  
  def getChildren(page: Page): List[Page] = {
    page.getChildren.asScala.toList
  }
  
  def setChildren(page: Page, children: List[Page]) {
    page.setChildren(children.asJava)
  }
  
  def getParent(page: Page): Either[Site, Page] = {
	if (page.getParentSite() != null) {
	  Left(page.getParentSite());
	} else if (page.getParentPage() != null) {
	  Right(page.getParentPage());
	} else {
	  throw new Exception("Page with id %s does not have a parent!".format(page.getId));
	}
  }
  
  def getPath(page: Page, suffix: List[String] = Nil): List[String] = {
    getParent(page) match {
      case Left(site) => site.getOwner.getUsername :: site.getIdent :: page.getIdent :: suffix
      case Right(parentPage) => getPath(parentPage, page.getIdent :: suffix)
	}
  }
}
