package eschool.sites.snippet

import xml.NodeSeq

import net.liftweb.http._
import net.liftweb.common._
import net.liftweb.mapper._
import net.liftweb.util._
import Helpers._

import eschool.users.model.User
import eschool.sites.model.{Site, Page}

object SitePage {
  def render(in: NodeSeq): NodeSeq = {
    val noSuchPage = (
        ".title" #> "" &
        ".body" #> "There is no page at this address."
    )(in)
    val pUser: Box[String] = S.param("user")
    val pSite: Box[String] = S.param("site")
    val pPage: Box[String] = S.param("page")
    (pUser, pSite, pPage) match {
      case (Full(username), Full(siteIdent), Full(pageIdent)) => {
        User.getByUsername(username) match {
          case Full(user) => {
            Site.find(By(Site.owner, user), By(Site.ident, siteIdent)) match {
              case Full(site) => {
                Page.find(By(Page.site, site), By(Page.ident, pageIdent)) match {
                  case Full(page) => (
                      ".title" #> page.title.is &
                      ".body *" #> page.content.is
                    )(in)
                  case _ => noSuchPage
                }
              }
              case _ => noSuchPage
            }
          }
          case _ => noSuchPage
        }
      }
      case _ => noSuchPage
    }
  }
}