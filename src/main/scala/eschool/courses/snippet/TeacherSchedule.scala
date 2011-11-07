package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._
import eschool.users.model.Teacher
import eschool.courses.model.{Term, Section, SectionUtil, TeacherAssignment, QTeacherAssignment, TermUtil}
import xml.NodeSeq
import bootstrap.liftweb.DataStore

class TeacherSchedule(teacher: Teacher) {
  // TODO: handle current term correctly
  val assignments: List[TeacherAssignment] = {
    val cand = QTeacherAssignment.candidate
    DataStore.pm.query[TeacherAssignment].filter(cand.teacher.eq(teacher).and(cand.term.eq(TermUtil.current))).executeList()
  }
  val sections: List[Section] = assignments.map(_.getSection)

  def sectionParts(section: Section): (NodeSeq => NodeSeq) = {
    ".periods *" #> SectionUtil.periodNames(section) &
    ".course *" #> section.getCourse.getName &
    ".room *" #> section.getRoom.getName
  }

  def render = //".scheduleTitlePlaceholder" #> scheduleTitle() &
      ".name" #> teacher.getUser.displayName &
      ".list *" #> sections.map(sectionParts(_))

}