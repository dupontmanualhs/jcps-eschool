package eschool.users.model

import net.liftweb.common._
import net.liftweb.http.{RequestVar, SessionVar}
import bootstrap.liftweb.DataStore
import net.liftweb.http.S

import jdohelpers.Email
import jdo.{QUser, User}

object IUser {
  private object currentId extends SessionVar[Box[Long]](Empty)
  private object currentUser extends RequestVar[Box[User]](fetchUser)
  
  def getMiddle(user: User): Option[String] = {
    user.getMiddle match {
      case null => None
      case s: String => Some(s)
    }
  }
  
  def getPreferred(user: User): Option[String] = {
    user.getPreferred match {
      case null => None
      case s: String => Some(s)
    }
  }
  
  def getEmail(user: User): Option[String] = {
    user.getEmail match {
      case null => None
      case email: Email => Some(email.get)
    }
  }
  
  def displayName(user: User): String = {
    val pref = getPreferred(user)
    (if (pref.isDefined) pref.get else user.getFirst) + " " + user.getLast
  }

  def formalName(user: User): String = {
    val mid = getMiddle(user)
    user.getLast + ", " + user.getFirst + (if (mid.isDefined) (" " + mid.get) else "")
  }
  
  def fetchUser(): Box[User] = {
    currentId.get match {
      case Full(id) => {
        val cand = QUser.candidate
        DataStore.pm.query[User].filter(cand.id.eq(id)).executeOption()
      }
      case _ => Empty
    }
  }
  
  def getById(id: Long): Box[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].filter(cand.id.eq(id)).executeOption()
  }

  def getByUsername(username: String): Box[User] = {
    val cand = QUser.candidate
    DataStore.pm.query[User].filter(cand.username.eq(username)).executeOption()
  }

  def authenticate(username: String, password: String): Box[User] = {
    getByUsername(username) match {
      case Full(user) => authenticate(user, password)
      case _ => Empty
    }
  }

  def authenticate(user: User, password: String): Box[User] = {
    if (user.getPassword.matches(password)) {
      Full(user)
    } else {
      Empty
    }
  }

  def login(user: User): Unit = {
    currentId.set(Full(user.getId))
  }

  def logout(): Unit = {
    currentId.set(Empty)
  }

  def loggedIn_? = currentId.isDefined

  def getCurrent: Box[User] = currentId.get.flatMap(getById(_)) // TODO: fix when bug fixed
    
  def getCurrentOrRedirect(): User = getCurrent openOr {
    S.notice("You must login to access that page.")
    S.redirectTo("/users/login")
  }
}
