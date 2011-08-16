package eschool.utils

import record.Gender
import scala.collection.JavaConversions._
import org.apache.poi.ss.usermodel.{Sheet, Row, WorkbookFactory}
import xml.{Node, Elem, XML}
import com.mongodb.Mongo
import eschool.users.model.{Student, User}
import bootstrap.liftweb.Boot

object ManualData {
  val netIdMap: Map[String, String] = buildNetIdMap()

  def load() {
    new Boot().boot()
    new Mongo().dropDatabase("eschool")
    loadStudents()
  }

  def loadStudents() {
    val doc = XML.load(getClass.getResourceAsStream("/manual-data/Students.xml"))
    val students = doc \\ "student"
    students foreach ((student: Node) => {
      // grab data
      val studentNumber = (student \ "@student.studentNumber").text.toInt.toString
      val stateId = (student \ "@student.stateID").text
      val first = (student \ "@student.firstName").text
      val middle = (student \ "@student.middleName").text
      val last = (student \ "@student.lastName").text
      val teamName = (student \ "@student.teamName").text
      val grade = (student \ "@student.grade").text.toInt
      val gender = Gender((student \ "@student.gender").text)
      val username = netIdMap.getOrElse(studentNumber, studentNumber)
      println()
      println("%s, %s %s".format(last, first, middle))
      println("#: %s, id: %s, grade: %d".format(studentNumber, stateId, grade))
      println("name: %s, magnet: %s, gender: %s".format(username, teamName, gender))

       // create User
      val user = User.createRecord.username(username).first(first).middle(middle).last(last).gender(gender)
      user.save(true)
      println("user saved")
      // create Student
      val dbStudent = Student.createRecord.user(user).stateId(stateId).studentNumber(studentNumber).grade(grade).teamName(teamName)
      dbStudent.save(true)
      println("student saved")
    })
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