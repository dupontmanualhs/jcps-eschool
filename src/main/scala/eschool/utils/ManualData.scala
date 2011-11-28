package eschool.utils

import scala.collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Sheet, Row, WorkbookFactory}
import xml.{Node, Elem, XML}
import eschool.users.model.{Student, Teacher, User}
import bootstrap.liftweb.{Boot, DataStore}
import org.joda.time.LocalDate
import eschool.courses.model._
import net.liftweb.common._
import org.joda.time.format.DateTimeFormat
import jdohelpers.Gender
import eschool.courses.model.QPeriod
import eschool.users.model.QTeacher
import eschool.courses.model.Section
import eschool.courses.model.TeacherAssignment
import eschool.courses.model.QSection
import eschool.users.model.QStudent
import eschool.courses.model.StudentEnrollment

object ManualData {
  val netIdMap: Map[String, String] = buildNetIdMap()

  def load(debug: Boolean = false) {
    new Boot().boot()
    createYearsAndTerms(debug)
    loadStudents(debug)
    loadTeachers(debug)
    loadCourses(debug)
    loadSections(debug)
    loadEnrollments(debug)
  }

  def createYearsAndTerms(debug: Boolean) {
    val acadYear = new AcademicYear("2011-12")
    DataStore.pm.makePersistent(acadYear)
    val fall2011 = new Term("Fall 2011", acadYear, new LocalDate(2011, 8, 17), new LocalDate(2012, 12, 16))
    DataStore.pm.makePersistent(fall2011)
    val spring2012 = new Term("Spring2012", acadYear, new LocalDate(2012, 1, 3), new LocalDate(2012, 5, 25))
    DataStore.pm.makePersistent(spring2012)
    val periods: List[Period] = List(
        new Period("Red 1", 1), new Period("Red 2", 2), new Period("Red 3", 3), new Period("Red 4", 4),
        new Period("Red Activity", 5), new Period("Red Advisory", 6),
        new Period("White 1", 7), new Period("White 2", 8), new Period("White 3", 9), new Period("White 4", 10),
        new Period("White Activity", 11), new Period("White Advisory", 12))
    DataStore.pm.makePersistentAll(periods)
    if (debug) println("Created AcademicYear, Terms, and Periods")
    DataStore.pm.commitTransaction()
  }

