/*package eschool.users.model

import javax.jdo.annotations._
import bootstrap.liftweb.DataStore
import net.liftweb.common._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

import eschool.users.model.jdo.Perspective;


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

trait QStudent extends QPerspective[Student] {
  private[this] lazy val _stateId: StringExpression = new StringExpressionImpl(this, "_stateId")
  def stateId: StringExpression = _stateId
  
  private[this] lazy val _studentNumber: StringExpression = new StringExpressionImpl(this, "_studentNumber")
  def studentNumber: StringExpression = _studentNumber
  
  private[this] lazy val _grade: NumericExpression[Int] = new NumericExpressionImpl[Int](this, "_grade")
  def grade: NumericExpression[Int] = _grade
  
  private[this] lazy val _teamName: StringExpression = new StringExpressionImpl(this, "_teamName")
  def teamName: StringExpression = _teamName
}

object QStudent {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QStudent = {
    new PersistableExpressionImpl[Student](parent, name) with QPerspective[Student] with QStudent
  }
  
  def apply(cls: Class[Student], name: String, exprType: ExpressionType): QStudent = {
    new PersistableExpressionImpl[Student](cls, name, exprType) with QPerspective[Student] with QStudent
  }
  
  private[this] lazy val jdoCandidate: QStudent = candidate("this")
  
  def candidate(name: String): QStudent = QStudent(null, name, 5)
  
  def candidate(): QStudent = jdoCandidate
  
  def parameter(name: String): QStudent = QStudent(classOf[Student], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QStudent = QStudent(classOf[Student], name, ExpressionType.VARIABLE)
}*/