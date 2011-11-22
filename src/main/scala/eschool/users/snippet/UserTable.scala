package eschool.users.snippet

import net.liftweb.util._
import Helpers._
import eschool.users.model.jdo.{QUser, User}
import bootstrap.liftweb.DataStore

object UserTable {
  def render = ".userRow *" #> allUsers().map(renderUser(_))

  def renderUser(user: User) = {
    ".last *" #> user.getLast &
    ".first *" #> user.getFirst &
    ".middle *" #> user.getMiddle &
    ".preferred *" #> user.getPreferred &
    ".username *" #> user.getUsername
  }

  def allUsers(): List[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].orderBy(cand.last.asc, cand.first.asc).executeList()
  }
}