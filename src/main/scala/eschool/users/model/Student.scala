package eschool.users.model

import javax.jdo.annotations._
import bootstrap.liftweb.DataStore

import net.liftweb.common._

class Student extends Perspective {
  @Unique
  private[this] var _stateId: String = _
  @Unique
  private[this] var _studentNumber: String = _
  private[this] var _grade: Int = _
  private[this] var _teamName: String = _
  
  def this(user: User, stateId: String, studentNumber: String, grade: Int, teamName: String) = {
    this()
    user_=(user)
    _stateId = stateId
    _studentNumber = studentNumber
    _grade = grade
    _teamName = teamName 
  }
  
  def stateId: String = _stateId
  def stateId_=(theStateId: String) { _stateId = theStateId }
  
  def studentNumber: String = _studentNumber
  def studentNumber_=(theStudentNumber: String) { _studentNumber = theStudentNumber }
  
  def grade: Int = _grade
  def grade_=(theGrade: Int) { _grade = theGrade }
  
  def teamName: String = _teamName
  def teamName_=(theTeamName: String) { _teamName = theTeamName }
}

object Student {
  def getByUsername(username: String): Box[Student] = {
    User.getByUsername(username) match {
      case Full(user) => {
        val cand = QStudent.candidate
        DataStore.pm.query[Student].filter(cand.user.eq(user)).executeOption
      }
      case _ => Empty
    }
  }
}