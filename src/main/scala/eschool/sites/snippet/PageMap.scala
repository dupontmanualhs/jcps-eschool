package eschool.sites.snippet

import xml.NodeSeq
import net.liftweb.http.{S, RequestVar}
import org.bson.types.ObjectId
import eschool.users.model.User
import eschool.sites.model.{Page, Site}
import net.liftweb.common.{Full, Empty, Box}
import net.liftweb.util.Helpers._

class PageMap(userAndSite: (User, Site)) {
  def render(in: NodeSeq): NodeSeq = {
    val (owner: User, site: Site) = userAndSite
    val currentUser_? = User.getCurrent.isDefined && User.getCurrent.get.id.get == owner.id.get
    val siteName = site.name.get
    val ownerDisplayName = owner.displayName
    val linkOtherSites = <a href={ "/sites/%s".format(owner.username.get) }>other sites</a>
    def insertCommand(pathToPage: String, command: String): String = {
      val prefix = "/sites"
      pathToPage.substring(0, prefix.length) + "/" + command + pathToPage.substring(prefix.length)
    }
    def pageHierarchy(pathToPage: String, maybePage: Box[Page]): NodeSeq = maybePage match {
      case Full(page) => {
        val subPages = page.pages.get
        <li><a href={ pathToPage }>{ page.name.get }</a>
          { if (currentUser_?) {
            <a href={ insertCommand(pathToPage, "edit") }>(edit)</a>
          } else {
            NodeSeq.Empty
          } }
          { if (subPages.isEmpty && !currentUser_?) {
            NodeSeq.Empty
          } else {
            <ul>{ subPages.flatMap {
              (identPlusOid: (String, ObjectId)) => {
                val (ident: String, oid: ObjectId) = identPlusOid
                pageHierarchy("%s/%s".format(pathToPage, ident), Page.find(oid))
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
      <ul>{ site.pages.get.flatMap {
        (identPlusOid: (String, ObjectId)) => {
          val (ident: String, oid: ObjectId) = identPlusOid
          pageHierarchy("/sites/%s/%s/%s".format(owner.username.get, site.ident.get, ident), Page.find(oid))
        }}
      }
      { if (currentUser_?) {
        <li><a href={ "/sites/add/%s/%s".format(owner.username.get, site.ident.get) }>Add Page</a></li>
      } else {
        NodeSeq.Empty
      }}</ul>
    (".siteName *" #> siteName &
        ".ownerDisplayName" #> ownerDisplayName &
        ".linkOtherSites" #> linkOtherSites &
        ".pageList" #> pageList)(in)
  }
}