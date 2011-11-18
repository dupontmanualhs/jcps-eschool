package eschool.users.model

import javax.jdo.annotations._
import net.liftweb.common._
import net.liftweb.http.{RequestVar, SessionVar}
import jdohelpers.{Email, Gender, Password}
import jdo.Id
import bootstrap.liftweb.DataStore
import net.liftweb.http.S
import jdo.QId
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable
class User extends Id[Long] {
  @Unique
  @Column(allowsNull="false")
  private[this] var _username: String = _
  @Column(allowsNull="false")
  private[this] var _first: String = _
  private[this] var _middle: String = _
  @Column(allowsNull="false")
  private[this] var _last: String = _
  private[this] var _preferred: String = _
  private[this] var _gender: Gender = _
  @Embedded
  @Unique
  private[this] var _email: Email = _
  @Embedded
  private[this] var _password: Password = _
  
  def this(username: String, first: String, middle: Option[String], last: String,
      preferred: Option[String], gender: Gender, email: String, password: String) = {
    this()
    username_=(username)
    first_=(first)
    if (middle.isDefined) middle_=(middle.get)
    last_=(last)
    preferred_=(preferred)
    gender_=(gender)
    email_=(new Email(email))
    password_=(new Password(password))
  }
  
  def username: String = _username
  def username_=(theUsername: String) { _username = theUsername }
  
  def first: String = _first
  def first_=(theFirst: String) { _first = theFirst }
  
  def middle: Option[String] = if (_middle == null) Empty else Full(_middle)
  def middle_=(theMiddle: String) { _middle = theMiddle }
  
  def last: String = _last
  def last_=(theLast: String) { _last = theLast }
  
  def preferred: Option[String] = if (_preferred == null) Empty else Full(_preferred)
  def preferred_=(thePreferred: Option[String]) { _preferred = thePreferred.getOrElse(null) }
  
  def gender: Gender = _gender
  def gender_=(theGender: Gender) { _gender = gender }
  
  def email: Option[String] = if (_email == null) None else Some(_email.get)
  def email_=(theEmail: Email) { _email = theEmail }
  def email_=(theEmail: Option[String]) {
    if (theEmail.isDefined) email = new Email(theEmail.get)
    else _email = null
  }
  def email_=(theEmail: String) { email = Some(theEmail) }
  
  def password: Password = _password
  def password_=(thePassword: Password) { _password = thePassword }
  def password_=(thePassword: String) { _password = new Password(thePassword) }

  def displayName: String = {
    preferred.getOrElse(first) + " " + last
  }
  
  def formalName: String = {
    last + ", " + first + (if (middle.isDefined) " " + middle.get else "")
  }
}

object User {
  private object currentId extends SessionVar[Box[Long]](Empty)
  private object currentUser extends RequestVar[Box[User]](fetchUser)
  
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
    if (user.password.matches(password)) {
      Full(user)
    } else {
      Empty
    }
  }

  def login(user: User): Unit = {
    currentId.set(Full(user.id))
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

trait QUser extends QId[Long, User] {
  private[this] lazy val _username: StringExpression = new StringExpressionImpl(this, "_username")
  def username: StringExpression = _username
  
  private[this] lazy val _first: StringExpression = new StringExpressionImpl(this, "_first")
  def first: StringExpression = _first
  
  private[this] lazy val _middle: StringExpression = new StringExpressionImpl(this, "_middle")
  def middle: StringExpression = _middle
  
  private[this] lazy val _last: StringExpression = new StringExpressionImpl(this, "_last")
  def last: StringExpression = _last
  
  private[this] lazy val _preferred: StringExpression = new StringExpressionImpl(this, "_preferred")
  def preferred: StringExpression = _last
  
  private[this] lazy val _gender: ObjectExpression[Gender] = new ObjectExpressionImpl[Gender](this, "_gender")
  def gender: ObjectExpression[Gender] = _gender
  
  private[this] lazy val _email: ObjectExpression[Email] = new ObjectExpressionImpl[Email](this, "_email")
  def email: ObjectExpression[Email] = _email
  
  private[this] lazy val _password: ObjectExpression[Password] = new ObjectExpressionImpl[Password](this, "_password")
  def password: ObjectExpression[Password] = _password
  
}

object QUser {
  def apply(parent: PersistableExpression[User], name: String, depth: Int) = {
    new PersistableExpressionImpl[User](parent, name) with QId[Long, User] with QUser
  }
  
  def apply(cls: Class[User], name: String, exprType: ExpressionType): QUser = {
    new PersistableExpressionImpl[User](cls, name, exprType) with QId[Long, User] with QUser
  }
  
  private[this] lazy val jdoCandidate: QUser = candidate("this")
  
  def candidate(name: String): QUser = QUser(null, name, 5)
  
  def candidate(): QUser = jdoCandidate

  def parameter(name: String): QUser = QUser(classOf[User], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QUser = QUser(classOf[User], name, ExpressionType.VARIABLE)

}