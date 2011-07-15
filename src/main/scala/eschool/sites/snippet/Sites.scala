package eschool.sites.snippet

import eschool.sites.model.PagePath._
import net.liftweb.sitemap.Loc.Unless._
import eschool.users.model.User
import net.liftweb.sitemap.Loc._
import xml.NodeSeq
import net.liftweb.common.{Empty, Failure, Full}
import eschool.sites.model.Site
import net.liftweb.json.JsonDSL._
import net.liftweb.sitemap.{Loc, Menu}
import net.liftweb.http.{NotFoundResponse, ResponseShortcutException, Templates, S}
import net.liftweb.sitemap.Menu.ParamsMenuable

/*object Sites {
  val menu = new ParamsMenuable[PagePath]("Page", "Page", PagePath(_), _.encode,
      List("sites"), true, List(Loc.Template(() => Templates(List("sites", "page"))
          openOr (throw ResponseShortcutException.shortcutResponse(NotFoundResponse("No such site."))))), Nil)

  lazy val loc = menu.toLoc

  def render(in: NodeSeq): NodeSeq = {
    loc.currentValue match {
      case Full(PagePath(Empty, Empty, Empty, Nil)) => siteList(User.getCurrentOrRedirect())
      case _ => {
        S.error("Not implemented yet")
        S.redirectTo("/index")
      }
    }
  }

  def siteList(user: User): NodeSeq = {
    val sites = Site.findAll(("owner" -> user.id.asJValue))
    if (sites.isEmpty) {
      <span>You currently have no sites.</span>
    } else {
      <span>You have the following { sites.size.toString } site(s):
        <ul>{ sites flatMap ((s: Site) => <li>{ s.name.get }</li>) }</ul>
      </span>
    }
  }
}*/