package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import org.joda.time.LocalDate
import eschool.users.model.Student
import jdo.QId
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

@PersistenceCapable
class StudentEnrollment extends Id[Long] {
  private[this] var _student: Student = _
  private[this] var _section: Section = _
  private[this] var _term: Term = _
  @Persistent
  private[this] var _start: LocalDate = _
  @Persistent
  private[this] var _end: LocalDate = _
  
  def this(student: Student, section: Section, term: Term,
      start: LocalDate, end: LocalDate) = {
    this()
    _student = student
    _section = section
    _term = term
    _start = start
    _end = end
  }
  
  def section: Section = _section
  def section_=(theSection: Section) { _section = theSection }
}

trait QStudentEnrollment extends QId[Long, StudentEnrollment] {
  private[this] lazy val _student: ObjectExpression[Student] = new ObjectExpressionImpl[Student](this, "_student")
  def student: ObjectExpression[Student] = _student
  
  private[this] lazy val _section: ObjectExpression[Section] = new ObjectExpressionImpl[Section](this, "_section")
  def section: ObjectExpression[Section] = _section
  
  private[this] lazy val _term: ObjectExpression[Term] = new ObjectExpressionImpl[Term](this, "_term")
  def term: ObjectExpression[Term] = _term
  
  private[this] lazy val _start: ObjectExpression[LocalDate] = new ObjectExpressionImpl[LocalDate](this, "_start")
  def start: ObjectExpression[LocalDate] = _start

  private[this] lazy val _end: ObjectExpression[LocalDate] = new ObjectExpressionImpl[LocalDate](this, "_end")
  def end: ObjectExpression[LocalDate] = _end
}

object QStudentEnrollment {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QStudentEnrollment = {
    new PersistableExpressionImpl[StudentEnrollment](parent, name) with QId[Long, StudentEnrollment] with QStudentEnrollment
  }
  
  def apply(cls: Class[StudentEnrollment], name: String, exprType: ExpressionType): QStudentEnrollment = {
    new PersistableExpressionImpl[StudentEnrollment](cls, name, exprType) with QId[Long, StudentEnrollment] with QStudentEnrollment
  }
  
  private[this] lazy val jdoCandidate: QStudentEnrollment = candidate("this")
  
  def candidate(name: String): QStudentEnrollment = QStudentEnrollment(null, name, 5)
  
  def candidate(): QStudentEnrollment = jdoCandidate
  
  def parameter(name: String): QStudentEnrollment = QStudentEnrollment(classOf[StudentEnrollment], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QStudentEnrollment = QStudentEnrollment(classOf[StudentEnrollment], name, ExpressionType.VARIABLE)
}