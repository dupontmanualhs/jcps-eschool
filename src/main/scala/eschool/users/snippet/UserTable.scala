package eschool.users.snippet

import net.liftweb.mapper._
import net.liftweb.util._
import Helpers._

import eschool.users.model.User

object UserTable {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    ".last *" #> user.last.is &
    ".first *" #> user.first.is &
    ".middle *" #> user.middle.is &
    ".preferred *" #> user.preferred.is &
    ".username *" #> user.username.is
  }

  def allUsers(): List[User] = {
    User.findAll(OrderBy(User.last, Ascending), OrderBy(User.first, Ascending))
  }
}