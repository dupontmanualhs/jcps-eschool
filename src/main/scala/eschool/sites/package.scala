package eschool

import sites.model.{Site, Page}
import sites.snippet.{SiteList, PageMap, SitePage}
import users.model.User
import net.liftweb.sitemap.{Loc, *, ConvertableToMenu, Menu}
import net.liftweb.util.Helpers._
import net.liftweb.common.{Box, Empty, Full}
import net.liftweb.http.{NotFoundResponse, Templates, MVCHelper}
import net.liftweb.json.JsonDSL._

package object sites {
  /*def menus: Array[ConvertableToMenu] = Array(
    Sites.menu
  )*/

  object Dispatch extends MVCHelper {
    serve {
      case "sites" :: pathToPage => pathToPage match {
        case username :: sitePlusPage => {
          User.getByUsername(username) match {
            case Full(user) => sitePlusPage match {
              case Nil => {
                SiteList.reqUser(Full(user))
                Templates(List("sites", "index"))
              }
              case siteIdent :: pagePath => Site.find(("owner" -> user.id.asJValue) ~ ("ident" -> siteIdent)) match {
                case Full(site) => pagePath match {
                  case Nil => {
                    PageMap.reqSite(Full(site))
                    Templates(List("sites", "pageMap"))
                  }
                  case topPageIdent :: restOfPath => {
                    def followPath(current: Option[Page], path: List[String]): MVCResponse = current match {
                      case Some(page) => path match {
                        case Nil => {
                          SitePage.reqPage(Full(page))
                          Templates(List("sites", "page"))
                        }
                        case next :: rest => followPath(Page.find(page.pages.get(next)), rest)
                      }
                      case _ => NotFoundResponse("There is no page with the given path.")
                    }
                    val topPage: Option[Page] = site.pages.get.get(topPageIdent) match {
                      case Some(pageId) => Page.find(pageId) match {
                        case Full(page) => Some(page)
                        case _ => None
                      }
                      case None => None
                    }
                    followPath(topPage, restOfPath)
                  }
                }
                case _ => NotFoundResponse("The user has no site with the identifier " + siteIdent)
              }
            }
            case _ => NotFoundResponse("There is no user with the username " + username)
          }
        } case Nil => {
            SiteList.reqUser(Full(User.getCurrentOrRedirect()))
            Templates(List("sites", "index"))
        }
      }
    }
  }
}