package eschool.sites.model

import net.liftweb.json.JsonDSL._
import net.liftweb.record.field.StringField
import eschool.users.model.User
import eschool.utils.model.HtmlField
import net.liftweb.mongodb.record.field._
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import org.bson.types.ObjectId
import net.liftweb.common.{Full, Failure, Empty, Box}

class Site extends MongoRecord[Site] with ObjectIdPk[Site] {
  def meta = Site

  object owner extends ObjectIdRefField[Site, User](this, User)
  object name extends StringField(this, 80)
  object ident extends StringField(this, 10)
  object pages extends MongoMapField[Site, ObjectId](this)
}

object Site extends Site with MongoMetaRecord[Site] {
  ensureIndex(("owner" -> 1) ~ ("name" -> 1), "unique" -> 1)
  ensureIndex(("owner" -> 1) ~ ("ident" -> 1), "unique" -> 1)
}

class Page extends MongoRecord[Page] with ObjectIdPk[Page] {
  def meta = Page

  object name extends StringField(this, 80)
  object content extends HtmlField(this)
  object pages extends MongoMapField[Page, ObjectId](this)
}

object Page extends Page with MongoMetaRecord[Page] {
  // TODO: indices
}

case class PagePath(
  userBox: Box[User],
  siteBox: Box[Site],
  pageBox: Box[Page],
  pathList: List[String]) {

  def encode: List[String] = Nil

  def encoder: List[String] = userBox match {
    case Full(user) => siteBox match {
      case Full(site) => user.username.get :: site.ident.get :: pathList
      case _ => List(user.username.get)
    }
    case _ => Nil
  }
}

object PagePath {
  def apply(path: List[String]): Box[PagePath] = path match {
    case Nil => Full(PagePath(Empty, Empty, Empty, Nil))
    case List("some", "random", "path") => Full(PagePath(Empty, Empty, Empty, List("blah")))
    case username :: sitePlusPage => {
      User.getByUsername(username) match {
        case Full(user) => sitePlusPage match {
          case Nil => Full(PagePath(Full(user), Empty, Empty, Nil))
          case siteIdent :: pagePath => Site.find(("owner" -> user.id.asJValue) ~ ("ident" -> siteIdent)) match {
            case Full(site) => pagePath match {
              case Nil => Full(PagePath(Full(user), Full(site), Empty, Nil))
              case topPageIdent :: restOfPath => {
                def followPath(current: Option[Page], path: List[String]): Box[PagePath] = current match {
                  case Some(page) => path match {
                    case Nil => Full(PagePath(Full(user), Full(site), Full(page), pagePath))
                    case next :: rest => followPath(Page.find(page.pages.get(next)), rest)
                  }
                  case _ => Failure("There is no page with the given path.")
                }
                val topPage: Option[Page] = site.pages.get.get(topPageIdent) match {
                  case Some(pageId) => Page.find(pageId) match {
                    case Full(page) => Some(page)
                    case _ => None
                  }
                  case None => None
                }
                followPath(topPage, restOfPath)
              }
            }
            case _ => Failure("The user has no site with the identifier " + siteIdent)
          }
        }
        case _ => Failure("There is no user with the username " + username)
      }
    }
  }
}
