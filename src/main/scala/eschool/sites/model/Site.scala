package eschool.sites.model

import net.liftweb.json.JsonDSL._
import net.liftweb.record.field.StringField
import eschool.users.model.User
import eschool.utils.model.HtmlField
import net.liftweb.mongodb.record.field._
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import org.bson.types.ObjectId
import net.liftweb.common.{Full, Failure, Empty, Box}
import xml.NodeSeq

import com.foursquare.rogue.Rogue._
import com.mongodb.WriteConcern

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

  /**
   * Produces the path to this page (not including the username and site-ident)
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
      case _ => ident.get :: suffix
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
}
