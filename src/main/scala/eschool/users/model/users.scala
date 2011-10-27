package eschool.users.model

/*
import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
*/

/*
class User extends MongoRecord[User] with ObjectIdPk[User] {
  def meta = User

  object username extends StringField(this, 15)
  object first extends StringField(this, 30)
  object middle extends OptionalStringField(this, 30)
  object last extends StringField(this, 30)
  object preferred extends OptionalStringField(this, 30)
  object gender extends EnumField(this, Gender, Gender.None)
  object email extends OptionalEmailField(this, 100)
  object password extends MongoPasswordField(this, 5) {
    override def optional_? = true
  }
  object guid extends OptionalStringField(this, 30)

  def displayName = {
    preferred.get.getOrElse(first.get) + " " + last.get
  }

  def formalName = {
    last.get + ", " + first.get
  }

  def login(): Unit = {
    User.current.set(Full(this.id.get))
  }

  def logout(): Unit = {
    User.current.set(Empty)
  }
}
*/

/*

*/

/*
trait Perspective[OwnerType <: MongoRecord[OwnerType]] extends ObjectIdPk[OwnerType] {
  self: OwnerType =>

  object user extends ObjectIdRefField(this.asInstanceOf[OwnerType], User)
}
*/

/*
class Student extends MongoRecord[Student] with Perspective[Student] {
  def meta = Student

  object stateId extends StringField(this, 10)
  object studentNumber extends StringField(this, 6)
  object grade extends IntField(this)
  object teamName extends StringField(this, 20)
}

object Student extends Student with MongoMetaRecord[Student] {
  override def collectionName = "students"

  ensureIndex("stateId" -> 1)
  ensureIndex("studentNumber" -> 1)

  def getByUsername(username: String): Box[Student] = {
    User.getByUsername(username) match {
      case Full(user) => Student where (_.user eqs user.id.get) get()
      case _ => Empty
    }
  }
}

class Teacher extends MongoRecord[Teacher] with Perspective[Teacher] {
  def meta = Teacher

  object personId extends StringField(this, 10)
  object stateId extends StringField(this, 10)
}

object Teacher extends Teacher with MongoMetaRecord[Teacher] {
  override def collectionName = "teachers"

  ensureIndex("personId" -> 1)

  def getByUsername(username: String): Box[Teacher] = {
    User.getByUsername(username) match {
      case Full(user) => Teacher where (_.user eqs user.id.get) get()
      case _ => Empty
    }
  }
}

class Guardian extends MongoRecord[Guardian] with Perspective[Guardian] {
  def meta = Guardian

  object children extends MongoListField[Guardian, ObjectId](this)
}

object Guardian extends Guardian with MongoMetaRecord[Guardian] {
  override def collectionName = "guardians"
}
*/