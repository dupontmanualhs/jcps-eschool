package eschool.users.snippet

import net.liftweb.util._
import Helpers._
import eschool.users.model.jdo.{QUser, User}
import xml.NodeSeq
import bootstrap.liftweb.DataStore
import eschool.users.model.IUser

object ContactList {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    val email = IUser.getEmail(user) match {
      case None => NodeSeq.Empty
      case Some(address)  => <a href={ "mailto:" + address }>{ address }</a>
    }
    ".name *" #> IUser.formalName(user) &
    ".email *" #> email
  }

  def allUsers(): List[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].orderBy(cand.last.asc, cand.first.asc, cand.middle.asc).executeList()
  }
}