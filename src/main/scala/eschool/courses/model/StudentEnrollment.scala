package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import org.joda.time.LocalDate

import eschool.users.model.Student

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