package eschool.users.model

import net.liftweb.mapper._

class Admin extends LongKeyedMapper[Admin]
      with IdPK with Perspective {
  def getSingleton = Admin
}

object Admin extends Admin with LongKeyedMetaMapper[Admin] {
  override def dbTableName = "admin"
  override def dbIndexes = UniqueIndex(user) :: super.dbIndexes
}