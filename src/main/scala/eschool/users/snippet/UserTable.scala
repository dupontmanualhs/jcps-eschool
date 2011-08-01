package eschool.users.snippet

import net.liftweb.util._
import Helpers._

import eschool.users.model.User

object UserTable {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    ".last *" #> user.last.get &
    ".first *" #> user.first.get &
    ".middle *" #> user.middle.get.getOrElse("") &
    ".preferred *" #> user.preferred.get.getOrElse("") &
    ".username *" #> user.username.get
  }

  def allUsers(): List[User] = {
    User.findAll.sortWith((u1: User, u2: User) => u1.formalName.toLowerCase < u2.formalName.toLowerCase)
  }
}