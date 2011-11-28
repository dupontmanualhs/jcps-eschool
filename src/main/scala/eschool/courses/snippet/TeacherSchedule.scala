package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._
import eschool.users.model.Teacher
import eschool.courses.model._
import xml.NodeSeq
import bootstrap.liftweb.DataStore
import scala.xml.Text
import eschool.utils.Helpers.mkNodeSeq

class TeacherSchedule(teacher: Teacher) {
  // TODO: handle current term correctly
  val assignments: List[TeacherAssignment] = {
    val cand = QTeacherAssignment.candidate
    DataStore.pm.query[TeacherAssignment].filter(cand.teacher.eq(teacher).and(cand.term.eq(Term.current))).executeList()
  }
  val sections: List[Section] = assignments.map(_.section)
  val periods: List[Period] = DataStore.pm.query[Period].orderBy(QPeriod.candidate.order.asc).executeList()

  def sectionsForPeriod(period: Period): (NodeSeq => NodeSeq) = {
    val sectionsThisPeriod = sections.filter(_.periods.contains(period))
    ".period *" #> period.name &
    ".courses *" #> mkNodeSeq(sectionsThisPeriod.map(_.course.name), <br/>) &
    ".rooms *" #> mkNodeSeq(sectionsThisPeriod.map(_.room.name), <br/>)
  }

  def render = //".scheduleTitlePlaceholder" #> scheduleTitle() &
      ".name" #> teacher.user.displayName &
      ".list *" #> periods.map(sectionsForPeriod(_))

}