package eschool.courses.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.common._
import eschool.users.model.{Student, Teacher}

import com.foursquare.rogue.Rogue._
import org.bson.types.ObjectId

class AcademicYear extends MongoRecord[AcademicYear] with ObjectIdPk[AcademicYear] {
  def meta = AcademicYear

  object name extends StringField(this, 20)
}

object AcademicYear extends AcademicYear with MongoMetaRecord[AcademicYear] {
  override def collectionName = "academicYears"
}

class Term extends MongoRecord[Term] with ObjectIdPk[Term] {
  def meta = Term

  object name extends StringField(this, 20)
  object year extends ObjectIdRefField(this, AcademicYear)
  object startDate extends DateField(this)
  object endDate extends DateField(this)
}

object Term extends Term with MongoMetaRecord[Term] {
  override def collectionName = "terms"
}

class Period extends MongoRecord[Period] with ObjectIdPk[Period] {
  def meta = Period

  object name extends StringField(this, 20)
}

object Period extends Period with MongoMetaRecord[Period] {
  override def collectionName = "periods"
}

/*
class School extends MongoRecord[School] with ObjectIdPk[School] {
  def meta = School

  object name extends StringField(this, 80)
  object shortName extends StringField(this, 20)
}

object School extends School with MongoMetaRecord[School] {
  override def collectionName = "schools"
}
*/

class Department extends MongoRecord[Department] with ObjectIdPk[Department] {
  def meta = Department

  object name extends StringField(this, 80)

  override def toString = name.get
}

object Department extends Department with MongoMetaRecord[Department] {
  override def collectionName = "departments"

  def getOrCreate(name: String): Department = {
    Department where (_.name eqs name) get() match {
      case Some(dept) => dept
      case None => {
        val dept = Department.createRecord.name(name)
        dept.save(true)
        dept
      }
    }
  }
}

class Course extends MongoRecord[Course] with ObjectIdPk[Course] {
  def meta = Course

  object name extends StringField(this, 80)
  object masterNumber extends StringField(this, 10)
  object department extends ObjectIdRefField(this, Department)
}

object Course extends Course with MongoMetaRecord[Course] {
  ensureIndex("masterNumber" -> 1, "unique" -> true)

  override def collectionName = "courses"
}

class Section extends MongoRecord[Section] with ObjectIdPk[Section] {
  def meta = Section

  object course extends ObjectIdRefField(this, Course)
  object sectionId extends StringField(this, 10)
  object terms extends MongoListField[Section, ObjectId](this)
  object periods extends MongoListField[Section, ObjectId](this)
  object room extends ObjectIdRefField(this, Room)
  object teacherAssignments extends MongoListField[Section, ObjectId](this)
  object studentEnrollments extends MongoListField[Section, ObjectId](this)
}

object Section extends Section with MongoMetaRecord[Section] {
  ensureIndex("course" -> 1)

  override def collectionName = "sections"
}

class Room extends MongoRecord[Room] with ObjectIdPk[Room] {
  def meta = Room

  object name extends StringField(this, 15)

  def getOrCreate(name: String): Room = {
    Room where (_.name eqs name) get() match {
      case Some(room) => room
      case None => {
        val room = Room.createRecord.name(name)
        room.save(true)
        room
      }
    }
  }
}

object Room extends Room with MongoMetaRecord[Room] {
  override def collectionName = "rooms"
}

class TeacherAssignment extends MongoRecord[TeacherAssignment] with ObjectIdPk[TeacherAssignment] {
  def meta = TeacherAssignment

  object teacher extends ObjectIdRefField(this, Teacher)
  object startDate extends DateField(this) {
    override def optional_? = true
  }
  object endDate extends DateField(this) {
    override def optional_? = true
  }
}

object TeacherAssignment extends TeacherAssignment with MongoMetaRecord[TeacherAssignment] {
  override def collectionName = "teacherAssignments"
  ensureIndex("teacher" -> 1)
}

class StudentEnrollment extends MongoRecord[StudentEnrollment] with ObjectIdPk[StudentEnrollment] {
  def meta = StudentEnrollment

  object student extends ObjectIdRefField(this, Student)
  object startDate extends DateField(this) {
    override def optional_? = true
  }
  object endDate extends DateField(this) {
    override def optional_? = true
  }
}

object StudentEnrollment extends StudentEnrollment with MongoMetaRecord[StudentEnrollment] {
  override def collectionName = "studentEnrollments"
  ensureIndex("student" -> 1)
}

