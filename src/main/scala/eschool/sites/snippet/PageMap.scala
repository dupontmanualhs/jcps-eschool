package eschool.sites.snippet

import xml.NodeSeq
import net.liftweb.http.{S, RequestVar}
import eschool.users.model.User
import eschool.sites.model.{Page, Site}
import net.liftweb.common.{Full, Empty, Box}
import net.liftweb.util.Helpers._

class PageMap(userAndSite: (User, Site)) {
  def render(in: NodeSeq): NodeSeq = {
    val (owner: User, site: Site) = userAndSite
    val currentUser_? = User.getCurrent.isDefined && User.getCurrent.get.id == owner.id
    val siteName = site.name
    val ownerDisplayName = owner.displayName
    val linkOtherSites = <a href={ "/sites/%s".format(owner.username) }>other sites</a>
    def insertCommand(pathToPage: String, command: String): String = {
      val prefix = "/sites"
      pathToPage.substring(0, prefix.length) + "/" + command + pathToPage.substring(prefix.length)
    }
    def pageHierarchy(pathToPage: String, maybePage: Box[Page]): NodeSeq = maybePage match {
      case Full(page) => {
        val subPages = page.children
        <li><a href={ pathToPage }>{ page.name }</a>
          { if (currentUser_?) {
            <a href={ insertCommand(pathToPage, "edit") }>(edit)</a>
            <a href={ insertCommand(pathToPage, "delete") }>(delete)</a>
          } else {
            NodeSeq.Empty
          } }
          { if (subPages.isEmpty && !currentUser_?) {
            NodeSeq.Empty
          } else {
            <ul>{ subPages.flatMap {
              (page: Page) => {
                pageHierarchy("%s/%s".format(pathToPage, page.ident), Full(page))
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
      <ul>{ site.children.flatMap {
        (page: Page) => {
          pageHierarchy("/sites/%s/%s/%s".format(owner.username, site.ident, page.ident), Full(page))
        }}
      }
      { if (currentUser_?) {
        <li><a href={ "/sites/add/%s/%s".format(owner.username, site.ident) }>Add Page</a></li>
      } else {
        NodeSeq.Empty
      }}</ul>
    (".siteName *" #> siteName &
        ".ownerDisplayName" #> ownerDisplayName &
        ".linkOtherSites" #> linkOtherSites &
        ".pageList" #> pageList)(in)
  }
}