package eschool.users.model

import net.liftweb.common._
import net.liftweb.mapper._

class Teacher extends LongKeyedMapper[Teacher]
      with IdPK with Perspective {
  def getSingleton = Teacher
}

object Teacher extends Teacher with LongKeyedMetaMapper[Teacher] {
  override def dbTableName = "teacher"
  override def dbIndexes = UniqueIndex(user) :: super.dbIndexes

  def byUser(user: User) = {
    find(By(Teacher.user, user)).open_!
  }

  // TODO: move this into Perspective (hopefully)
  def getByUsername(username: String): Box[Teacher] = {
    User.find(By(User.username, username)) match {
      case Full(user) => Teacher.find(By(Teacher.user, user))
      case _ => Empty
    }
  }
}
