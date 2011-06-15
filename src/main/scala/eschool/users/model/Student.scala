package eschool.users.model

import net.liftweb.common._
import net.liftweb.mapper._

class Student extends LongKeyedMapper[Student]
      with IdPK with Perspective {
  def getSingleton = Student

  object grade extends MappedInt(this)
}

object Student extends Student with LongKeyedMetaMapper[Student] {
  override def dbTableName = "student"
  override def dbIndexes = UniqueIndex(user) :: super.dbIndexes

  def getByGuid(guid: String): Student = {
    val user = User.find(By(User.guid, guid)).open_!
    Student.find(By(Student.user, user)).open_!
  }

  def getByUsername(username: String): Box[Student] = {
    User.find(By(User.username, username)) match {
      case Full(user) => Student.find(By(Student.user, user))
      case _ => Empty
    }
  }
}