  def loadStudents(debug: Boolean) {
    DataStore.pm.beginTransaction()
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
      val gender = if ((student \ "@student.gender").text == "F") Gender.FEMALE else Gender.MALE
      val username = netIdMap.getOrElse(studentNumber, studentNumber)
      if (debug) {
        println()
        println("%s, %s %s".format(last, first, middle))
        println("#: %s, id: %s, grade: %d".format(studentNumber, stateId, grade))
        println("name: %s, magnet: %s, gender: %s".format(username, teamName, gender))
      }
       // create User
      val user = new User(username, first, Some(middle), last, None, gender, null, "temp123")
      DataStore.pm.makePersistent(user)
      if (debug) println("user saved")
      // create Student
      val dbStudent = new Student(user, stateId, studentNumber, grade, teamName)
      DataStore.pm.makePersistent(dbStudent)
      if (debug) println("student saved")
    })
    DataStore.pm.commitTransaction()
  }

  def loadTeachers(debug: Boolean) {
    DataStore.pm.beginTransaction()
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Teachers.xml"))
    val teachers = doc \\ "person"
    teachers foreach ((teacher: Node) => {
      val username = asIdNumber((teacher \ "@individual.personID").text) // TODO: get real login name
      val first = (teacher \ "@individual.firstName").text
      val middle = (teacher \ "@individual.middleName").text
      val last = (teacher \ "@individual.lastName").text
      val gender = if ((teacher \ "@individual.gender").text == "F") Gender.FEMALE else Gender.MALE
      val personId = asIdNumber((teacher \ "@individual.personID").text)
      val stateId = asIdNumber((teacher \ "@individual.stateID").text)
      if (debug) {
        println()
        println("%s, %s %s".format(last, first, middle))
        println("#: %s, id: %s".format(personId, stateId))
        println("name: %s, gender: %s".format(username, gender))
      }
      val user = new User(username, first, Some(middle), last, None, gender, null, "temp123")
      DataStore.pm.makePersistent(user)
      if (debug) println("user saved")
      val dbTeacher = new Teacher(user, personId, stateId)
      DataStore.pm.makePersistent(dbTeacher)
      if (debug) println("teacher saved")
    })
    DataStore.pm.commitTransaction()
  }

  def loadCourses(debug: Boolean) {
    DataStore.pm.beginTransaction()
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Courses.xml"))
    val courses = doc \\ "curriculum"
    courses foreach ((course: Node) => {
      val name = (course \ "@courseInfo.courseName").text
      val masterNumber = asIdNumber((course \ "@courseInfo.courseMasterNumber").text)
      val dept = Department.getOrCreate((course \ "@courseInfo.departmentName").text)
      if (debug) println("%s, %s (%s)".format(name, masterNumber, dept))
      val dbCourse = new Course(name, masterNumber, dept)
      DataStore.pm.makePersistent(dbCourse)
    })
    DataStore.pm.commitTransaction()
  }

  def loadSections(debug: Boolean) {
    DataStore.pm.beginTransaction()
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Sections.xml"))
    val sections = doc \\ "curriculum"
    val fall11 = DataStore.pm.query[Term].filter(QTerm.candidate.name.eq("Fall 2011")).executeOption().get
    val spring12 = DataStore.pm.query[Term].filter(QTerm.candidate.name.eq("Fall 2011")).executeOption().get
    sections foreach ((section: Node) => {
      val sectionId = (section \ "@sectionInfo.sectionID").text
      if (debug) println("Working on section: %s".format(sectionId))
      val courseMasterNumber = asIdNumber((section \ "@courseInfo.courseMasterNumber").text)
      val course = DataStore.pm.query[Course].filter(QCourse.candidate.masterNumber.eq(courseMasterNumber)).executeOption().get
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
        DataStore.pm.query[Period].filter(QPeriod.candidate.name.eq(p)).executeOption().get
      })
      val teacherPersonId = (section \ "@sectionInfo.teacherPersonID").text
      val teacher = DataStore.pm.query[Teacher].filter(QTeacher.candidate.personId.eq(teacherPersonId)).executeOption().get
      val dbSection = new Section(course, sectionId, terms.toSet[Term], periods.toSet[Period], room)
      DataStore.pm.makePersistent(dbSection)
      terms foreach ((term: Term) => {
        val teacherAssignment = new TeacherAssignment(teacher, dbSection, term, null, null)
        DataStore.pm.makePersistent(teacherAssignment)
      })
    })
    DataStore.pm.commitTransaction()
  }

  def loadEnrollments(debug: Boolean) {
    DataStore.pm.beginTransaction()
	import scala.collection.JavaConversions.asScalaSet
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Schedule.xml"))
    val enrollments = doc \\ "student"
    enrollments foreach  ((enrollment: Node) => {
      val sectionId = asIdNumber((enrollment \ "@courseSection.sectionID").text)
      val section = DataStore.pm.query[Section].filter(QSection.candidate.sectionId.eq(sectionId)).executeOption().get
      val studentNumber = asIdNumber((enrollment \ "@student.studentNumber").text)
      val student = DataStore.pm.query[Student].filter(QStudent.candidate.studentNumber.eq(studentNumber)).executeOption().get
      if (debug) println("Adding student #%s to section #%s".format(studentNumber, sectionId))
      val startDate = asLocalDate((enrollment \ "@roster.startDate").text)
      val endDate = asLocalDate((enrollment \ "@roster.endDate").text)
      asScalaSet[Term](section.terms) foreach ((term: Term) => {
        val dbEnrollment = new StudentEnrollment(student, section, term, startDate, endDate)
        DataStore.pm.makePersistent(dbEnrollment)
      })
    })
    DataStore.pm.commitTransaction()
  }

  def asLocalDate(date: String): LocalDate = {
    val format = DateTimeFormat.forPattern("MM/dd/yyyy")
    date match {
      case "" => null
      case _ =>  format.parseDateTime(date).toLocalDate
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
    val num = s.replaceAll("^0+", "")
    if (num.equals("")) null else num
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