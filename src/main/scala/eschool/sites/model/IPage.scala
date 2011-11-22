package eschool.sites.model

import eschool.sites.model.jdo.Page

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
          case next :: rest => followPath(page.children.find(_.ident == next), rest)
        }
        case _ => Failure("There is no page with the given path.")
      }
      val topPage: Option[Page] = site.children.find(_.ident == topPageIdent)
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
}
