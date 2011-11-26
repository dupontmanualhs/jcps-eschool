package eschool.courses.snippet

import net.liftweb.util._
import net.liftweb.util.Helpers._
import eschool.users.model.jdo.Student
import xml.NodeSeq
import eschool.courses.model._
import eschool.courses.model.jdo._
import bootstrap.liftweb.DataStore
import eschool.utils.Helpers.mkNodeSeq

class StudentSchedule(student: Student) {
  // TODO: handle current term correctly
  val assignments: List[StudentEnrollment] = {
    val cand = QStudentEnrollment.candidate
    DataStore.pm.query[StudentEnrollment].filter(cand.student.eq(student).and(cand.term.eq(ITerm.current))).executeList()
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
      ".name" #> student.getUser.displayName &
      ".list *" #> periods.map(sectionsForPeriod(_))

}