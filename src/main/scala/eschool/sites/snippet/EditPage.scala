package eschool.sites.snippet

import eschool.users.model.User
import eschool.sites.model.{Page, Site}
import net.liftweb.http.{LiftScreen, S}
import net.liftweb.util.FieldError
import net.liftweb.common.Full

import com.foursquare.rogue.Rogue._
import xml.{XML, Elem, NodeSeq}

class EditPage(userSiteAndPage: (User, Site, Page)) extends LiftScreen {
  object currentUser extends ScreenVar[User](User.getCurrentOrRedirect())
  val (user: User, site: Site, page: Page) = userSiteAndPage
  var contentNodeSeq: NodeSeq = page.content.get
  if (currentUser.id.get != user.id.get) {
    S.error("You do not have permission to edit that page.")
    S.redirectTo(S.referer openOr "/index")
  }

  val name = text("Page Name", page.name.get,
      valMinLen(1, "The page name must be at least one character."),
      valMaxLen(80, "The page name must be 80 characters or fewer."),
      uniqueName _)
  val content = textarea("Content", page.content.get.toString, 30, 80, validHtml _)

  def finish() {
    page.name(name.get).content(contentNodeSeq).save(true)
  }

  def validHtml(s: String): List[FieldError] = {
    try {
      val el: Elem = XML.loadString("<dummy>" + s + "</dummy>")
      contentNodeSeq = el.child
      Nil
    } catch {
      case e: Exception => "Content must be valid (X)HTML."
    }
  }

  def uniqueName(s: String): List[FieldError] = {
    if (s != page.name.get) {
      page.parentPage.valueBox match {
        case Full(oid) => {
          Page where (_.parentPage eqs oid) and (_.name eqs s) fetch() match {
            case Nil => Nil
            case _ => "You already have a page with that name."
          }
        }
        case _ => page.parentSite.valueBox match {
          case Full(oid) => {
            Page where (_.parentSite eqs oid) and (_.name eqs s) fetch() match {
              case Nil => Nil
              case _ => "You already have a page with that name."
            }
          }
          case _ => throw new Exception("We have a page without a parent. That shouldn't happen.")
        }
      }
    } else {
      Nil
    }
  }
}