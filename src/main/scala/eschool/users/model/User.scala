package eschool.users.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.http.SessionVar
import net.liftweb.common.{Box, Empty, Full}

import eschool.utils.record.Gender

class User private() extends MongoRecord[User] with ObjectIdPk[User] {
  def meta = User

  object username extends StringField(this, 15)
  object first extends StringField(this, 30)
  object middle extends OptionalStringField(this, 30)
  object last extends StringField(this, 30)
  object preferred extends OptionalStringField(this, 30)
  object gender extends EnumField(this, Gender, Gender.None)
  object email extends OptionalEmailField(this, 100)
  object password extends StringField(this, 80)  //TODO Password, when fixed
  object guid extends OptionalStringField(this, 30)

  def displayName = {
    preferred.get.getOrElse(first.get) + " " + last.get
  }

  def login(): Unit = {
    User.current.set(Full(this))
  }

  def logout(): Unit = {
    User.current.set(Empty)
  }
}

object User extends User with MongoMetaRecord[User] {
  ensureIndex("username" -> 1, "unique" -> true)
  ensureIndex("email" -> 1, ("sparse" -> true) ~ ("unique" -> true))
  ensureIndex("guid" -> 1, ("sparse" -> true) ~ ("unique" -> true))
  ensureIndex(("last" -> 1) ~ ("first" -> 1))

  override def collectionName = "users"

  object current extends SessionVar[Box[User]](Empty)

  def getByUsername(username: String): Box[User] = {
    User.find("username" -> username)
  }

  def authenticate(username: String, password: String): Box[User] = {
    val usersByName = User.findAll("username" -> username)
    usersByName match {
      case user :: Nil => authenticate(user, password)
      case _ => Empty // maybe more than one user, which shouldn't happen
    }
  }

  def authenticate(user: User, password: String): Box[User] = {
    if (user.password.get == password) { //TODO change to match_? when fixed
      Full(user)
    } else {
      Empty
    }
  }

  def loggedIn_? = User.current.isDefined
}
