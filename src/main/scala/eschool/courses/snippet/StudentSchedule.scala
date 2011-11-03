package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._
import eschool.users.model.Student
import xml.NodeSeq
import eschool.courses.model.{QStudentEnrollment, StudentEnrollment, TermUtil, Section, SectionUtil}
import bootstrap.liftweb.DataStore

class StudentSchedule(student: Student) {
  // TODO: handle current term correctly
  val assignments: List[StudentEnrollment] = {
    val cand = QStudentEnrollment.candidate
    DataStore.pm.query[StudentEnrollment].filter(cand.student.eq(student).and(cand.term.eq(TermUtil.current))).executeList()
  }
  val sections: List[Section] = assignments.map(_.getSection)

  def sectionParts(section: Section): (NodeSeq => NodeSeq) = {
    ".periods *" #> SectionUtil.periodNames(section) &
    ".course *" #> section.getCourse.getName &
    ".room *" #> section.getRoom.getName
  }

  def render = //".scheduleTitlePlaceholder" #> scheduleTitle() &
      ".name" #> student.getUser.displayName &
      ".list *" #> sections.map(sectionParts(_))

}