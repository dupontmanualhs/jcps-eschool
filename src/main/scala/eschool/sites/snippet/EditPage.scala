package eschool.sites.snippet

import eschool.users.model.{User, UserUtil}
import eschool.sites.model.{Page, PageUtil, Site, SiteUtil}
import net.liftweb.util.FieldError
import net.liftweb.common.{Box, Full}
import net.liftweb.http.S
import xml._
import eschool.utils.snippet.EditorScreen
import bootstrap.liftweb.DataStore

class EditPage(userSiteAndPage: (User, Site, Page)) extends EditorScreen {
  object currentUser extends ScreenVar[User](UserUtil.getCurrentOrRedirect())
  val (user: User, site: Site, page: Page) = userSiteAndPage
  if (currentUser.getId != user.getId) {
    S.error("You do not have permission to edit that page.")
    S.redirectTo(S.referer openOr "/index")
  }

  val name = text("Page Name", page.getName,
      validatePage _,
      (s: String) => boxStrToListFieldError(PageUtil.uniqueName(PageUtil.getParent(page), s)))
  val content = mceTextarea("Content", page.getContent.get.toString, 30, 80)

  def finish() {
    page.setName(name.get)
    page.setContent(content.get)
    DataStore.pm.makePersistent(page)
  }
}