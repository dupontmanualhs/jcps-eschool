package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import org.joda.time.LocalDate
import bootstrap.liftweb.DataStore
import jdo.QId
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

class Term extends Id[Long] {
  private[this] var _name: String = _
  private[this] var _year: AcademicYear = _
  @Persistent
  private[this] var _start: LocalDate = _
  @Persistent
  private[this] var _end: LocalDate = _
  
  def this(name: String, year: AcademicYear, start: LocalDate, end: LocalDate) = {
    this()
    _name = name
    _year = year
    _start = start
    _end = end
  }
}

object Term {
  lazy val current: Term = {
    val cand = QTerm.candidate
    DataStore.pm.query[Term].filter(cand.name.eq("Fall 2011")).executeOption().get
  }
}

trait QTerm extends QId[Long, Term] {
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _year: ObjectExpression[AcademicYear] = new ObjectExpressionImpl[AcademicYear](this, "_year")
  def term: ObjectExpression[AcademicYear] = _year
  
  private[this] lazy val _start: ObjectExpression[LocalDate] = new ObjectExpressionImpl[LocalDate](this, "_start")
  def start: ObjectExpression[LocalDate] = _start

  private[this] lazy val _end: ObjectExpression[LocalDate] = new ObjectExpressionImpl[LocalDate](this, "_end")
  def end: ObjectExpression[LocalDate] = _end
}

object QTerm {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QTerm = {
    new PersistableExpressionImpl[Term](parent, name) with QId[Long, Term] with QTerm
  }
  
  def apply(cls: Class[Term], name: String, exprType: ExpressionType): QTerm = {
    new PersistableExpressionImpl[Term](cls, name, exprType) with QId[Long, Term] with QTerm
  }
  
  private[this] lazy val jdoCandidate: QTerm = candidate("this")
  
  def candidate(name: String): QTerm = QTerm(null, name, 5)
  
  def candidate(): QTerm = jdoCandidate
  
  def parameter(name: String): QTerm = QTerm(classOf[Term], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QTerm = QTerm(classOf[Term], name, ExpressionType.VARIABLE)
}

