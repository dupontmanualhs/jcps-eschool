package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import org.joda.time.LocalDate
import bootstrap.liftweb.DataStore

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

