package eschool.sites.snippet

import xml.NodeSeq
import net.liftweb.http.{S, RequestVar}
import eschool.users.model.{User, UserUtil}
import eschool.sites.model.{Page, PageUtil, Site, SiteUtil}
import net.liftweb.common.{Full, Empty, Box}
import net.liftweb.util.Helpers._

class PageMap(userAndSite: (User, Site)) {
  def render(in: NodeSeq): NodeSeq = {
    val (owner: User, site: Site) = userAndSite
    val currentUser_? = UserUtil.getCurrent.isDefined && UserUtil.getCurrent.get.getId == owner.getId
    val siteName = site.getName
    val ownerDisplayName = owner.displayName
    val linkOtherSites = <a href={ "/sites/%s".format(owner.getUsername) }>other sites</a>
    def insertCommand(pathToPage: String, command: String): String = {
      val prefix = "/sites"
      pathToPage.substring(0, prefix.length) + "/" + command + pathToPage.substring(prefix.length)
    }
    def pageHierarchy(pathToPage: String, maybePage: Box[Page]): NodeSeq = maybePage match {
      case Full(page) => {
        val subPages = PageUtil.getPages(page)
        <li><a href={ pathToPage }>{ page.getName }</a>
          { if (currentUser_?) {
            <a href={ insertCommand(pathToPage, "edit") }>(edit)</a>
          } else {
            NodeSeq.Empty
          } }
          { if (subPages.isEmpty && !currentUser_?) {
            NodeSeq.Empty
          } else {
            <ul>{ subPages.flatMap {
              (identPlusPage: (String, Page)) => {
                val (ident: String, page: Page) = identPlusPage
                pageHierarchy("%s/%s".format(pathToPage, ident), Full(page))
              }
            } }
            { if (currentUser_?) {
              <li><a href={ insertCommand(pathToPage, "add") }>Add Page</a></li>
            } else {
              NodeSeq.Empty
            }
            }</ul>
          }
        }</li>
      }
      case _ => NodeSeq.Empty
    }
    val pageList =
      <ul>{ SiteUtil.getPages(site).flatMap {
        (identPlusPage: (String, Page)) => {
          val (ident: String, page: Page) = identPlusPage
          pageHierarchy("/sites/%s/%s/%s".format(owner.getUsername, site.getIdent, ident), Full(page))
        }}
      }
      { if (currentUser_?) {
        <li><a href={ "/sites/add/%s/%s".format(owner.getUsername, site.getIdent) }>Add Page</a></li>
      } else {
        NodeSeq.Empty
      }}</ul>
    (".siteName *" #> siteName &
        ".ownerDisplayName" #> ownerDisplayName &
        ".linkOtherSites" #> linkOtherSites &
        ".pageList" #> pageList)(in)
  }
}