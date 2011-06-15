package eschool.users.model

import net.liftweb.common.{Empty, Box}
import net.liftweb.http.SessionVar
import net.liftweb.mapper._

trait Perspective {
  self: BaseLongKeyedMapper =>
  object user extends MappedLongForeignKey[MapperType, User](this.asInstanceOf[MapperType], User) {
    override def dbIndexed_? = false
  }
  def getUser(): User = user.obj.open_!
}

object Perspective {
  object current extends SessionVar[Box[Perspective]](Empty)
}