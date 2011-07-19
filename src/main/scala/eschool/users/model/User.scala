package eschool.users.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{Box, Empty, Full}
import eschool.utils.record.Gender
import org.bson.types.ObjectId
import net.liftweb.http.{RequestVar, S, SessionVar}
import java.security.PrivateKey

class User private() extends MongoRecord[User] with ObjectIdPk[User] {
  def meta = User

  object username extends StringField(this, 15)
  object first extends StringField(this, 30)
  object middle extends OptionalStringField(this, 30)
  object last extends StringField(this, 30)
  object preferred extends OptionalStringField(this, 30)
  object gender extends EnumField(this, Gender, Gender.None)
  object email extends OptionalEmailField(this, 100)
  object password extends MongoPasswordField(this, 5)
  object guid extends OptionalStringField(this, 30)

  def displayName = {
    preferred.get.getOrElse(first.get) + " " + last.get
  }

  def login(): Unit = {
    User.current.set(Full(this.id.get))
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

  private object current extends SessionVar[Box[ObjectId]](Empty)
  private object currentUser extends RequestVar[Box[User]](current.get.flatMap(User.find(_)))

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
    if (user.password.isMatch(password)) {
      Full(user)
    } else {
      Empty
    }
  }

  def loggedIn_? = current.isDefined

  def getCurrent: Box[User] = currentUser.get

  def getCurrentOrRedirect(): User = getCurrent openOr {
    S.error("You must login to access that page.")
    S.redirectTo("/users/login")
  }
}
