package eschool.users.model

import net.liftweb.common._
import bootstrap.liftweb.DataStore

import eschool.users.model.jdo.{QTeacher, Teacher};

object ITeacher {
  def getByUsername(username: String): Box[Teacher] = {
    IUser.getByUsername(username) match {
      case Full(user) => {
        val cand = QTeacher.candidate
        DataStore.pm.query[Teacher].filter(cand.user.eq(user)).executeOption
      }
      case _ => Empty
    }
  }
}
