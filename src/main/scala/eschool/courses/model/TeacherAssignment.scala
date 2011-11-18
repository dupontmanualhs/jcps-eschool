package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import eschool.users.model.Teacher
import org.joda.time.LocalDate
import jdo.QId
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

@PersistenceCapable
class TeacherAssignment extends Id[Long] {
  private[this] var _teacher: Teacher = _
  private[this] var _section: Section = _
  private[this] var _term: Term = _
  @Persistent
  private[this] var _start: LocalDate = _
  @Persistent
  private[this] var _end: LocalDate = _
  
  def this(teacher: Teacher, section: Section,
      term: Term, start: LocalDate, end: LocalDate) = {
    this()
    _teacher = teacher
    _section = section
    _term = term
    _start = start
    _end = end
  }
  
  def teacher: Teacher = _teacher
  def teacher_=(theTeacher: Teacher) { _teacher = theTeacher }

  def section: Section = _section
  def section_=(theSection: Section) { _section = theSection }
  
  def term: Term = _term
  def term_=(theTerm: Term) { _term = theTerm }
  
  def start: LocalDate = _start
  def start_=(theStart: LocalDate) { _start = theStart }
  
  def end: LocalDate = _end
  def end_=(theEnd: LocalDate) { _end = theEnd }
}

trait QTeacherAssignment extends QId[Long, TeacherAssignment] {
  private[this] lazy val _teacher: ObjectExpression[Teacher] = new ObjectExpressionImpl[Teacher](this, "_teacher")
  def teacher: ObjectExpression[Teacher] = _teacher
  
  private[this] lazy val _section: ObjectExpression[Section] = new ObjectExpressionImpl[Section](this, "_section")
  def section: ObjectExpression[Section] = _section
  
  private[this] lazy val _term: ObjectExpression[Term] = new ObjectExpressionImpl[Term](this, "_term")
  def term: ObjectExpression[Term] = _term
  
  private[this] lazy val _start: ObjectExpression[LocalDate] = new ObjectExpressionImpl[LocalDate](this, "_start")
  def start: ObjectExpression[LocalDate] = _start

  private[this] lazy val _end: ObjectExpression[LocalDate] = new ObjectExpressionImpl[LocalDate](this, "_end")
  def end: ObjectExpression[LocalDate] = _end
}

object QTeacherAssignment {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QTeacherAssignment = {
    new PersistableExpressionImpl[TeacherAssignment](parent, name) with QId[Long, TeacherAssignment] with QTeacherAssignment
  }
  
  def apply(cls: Class[TeacherAssignment], name: String, exprType: ExpressionType): QTeacherAssignment = {
    new PersistableExpressionImpl[TeacherAssignment](cls, name, exprType) with QId[Long, TeacherAssignment] with QTeacherAssignment
  }
  
  private[this] lazy val jdoCandidate: QTeacherAssignment = candidate("this")
  
  def candidate(name: String): QTeacherAssignment = QTeacherAssignment(null, name, 5)
  
  def candidate(): QTeacherAssignment = jdoCandidate
  
  def parameter(name: String): QTeacherAssignment = QTeacherAssignment(classOf[TeacherAssignment], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QTeacherAssignment = QTeacherAssignment(classOf[TeacherAssignment], name, ExpressionType.VARIABLE)
}