package eschool.sites.snippet

import eschool.users.model.IUser
import eschool.users.model.jdo.User
import eschool.sites.model.{Page, Site}
import net.liftweb.util.FieldError
import net.liftweb.common.{Box, Full}
import net.liftweb.http.S
import xml._
import eschool.utils.snippet.EditorScreen
import bootstrap.liftweb.DataStore

class EditPage(userSiteAndPage: (User, Site, Page)) extends EditorScreen {
  object currentUser extends ScreenVar[User](IUser.getCurrentOrRedirect())
  val (user: User, site: Site, page: Page) = userSiteAndPage
  if (currentUser.getId != user.getId) {
    S.error("You do not have permission to edit that page.")
    S.redirectTo(S.referer openOr "/index")
  }

  val name = text("Page Name", page.name,
      validatePage _,
      (s: String) => boxStrToListFieldError(Page.uniqueName(page.parent, s)))
  val content = mceTextarea("Content", page.content.toString, 30, 80)

  def finish() {
    page.name = name.get
    page.content = content.get
    DataStore.pm.makePersistent(page)
  }
}