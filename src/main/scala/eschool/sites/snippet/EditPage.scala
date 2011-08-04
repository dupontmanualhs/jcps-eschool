package eschool.sites.snippet

import eschool.users.model.User
import eschool.sites.model.{Page, Site}
import net.liftweb.util.FieldError
import com.foursquare.rogue.Rogue._
import net.liftweb.common.{Box, Full}
import net.liftweb.http.S
import xml._
import eschool.utils.snippet.EditorScreen

class EditPage(userSiteAndPage: (User, Site, Page)) extends EditorScreen {
  object currentUser extends ScreenVar[User](User.getCurrentOrRedirect())
  val (user: User, site: Site, page: Page) = userSiteAndPage
  if (currentUser.id.get != user.id.get) {
    S.error("You do not have permission to edit that page.")
    S.redirectTo(S.referer openOr "/index")
  }

  val name = text("Page Name", page.name.get,
      validatePage _,
      (s: String) => boxStrToListFieldError(Page.uniqueName(page.parent, s)))
  val content = mceTextarea("Content", page.content.get.toString, 30, 80)

  def finish() {
    page.name(name.get).content(XML.loadString("<dummy>" + content.get + "</dummy>").child).save(true)
  }
}