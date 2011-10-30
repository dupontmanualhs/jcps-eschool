package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._

import eschool.users.model.Student
import xml.NodeSeq
import eschool.courses.model.{StudentEnrollment, Term, Section}

class StudentSchedule(student: Student) {
  // TODO: handle current term correctly
  val assignments: List[StudentEnrollment] = StudentEnrollment where (_.student eqs student.id.get) and (_.term eqs Term.current.id.get) fetch
  val sections: List[Section] = assignments.map(_.section.obj.open_!)

  def sectionParts(section: Section): (NodeSeq => NodeSeq) = {
    ".periods *" #> section.periodNames &
    ".course *" #> section.course.obj.open_!.name.get &
    ".room *" #> section.room.obj.open_!.name.get
  }

  /*def scheduleTitle() = <head_merge><title>{
    "Schedule For " + student.user.obj.open_!.displayName
  }</title></head_merge>
  */

  def render = //".scheduleTitlePlaceholder" #> scheduleTitle() &
      ".name" #> student.user.obj.open_!.displayName &
      ".list *" #> sections.map(sectionParts(_))

}