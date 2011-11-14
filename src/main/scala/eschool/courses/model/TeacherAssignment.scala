package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import eschool.users.model.Teacher
import org.joda.time.LocalDate

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

  def section: Section = _section
  def section_=(theSection: Section) { _section = theSection }
  // TODO: getters and setters
}