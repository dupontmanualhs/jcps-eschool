package eschool.courses.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.common._
import eschool.users.model.{Student, Teacher}
import eschool.utils.record.field.{MongoRecordField, MongoRecordListField}

class School extends MongoRecord[School] with ObjectIdPk[School] {
  def meta = School

  object name extends StringField(this, 80)
  object shortName extends StringField(this, 20)
}

object School extends School with MongoMetaRecord[School] {
  override def collectionName = "schools"
}

class Department extends MongoRecord[Department] with ObjectIdPk[Department] {
  def meta = Department

  object name extends StringField(this, 80)
}

object Department extends Department with MongoMetaRecord[Department] {
  override def collectionName = "departments"
}

class Course extends MongoRecord[Course] with ObjectIdPk[Course] {
  def meta = Course

  object name extends StringField(this, 80)
  object stateId extends StringField(this, 20)
}

object Course extends Course with MongoMetaRecord[Course] {
  ensureIndex("courseName" -> 1, "unique" -> true)
  ensureIndex("stateId" -> 1, "unique" -> true)

  override def collectionName = "courses"
}

class Section extends MongoRecord[Section] with ObjectIdPk[Section] {
  def meta = Section

  object course extends MongoRecordField[Section, Course](this, Course)
  object school extends MongoRecordField[Section, School](this, School)
  object number extends IntField(this)
  object terms extends MongoRecordListField[Section, Term](this, Term)
  object teacherAssignments extends MongoRecordListField[Section, TeacherAssignment](this, TeacherAssignment)
  object studentEnrollments extends MongoRecordListField[Section, StudentEnrollment](this, StudentEnrollment)
}

object Section extends Section with MongoMetaRecord[Section] {
  ensureIndex("course" -> 1)

  override def collectionName = "sections"
}

class Term extends MongoRecord[Term] with ObjectIdPk[Term] {
  def meta = Term

  object year extends MongoRecordField[Term, AcademicYear](this, AcademicYear)
  object startDate extends DateField(this)
  object endDate extends DateField(this)
}

object Term extends Term with MongoMetaRecord[Term] {
  override def collectionName = "terms"
}

class AcademicYear extends MongoRecord[AcademicYear] with ObjectIdPk[AcademicYear] {
  def meta = AcademicYear

  object startDate extends DateField(this)
  object endDate extends DateField(this)
}

object AcademicYear extends AcademicYear with MongoMetaRecord[AcademicYear] {
  override def collectionName = "academicYears"
}

class TeacherAssignment extends MongoRecord[TeacherAssignment] with ObjectIdPk[TeacherAssignment] {
  def meta = TeacherAssignment

  object teacher extends MongoRecordField[TeacherAssignment, Teacher](this, Teacher)
  object startDate extends DateField(this) {
    override def optional_? = true
  }
  object endData extends DateField(this) {
    override def optional_? = true
  }
}

object TeacherAssignment extends TeacherAssignment with MongoMetaRecord[TeacherAssignment] {
  override def collectionName = "teacherAssignments"
  ensureIndex("teacher" -> 1)
}

class StudentEnrollment extends MongoRecord[StudentEnrollment] with ObjectIdPk[StudentEnrollment] {
  def meta = StudentEnrollment

  object student extends MongoRecordField[StudentEnrollment, Student](this, Student)
  object startData extends DateField(this) {
    override def optional_? = true
  }
  object endData extends DateField(this) {
    override def optional_? = true
  }
}

object StudentEnrollment extends StudentEnrollment with MongoMetaRecord[StudentEnrollment] {
  override def collectionName = "studentEnrollments"
  ensureIndex("student" -> 1)
}

