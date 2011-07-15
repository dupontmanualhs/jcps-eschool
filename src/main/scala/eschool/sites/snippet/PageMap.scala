package eschool.sites.snippet

import eschool.sites.model.Site
import net.liftweb.common.{Empty, Box}
import xml.NodeSeq
import net.liftweb.http.{S, RequestVar}
import java.util.jar.Attributes.Name
import org.bson.types.ObjectId
import org.ietf.jgss.Oid

object PageMap {
  object reqSite extends RequestVar[Box[Site]](Empty)

  def render(in: NodeSeq): NodeSeq = {
    val site = reqSite openOr {
      S.error(<p>You somehow got to a site's page map without specifying the site.</p>)
      S.redirectTo("/error")
    }
    <ul>
    { site.pages.get.flatMap ((identPageId: (String, ObjectId)) => {
      val (ident: String, pageId: ObjectId) = identPageId
      <li>{ ident }</li>})
    }
    </ul>
  }
}