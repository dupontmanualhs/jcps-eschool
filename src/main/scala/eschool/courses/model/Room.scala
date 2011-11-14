package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import bootstrap.liftweb.DataStore

class Room extends Id[Long] {
  @Unique
  private[this] var _name: String = _
  
  def this(name: String) = {
    this()
    _name = name
  }

  def name: String = _name
  def name_=(theName: String) { _name = theName }
}

object RoomUtil {
  def getOrCreate(name: String): Room = {
    val cand = QRoom.candidate
    DataStore.pm.query[Room].filter(cand.name.eq(name)).executeOption() match {
      case Some(room) => room
      case None => {
        val room = new Room(name)
        DataStore.pm.makePersistent(room)
      }
    }
  }
}
