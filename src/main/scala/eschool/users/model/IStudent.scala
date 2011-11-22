package eschool.users.model

import bootstrap.liftweb.DataStore
import net.liftweb.common._

import eschool.users.model.jdo.{Perspective, Student, QStudent}

object IStudent {
  def getByUsername(username: String): Box[Student] = {
    IUser.getByUsername(username) match {
      case Full(user) => {
        val cand = QStudent.candidate
        DataStore.pm.query[Student].filter(cand.user.eq(user)).executeOption
      }
      case _ => Empty
    }
  }
}
