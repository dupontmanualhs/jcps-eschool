package eschool.utils

import record.Gender
import scala.collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Sheet, Row, WorkbookFactory}
import xml.{Node, Elem, XML}
import com.mongodb.Mongo
import eschool.users.model.{Student, Teacher, User}
import bootstrap.liftweb.Boot
import org.joda.time.LocalDate
import eschool.courses.model._

import com.foursquare.rogue.Rogue._

import net.liftweb.common._
import java.util.Date
import org.joda.time.format.DateTimeFormat

object ManualData {
  val netIdMap: Map[String, String] = buildNetIdMap()

  def load(debug: Boolean = false) {
    new Boot().boot()
    new Mongo().dropDatabase("eschool")
    createYearsAndTerms(debug)
    loadStudents(debug)
    loadTeachers(debug)
    loadCourses(debug)
    loadSections(debug)
    loadEnrollments(debug)
  }

  def createYearsAndTerms(debug: Boolean) {
    val acadYear = AcademicYear.createRecord.name("2011-12")
    acadYear.save(true)
    val fallStart = new LocalDate(2011, 8, 17).toDateTimeAtStartOfDay.toDate
    val fallEnd = new LocalDate(2012, 12, 16).toDateTimeAtStartOfDay.toDate
    val fall2011 = Term.createRecord.name("Fall 2011").year(acadYear.id.get).startDate(fallStart).endDate(fallEnd)
    fall2011.save(true)
    val springStart = new LocalDate(2012, 1, 3).toDateTimeAtStartOfDay.toDate
    val springEnd = new LocalDate(2012, 5, 25).toDateTimeAtStartOfDay.toDate
    val spring2012 = Term.createRecord.name("Spring 2012").year(acadYear.id.get).startDate(springStart).endDate(springEnd)
    spring2012.save(true)
    val red1 = Period.createRecord.name("Red 1")
    red1.save(true)
    val red2 = Period.createRecord.name("Red 2")
    red2.save(true)
    val red3 = Period.createRecord.name("Red 3")
    red3.save(true)
    val red4 = Period.createRecord.name("Red 4")
    red4.save(true)
    val white1 = Period.createRecord.name("White 1")
    white1.save(true)
    val white2 = Period.createRecord.name("White 2")
    white2.save(true)
    val white3 = Period.createRecord.name("White 3")
    white3.save(true)
    val white4 = Period.createRecord.name("White 4")
    white4.save(true)
    val redAct = Period.createRecord.name("Red Activity")
    redAct.save(true)
    val whiteAct = Period.createRecord.name("White Activity")
    whiteAct.save(true)
    val redAdv = Period.createRecord.name("Red Advisory")
    redAdv.save(true)
    val whiteAdv = Period.createRecord.name("White Advisory")
    whiteAdv.save(true)
    if (debug) println("Created AcademicYear, Terms, and Periods")
  }

  def loadStudents(debug: Boolean) {
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Students.xml"))
    val students = doc \\ "student"
    students foreach ((student: Node) => {
      // grab data
      val studentNumber = asIdNumber((student \ "@student.studentNumber").text)
      val stateId = asIdNumber((student \ "@student.stateID").text)
      val first = (student \ "@student.firstName").text
      val middle = (student \ "@student.middleName").text
      val last = (student \ "@student.lastName").text
      val teamName = (student \ "@student.teamName").text
      val grade = (student \ "@student.grade").text.toInt
      val gender = Gender((student \ "@student.gender").text)
      val username = netIdMap.getOrElse(studentNumber, studentNumber)
      if (debug) {
        println()
        println("%s, %s %s".format(last, first, middle))
        println("#: %s, id: %s, grade: %d".format(studentNumber, stateId, grade))
        println("name: %s, magnet: %s, gender: %s".format(username, teamName, gender))
      }
       // create User
      val user = User.createRecord.username(username).first(first).middle(middle).last(last).gender(gender)
      user.save(true)
      if (debug) println("user saved")
      // create Student
      val dbStudent = Student.createRecord.user(user.id.get).stateId(stateId).studentNumber(studentNumber).grade(grade).teamName(teamName)
      dbStudent.save(true)
      if (debug) println("student saved")
    })
  }

  def loadTeachers(debug: Boolean) {
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Teachers.xml"))
    val teachers = doc \\ "person"
    teachers foreach ((teacher: Node) => {
      val username = asIdNumber((teacher \ "@individual.personID").text) // TODO: get real login name
      val first = (teacher \ "@individual.firstName").text
      val middle = (teacher \ "@individual.middleName").text
      val last = (teacher \ "@individual.lastName").text
      val gender = Gender((teacher \ "@individual.gender").text)
      val personId = asIdNumber((teacher \ "@individual.personID").text)
      val stateId = asIdNumber((teacher \ "@individual.stateID").text)
      if (debug) {
        println()
        println("%s, %s %s".format(last, first, middle))
        println("#: %s, id: %s".format(personId, stateId))
        println("name: %s, gender: %s".format(username, gender))
      }
      val user = User.createRecord.username(username).first(first).middle(middle).last(last).gender(gender)
      user.save(true)
      if (debug) println("user saved")
      val dbTeacher = Teacher.createRecord.user(user.id.get).personId(personId).stateId(stateId)
      dbTeacher.save(true)
      if (debug) println("teacher saved")
    })
  }

