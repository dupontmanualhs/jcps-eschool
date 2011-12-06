package eschool.users.snippet

import net.liftweb.util._
import Helpers._
import eschool.users.model.{QTeacher, Teacher, QUser, User}
import xml.NodeSeq
import bootstrap.liftweb.DataStore

object ContactList {
  def render = ".userRow *" #> allTeachers().map(renderUser(_))

  def renderUser(user: User) = {
    val email = user.email match {
      case None => NodeSeq.Empty
      case Some(address)  => <a href={ "mailto:" + address }>{ address }</a>
    }
    ".name *" #> user.formalName &
    ".email *" #> email
  }

  def allTeachers(): List[User] = {
    val userVar = QUser.variable("userVar")
    val cand = QTeacher.candidate
    DataStore.pm.query[Teacher].filter(cand.user.eq(userVar)).orderBy(userVar.last.asc, userVar.first.asc).executeList().map(_.user)
  }
}