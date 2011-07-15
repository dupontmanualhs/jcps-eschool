package eschool.sites.snippet

import eschool.sites.model.Page
import net.liftweb.common.{Box, Empty}
import xml.NodeSeq
import net.liftweb.http.{S, RequestVar}
import net.liftweb.util._
import net.liftweb.util.Helpers._

object SitePage {
  object reqPage extends RequestVar[Box[Page]](Empty)

  def render(in: NodeSeq): NodeSeq = {
    val page = reqPage openOr  {
      S.error(<p>You somehow tried to view a page without specifying it.</p>)
      S.redirectTo("/error")
    }
    (".title *" #> page.name.get &
      ".content" #> page.content.get)(in)
  }
}