  def loadCourses(debug: Boolean) {
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Courses.xml"))
    val courses = doc \\ "curriculum"
    courses foreach ((course: Node) => {
      val name = (course \ "@courseInfo.courseName").text
      val masterNumber = asIdNumber((course \ "@courseInfo.courseMasterNumber").text)
      val dept = Department.getOrCreate((course \ "@courseInfo.departmentName").text)
      if (debug) println("%s, %s (%s)".format(name, masterNumber, dept))
      val dbCourse = Course.createRecord.name(name).masterNumber(masterNumber).department(dept.id.get)
      dbCourse.save(true)
    })
  }

  def loadSections(debug: Boolean) {
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Sections.xml"))
    val sections = doc \\ "curriculum"
    val fall11 = (Term where (_.name eqs "Fall 2011") get ()).get
    val spring12 = (Term where (_.name eqs "Spring 2012") get()).get
    sections foreach ((section: Node) => {
      val sectionId = (section \ "@sectionInfo.sectionID").text
      if (debug) println("Working on section: %s".format(sectionId))
      val courseMasterNumber = asIdNumber((section \ "@courseInfo.courseMasterNumber").text)
      val course = Course.where(_.masterNumber eqs courseMasterNumber).get().get
      val roomNum = (section \ "@sectionInfo.roomName").text
      val room = Room.getOrCreate(roomNum)
      val termStart = (section \ "@sectionSchedule.termStart").text
      val termEnd = (section \ "@sectionSchedule.termEnd").text
      val terms: List[Term] = (termStart, termEnd) match {
        case ("1", "3") => List(fall11)
        case ("4", "6") => List(spring12)
        case ("1", "6") => List(fall11, spring12)
      }
      val periodStart = (section \ "@sectionSchedule.periodStart").text
      val periodEnd = (section \ "@sectionSchedule.periodEnd").text
      val dayStart = (section \ "@sectionSchedule.scheduleStart").text
      val dayEnd = (section \ "@sectionSchedule.scheduleEnd").text
      val periods = periodNames(dayStart, dayEnd, periodStart, periodEnd) map ((p: String) => {
        (Period where (_.name eqs p) get()).get
      })
      val teacherPersonId = (section \ "@sectionInfo.teacherPersonID").text
      val teacher = (Teacher where (_.personId eqs teacherPersonId) get()).get
      val teacherAssignment = TeacherAssignment.createRecord.teacher(teacher.id.get).startDate(Empty).endDate(Empty)
      teacherAssignment.save(true)
      val dbSection: Section = Section.createRecord.course(course.id.get)
      dbSection.sectionId(sectionId).terms(terms.map(_.id.get))
      dbSection.periods(periods.map(_.id.get)).room(room.id.get).teacherAssignments(List(teacherAssignment.id.get))
      dbSection.studentEnrollments(List())
      dbSection.save(true)
    })
  }

  def loadEnrollments(debug: Boolean) {
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Schedule.xml"))
    val enrollments = doc \\ "student"
    enrollments foreach  ((enrollment: Node) => {
      val sectionId = asIdNumber((enrollment \ "@courseSection.sectionID").text)
      val section = (Section where (_.sectionId eqs sectionId) get()).get
      val studentNumber = asIdNumber((enrollment \ "@student.studentNumber").text)
      val student = (Student where (_.studentNumber eqs studentNumber) get()).get
      if (debug) println("Adding student #%s to section #%s".format(studentNumber, sectionId))
      val startDate = asDateBox((enrollment \ "@roster.startDate").text)
      val endDate = asDateBox((enrollment \ "@roster.endDate").text)
      val dbEnrollment = StudentEnrollment.createRecord.student(student.id.get).startDate(startDate).endDate(endDate)
      dbEnrollment.save(true)
      section.studentEnrollments(dbEnrollment.id.get :: section.studentEnrollments.get)
      section.save(true)
    })
  }

  def asDateBox(date: String): Box[Date] = {
    val format = DateTimeFormat.forPattern("MM/dd/yyyy")
    date match {
      case "" => Empty
      case _ =>  Full(format.parseDateTime(date).toDate)
    }
  }

  def periodNames(dayStart: String, dayEnd: String,
                  periodStart: String, periodEnd: String): List[String] = {
    val days = List(dayStart, dayEnd).distinct map ((d: String) => {
      d match {
        case "RED" => "Red"
        case "WHT" => "White"
      }
    })
    val periods: List[String] = (periodStart, periodEnd) match {
      case ("ACT", "ACT") => List("Activity")
      case ("ADV", "ADV") => List("Advisory")
      case (_, _) => (periodStart.toInt to periodEnd.toInt).toList.map(_.toString)
    }
    days flatMap ((d: String) => {
      periods map ((p: String) => {
        "%s %s".format(d, p)
      })
    })
  }

  def asIdNumber(s: String): String = {
    s.replaceAll("^0+", "")
  }

  def buildNetIdMap(): Map[String, String] = {
    val wb = WorkbookFactory.create(getClass.getResourceAsStream("/manual-data/StudentNetworkSecurityInfo.xls"))
    val sheet: Sheet = wb.getSheetAt(0)
    sheet.removeRow(sheet.getRow(0))
    val pairs = (sheet map ((row: Row) => {
      row.getCell(0).getNumericCellValue.toInt.toString -> row.getCell(6).getStringCellValue
    })).toList
    Map(pairs: _*)
  }
}