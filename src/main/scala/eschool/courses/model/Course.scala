package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import jdo.QId
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

class Course extends Id[Long] {
  private[this] var _name: String = _
  @Unique
  private[this] var _masterNumber: String = _
  private[this] var _department: Department = _
  
  def this(name: String, masterNumber: String, department: Department) = {
    this()
    _name = name
    _masterNumber = masterNumber
    _department = department
  }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def masterNumber: String = _masterNumber
  def masterNumber_=(theMasterNumber: String) { _masterNumber = theMasterNumber }
  
  def department: Department = _department
  def department_=(theDepartment: Department) { _department = theDepartment }
}

trait QCourse extends QId[Long, Course] {
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _masterNumber: StringExpression = new StringExpressionImpl(this, "_masterNumber")
  def masterNumber: StringExpression = _masterNumber
  
  private[this] lazy val _department: ObjectExpression[Department] = new ObjectExpressionImpl[Department](this, "_department")
  def department: ObjectExpression[Department] = _department
}

object QCourse {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QCourse = {
    new PersistableExpressionImpl[Course](parent, name) with QId[Long, Course] with QCourse
  }
  
  def apply(cls: Class[Course], name: String, exprType: ExpressionType): QCourse = {
    new PersistableExpressionImpl[Course](cls, name, exprType) with QId[Long, Course] with QCourse
  }
  
  private[this] lazy val jdoCandidate: QCourse = candidate("this")
  
  def candidate(name: String): QCourse = QCourse(null, name, 5)
  
  def candidate(): QCourse = jdoCandidate
  
  def parameter(name: String): QCourse = QCourse(classOf[Course], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QCourse = QCourse(classOf[Course], name, ExpressionType.VARIABLE)
}