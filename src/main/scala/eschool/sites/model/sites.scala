package eschool.sites.model

import net.liftweb.common._

import bootstrap.liftweb.DataStore

object SiteUtil {
  def getPages(site: Site): Map[String, Page] = {
    val cand = QPage.candidate
    Map(DataStore.pm.query[Page].filter(cand.parentSite.eq(site)).executeList().map((page: Page) => (page.getIdent, page)): _*)
  }
}

object PageUtil {
  def getParent(page: Page): Either[Site, Page] = {
    page.getParentSite() match {
      case null => page.getParentPage() match {
        case null => throw new Exception("Page with id %s does not have a parent!".format(page.getId))
        case page: Page => Right(page)
      }
      case site: Site => Left(site)
    }
  }
  
  def getPages(page: Page): Map[String, Page] = {
    val cand = QPage.candidate
    Map(DataStore.pm.query[Page].filter(cand.parentPage.eq(page)).executeList().map((page: Page) => (page.getIdent, page)): _*)
  }

  /**
   * Produces the path to page (including the username and site-ident)
   */
  def getPath(page: Page): List[String] = {
    getPathPlusSuffix(page, Nil)
  }

  private def getPathPlusSuffix(page: Page, suffix: List[String]): List[String] = {
    page.getParentPage() match {
      case null => page.getParentSite() match {
        case null => Nil // TODO: log this (page has no parent)
        case site: Site => site.getOwner.getUsername :: site.getIdent :: page.getIdent :: suffix
      }
      case parentPage: Page => getPathPlusSuffix(parentPage, page.getIdent :: suffix)
    }
  }

  def fromSiteAndPath(site: Site, path: List[String]): Box[Page] = path match {
    case Nil => Failure("How did I get here without a page path?")
    case topPageIdent :: restOfPath => {
      def followPath(current: Option[Page], path: List[String]): Box[Page] = current match {
        case Some(page) => path match {
          case Nil => Full(page)
          case next :: rest => followPath(PageUtil.getPages(page).get(next), rest)
        }
        case _ => Failure("There is no page with the given path.")
      }
      val topPage: Option[Page] = SiteUtil.getPages(site).get(topPageIdent)
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
    val cand = QPage.candidate
    parent match {
      case Left(parentSite) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentSite.eq(parentSite).and(cand.ident.eq(possIdent))).executeOption()
        errorIfSome(possPage, "identifier")
      }
      case Right(parentPage) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentPage.eq(parentPage).and(cand.ident.eq(possIdent))).executeOption()
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
      case Left(parentSite) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentSite.eq(parentSite).and(cand.name.eq(possName))).executeOption()
        errorIfSome(possPage, "name")
      }
      case Right(parentPage) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentPage.eq(parentPage).and(cand.name.eq(possName))).executeOption()
        errorIfSome(possPage, "name")
      }
    }
  }
}