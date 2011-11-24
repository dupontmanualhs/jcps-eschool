package eschool.sites.snippet

import eschool.sites.model.jdo.{Page, Site}
import eschool.users.model.jdo.User
import net.liftweb.common.{Box, Empty}
import xml.NodeSeq
import net.liftweb.util._
import net.liftweb.util.Helpers._

class SitePage(userSiteAndPage: (User, Site, Page)) {
  def render = {
    val page = userSiteAndPage._3
    ".title *" #> page.getName &
    ".content" #> page.getContent
  }
}