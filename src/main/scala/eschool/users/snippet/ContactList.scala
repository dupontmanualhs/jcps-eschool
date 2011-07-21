package eschool.users.snippet

import net.liftweb.util._
import Helpers._

import eschool.users.model.User
import xml.NodeSeq

object ContactList {
  def render = ".userRow *" #> allTeachers().map(renderUser(_))

  def renderUser(user: User) = {
    val email = user.email.get match {
      case Some(address) => <a href={ "mailto:" + address }>{ address }</a>
      case _ => NodeSeq.Empty
    }
    ".name *" #> user.formalName &
    ".email *" #> email
  }

  def allTeachers(): List[User] = {
    User.findAll.sortWith((u1: User, u2: User) => u1.formalName.toLowerCase < u2.formalName.toLowerCase)
  }
}