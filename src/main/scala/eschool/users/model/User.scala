package eschool.users.model

import net.liftweb.mapper._
import net.liftweb.http.SessionVar
import net.liftweb.common.{Box, Empty, Full}

class User extends LongKeyedMapper[User] with IdPK {
  def getSingleton = User

  object username extends MappedString(this, 15)
  object first extends MappedString(this, 30)
  object middle extends MappedString(this, 30)
  object last extends MappedString(this, 30)
  object preferred extends MappedString(this, 30)
  object gender extends MappedGender(this)
  object email extends MappedEmail(this, 100)
  object password extends MappedPassword(this)
  object guid extends MappedString(this, 30)

  def displayName = {
    (if (preferred.is != "") preferred.is else first.is) + " " + last.is
  }

  def login(): Unit = {
    User.current.set(Full(this))
  }

  def logout(): Unit = {
    User.current.set(Empty)
  }
}

object User extends User with LongKeyedMetaMapper[User] {
  override def dbTableName = "users"
  override def dbIndexes = UniqueIndex(username) :: UniqueIndex(email) :: UniqueIndex(guid) :: super.dbIndexes

  object current extends SessionVar[Box[User]](Empty)

  def getByUsername(username: String): Box[User] = {
    User.find(By(User.username, username))
  }

  def authenticate(username: String, password: String): Box[User] = {
    val usersByName = User.findAll(By(User.username, username))
    usersByName match {
      case user :: Nil => authenticate(user, password)
      case _ => Empty // maybe more than one user, which shouldn't happen
    }
  }

  def authenticate(user: User, password: String): Box[User] = {
    if (user.password.match_?(password)) {
      Full(user)
    } else {
      Empty
    }
  }

  def loggedIn_? = User.current.isDefined
}
