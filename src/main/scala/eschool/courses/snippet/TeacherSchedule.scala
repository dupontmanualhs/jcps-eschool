package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._
import eschool.users.model.jdo.Teacher
import eschool.courses.model.jdo._
import xml.NodeSeq
import bootstrap.liftweb.DataStore
import scala.xml.Text
import eschool.utils.Helpers.mkNodeSeq
import eschool.courses.model.ISection
import eschool.courses.model.ITerm
import eschool.users.model.IUser

class TeacherSchedule(teacher: Teacher) {
  // TODO: handle current term correctly
  val assignments: List[TeacherAssignment] = {
    val cand = QTeacherAssignment.candidate
    DataStore.pm.query[TeacherAssignment].filter(cand.teacher.eq(teacher).and(cand.term.eq(ITerm.current))).executeList()
  }
  val sections: List[Section] = assignments.map(_.getSection)
  val periods: List[Period] = DataStore.pm.query[Period].orderBy(QPeriod.candidate.order.asc).executeList()

  def sectionsForPeriod(period: Period): (NodeSeq => NodeSeq) = {
    val sectionsThisPeriod = sections.filter(ISection.getPeriods(_).contains(period))
    ".period *" #> period.getName &
    ".courses *" #> mkNodeSeq(sectionsThisPeriod.map(_.getCourse.getName), <br/>) &
    ".rooms *" #> mkNodeSeq(sectionsThisPeriod.map(_.getRoom.getName), <br/>)
  }

  def render = //".scheduleTitlePlaceholder" #> scheduleTitle() &
      ".name" #> IUser.displayName(teacher.getUser) &
      ".list *" #> periods.map(sectionsForPeriod(_))

}