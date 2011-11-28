/*package eschool.courses.model

import bootstrap.liftweb.DataStore

import jdo.{QRoom, Room}

object IRoom {
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
}*/
