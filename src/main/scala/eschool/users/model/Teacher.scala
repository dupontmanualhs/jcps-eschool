package eschool.users.model

import net.liftweb.common._

import javax.jdo.annotations._
import bootstrap.liftweb.DataStore

@PersistenceCapable
class Teacher extends Perspective {
  @Unique
  private[this] var _personId: String = _
  @Unique
  private[this] var _stateId: String = _
  
  def this(user: User, personId: String, stateId: String) = {
    this()
    user_=(user)
    _personId = personId
    _stateId = stateId
  }
  
  def personId: String = _personId
  def personId_=(thePersonId: String) { _personId = thePersonId }
  
  def stateId: String = _stateId
  def stateId_=(theStateId: String) { _stateId = theStateId }
}

object Teacher {
  def getByUsername(username: String): Box[Teacher] = {
    User.getByUsername(username) match {
      case Full(user) => {
        val cand = QTeacher.candidate
        DataStore.pm.query[Teacher].filter(cand.user.eq(user)).executeOption
      }
      case _ => Empty
    }
  }
}