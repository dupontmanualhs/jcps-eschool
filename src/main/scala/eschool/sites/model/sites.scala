package eschool.sites.model

import net.liftweb.json.JsonDSL._
import net.liftweb.record.field.StringField
import eschool.users.model.User
import eschool.utils.model.HtmlField
import net.liftweb.mongodb.record.field._
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import org.bson.types.ObjectId
import net.liftweb.common.{Full, Failure, Empty, Box}
import com.foursquare.rogue.Rogue._
import com.mongodb.WriteConcern
import net.liftweb.util.FieldError
import javax.xml.soap.Text
import xml.{Text, NodeSeq}

class Site extends MongoRecord[Site] with ObjectIdPk[Site] {
  def meta = Site

  object owner extends ObjectIdRefField(this, User)
  object name extends StringField(this, 80)
  object ident extends StringField(this, 10)
  object pages extends MongoMapField[Site, ObjectId](this)

  override def save(concern: WriteConcern): Site = {
    super.save(concern)
    if (pages.dirty_?) {
      for ((ident, oid) <- pages.get) {
        Page.find(oid) match {
          case Full(page) => page.ident(ident).parentSite(this.id.get).save(concern)
          case _ => Unit
        }
      }
    }
    this
  }
}

object Site extends Site with MongoMetaRecord[Site] {
  ensureIndex(("owner" -> 1) ~ ("name" -> 1), "unique" -> 1)
  ensureIndex(("owner" -> 1) ~ ("ident" -> 1), "unique" -> 1)
}

class Page extends MongoRecord[Page] with ObjectIdPk[Page] {
  def meta = Page

  object parentSite extends ObjectIdRefField(this, Site) {
    override def optional_? = true
  }
  object parentPage extends ObjectIdRefField(this, Page) {
    override def optional_? = true
  }
  object ident extends StringField(this, 10)
  object name extends StringField(this, 80)
  object content extends HtmlField(this)
  object pages extends MongoMapField[Page, ObjectId](this)

  override def save(concern: WriteConcern): Page = {
    super.save(concern)
    if (pages.dirty_?) {
      for ((ident, oid) <- pages.get) {
        Page.find(oid) match {
          case Full(page) => page.ident(ident).parentPage(this.id.get).save(concern)
          case _ => Unit
        }
      }
    }
    this
  }

  def parent(): Either[Site, Page] = {
    parentSite.valueBox match {
      case Full(siteOid) => Left(Site.find(siteOid).openOr(
          throw new Exception("Page with id %s refers to parent site that doesn't exist.".format(id.get))))
      case _ => parentPage.valueBox match {
        case Full(pageOid) => Right(Page.find(pageOid).openOr(
            throw new Exception("Page with id %s refers to parent page that doesn't exist.".format(id.get))))
        case _ => (throw new Exception("Page with id %s does not have a parent!".format(id.get)))
      }
    }
  }

  /**
   * Produces the path to this page (including the username and site-ident)
   */
  def getPath(): List[String] = {
    getPathPlusSuffix(Nil)
  }

  private def getPathPlusSuffix(suffix: List[String]): List[String] = {
    parentPage.valueBox match {
      case Full(pageOid) => Page.find(pageOid) match {
        case Full(page) => page.getPathPlusSuffix(ident.get :: suffix)
        case _ => Nil // TODO: log this problem
      }
      case _ => parentSite.valueBox match {
        case Full(siteOid) => Site.find(siteOid) match {
          case Full(site) => site.owner.obj match {
            case Full(user) => user.username.get :: site.ident.get :: ident.get :: suffix
            case _ => Nil // TODO: log this (site points to owner that doesn't exist)
          }
          case _ => Nil // TODO: log this problem
        }
        case _ => Nil // TODO: log this problem
      }
    }
  }
}

object Page extends Page with MongoMetaRecord[Page] {
  // TODO: indices

  def fromSiteAndPath(site: Site, path: List[String]): Box[Page] = path match {
    case Nil => Failure("How did I get here without a page path?")
    case topPageIdent :: restOfPath => {
      def followPath(current: Option[Page], path: List[String]): Box[Page] = current match {
        case Some(page) => path match {
          case Nil => Full(page)
          case next :: rest => followPath(Page.find(page.pages.get(next)), rest)
        }
        case _ => Failure("There is no page with the given path.")
      }
      val topPage: Option[Page] = site.pages.get.get(topPageIdent) match {
        case Some(pageId) => Page where (_.id eqs pageId) get()
        case None => None
      }
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
    parent match {
      case Left(parentSite) => errorIfSome(
          Page where (_.parentSite eqs parentSite.id.get) and (_.ident eqs possIdent) get(),
          "identifier")
      case Right(parentPage) => errorIfSome(
          Page where (_.parentPage eqs parentPage.id.get) and (_.ident eqs possIdent) get(),
          "identifier")
    }
  }

  /**
   * validates a possible name for a page to make sure a page
   *   with the given name doesn't already exist as a child of
   *   the given Site
   * returns Nil if okay, and an appropriate error, if not
   */
  def uniqueName(parent: Either[Site, Page], possName: String): Box[String] = {
    parent match {
      case Left(parentSite) => errorIfSome(
          Page where (_.parentSite eqs parentSite.id.get) and (_.name eqs possName) get(),
          "name")
      case Right(parentPage) => errorIfSome(
          Page where (_.parentPage eqs parentPage.id.get) and (_.name eqs possName) get(),
          "name")
    }
  }
}