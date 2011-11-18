package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._
import eschool.users.model.Student
import xml.NodeSeq
import eschool.courses.model._
import bootstrap.liftweb.DataStore
import eschool.utils.Helpers.mkNodeSeq

class StudentSchedule(student: Student) {
  // TODO: handle current term correctly
  val assignments: List[StudentEnrollment] = {
    val cand = QStudentEnrollment.candidate
    DataStore.pm.query[StudentEnrollment].filter(cand.student.eq(student).and(cand.term.eq(Term.current))).executeList()
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
      ".name" #> student.user.displayName &
      ".list *" #> periods.map(sectionsForPeriod(_))

}