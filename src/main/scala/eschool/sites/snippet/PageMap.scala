package eschool.sites.snippet

import xml.NodeSeq
import net.liftweb.http.{S, RequestVar}
import org.bson.types.ObjectId
import eschool.users.model.User
import eschool.sites.model.{Page, Site}
import net.liftweb.common.{Full, Empty, Box}
import net.liftweb.util.Helpers._
import org.ietf.jgss.Oid

object PageMap {
  object reqSite extends RequestVar[Box[Site]](Empty)

  def render(in: NodeSeq): NodeSeq = {
    val site = reqSite openOr {
      S.error(<p>You somehow got to a site's page map without specifying the site.</p>)
      S.redirectTo("/error")
    }
    val owner = site.owner.obj openOr {
      S.error(<p>This site doesn't have a link to its owner. That's a big error.</p>)
      S.redirectTo("/error")
    }
    val currentUser_? = User.getCurrent.isDefined && User.getCurrent.get.id.get == owner.id.get
    val siteName = site.name.get
    val ownerDisplayName = owner.displayName
    val linkOtherSites = <a href={ "/sites/%s".format(owner.username.get) }>other sites</a>
    def pageHierarchy(pathToPage: String, maybePage: Box[Page]): NodeSeq = maybePage match {
      case Full(page) => {
        val subPages = page.pages.get
        <li><a href={ pathToPage }>{ page.name.get }</a>
          {  if (subPages.isEmpty) {
            NodeSeq.Empty
          } else {
            <ul>{ subPages.flatMap {
              (identPlusOid: (String, ObjectId)) => {
                val (ident: String, oid: ObjectId) = identPlusOid
                pageHierarchy("%s/%s".format(pathToPage, ident), Page.find(oid))
              }
            } }</ul>
          }
        }</li>
      }
      case _ => NodeSeq.Empty
    }
    val pageList = <ul>{ site.pages.get.flatMap {
      (identPlusOid: (String, ObjectId)) => {
        val (ident: String, oid: ObjectId) = identPlusOid
        pageHierarchy("/sites/%s/%s/%s".format(owner.username.get, site.ident.get, ident), Page.find(oid))
      }
    } }</ul>
    (".siteName *" #> siteName &
        ".ownerDisplayName" #> ownerDisplayName &
        ".linkOtherSites" #> linkOtherSites &
        ".pageList" #> pageList)(in)
  }
}