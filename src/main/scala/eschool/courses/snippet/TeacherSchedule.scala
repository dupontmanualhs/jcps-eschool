package eschool.courses.snippet

import com.foursquare.rogue.Rogue._

import net.liftweb.util._
import net.liftweb.util.Helpers._

import eschool.users.model.Teacher
import eschool.courses.model.{Term, Section, TeacherAssignment}
import xml.NodeSeq

class TeacherSchedule(teacher: Teacher) {
  // TODO: handle current term correctly
  val assignments: List[TeacherAssignment] = TeacherAssignment where (_.teacher eqs teacher.id.get) and (_.term eqs Term.current.id.get) fetch
  val sections: List[Section] = assignments.map(_.section.obj.open_!)

  def sectionParts(section: Section): (NodeSeq => NodeSeq) = {
    ".periods *" #> section.periodNames &
    ".course *" #> section.course.obj.open_!.name.get &
    ".room *" #> section.room.obj.open_!.name.get
  }

  /*def scheduleTitle() = <head_merge><title>{
    "Schedule For " + teacher.user.obj.open_!.displayName
  }</title></head_merge>
  */
  
  def render = //".scheduleTitlePlaceholder" #> scheduleTitle() &
      ".name" #> teacher.user.obj.open_!.displayName &
      ".list *" #> sections.map(sectionParts(_))

}