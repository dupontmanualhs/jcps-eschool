package eschool.users.snippet

import net.liftweb.util._
import Helpers._
import eschool.users.model.{QUser, User}
import xml.NodeSeq
import bootstrap.liftweb.DataStore

object ContactList {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    val email = user.email match {
      case None => NodeSeq.Empty
      case Some(address)  => <a href={ "mailto:" + address }>{ address }</a>
    }
    ".name *" #> user.formalName &
    ".email *" #> email
  }

  def allUsers(): List[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].orderBy(cand.last.asc, cand.first.asc, cand.middle.asc).executeList()
  }
}