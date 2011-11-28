package eschool.users.snippet

import net.liftweb.util._
import Helpers._
import eschool.users.model.{QUser, User}
import bootstrap.liftweb.DataStore

object UserTable {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    ".last *" #> user.last &
    ".first *" #> user.first &
    ".middle *" #> user.middle &
    ".preferred *" #> user.preferred &
    ".username *" #> user.username
  }

  def allUsers(): List[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].orderBy(cand.last.asc, cand.first.asc).executeList()
  }
}