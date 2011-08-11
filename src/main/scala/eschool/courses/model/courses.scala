package eschool.courses.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{Box, Empty, Full}
import org.bson.types.ObjectId
import eschool.utils.model.HtmlField
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
  object terms extends MongoRecordListField[Section, ObjectId](this)
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
  override def collectionName = "acadyears"
}

