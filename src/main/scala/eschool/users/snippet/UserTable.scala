package eschool.users.snippet

import net.liftweb.util._
import Helpers._

import eschool.users.model.User

object UserTable {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    ".last *" #> user.last.get &
    ".first *" #> user.first.get &
    ".middle *" #> user.middle.get &
    ".preferred *" #> user.preferred.get &
    ".username *" #> user.username.get
  }

  def allUsers(): List[User] = {
    User.findAll // TODO: sort by last name, first name
  }
}