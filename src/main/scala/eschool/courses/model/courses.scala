package eschool.courses.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{Box, Empty, Full}
import org.bson.types.ObjectId
import eschool.utils.model.XmlField

class Course extends MongoRecord[Course] with ObjectIdPk[Course] {
  def meta = Course

  object name extends StringField(this, 80)
  object stateId extends StringField(this, 20)
}

object Course extends Course with MongoMetaRecord[Course] {
  ensureIndex("courseName" -> 1, "unique" -> true)
  ensureIndex("courseId" -> 1)
  ensureIndex("objectives" -> 1)
}

class Section extends MongoRecord[Section] with ObjectIdPk[Section] {
  def meta = Section

  object course extends ObjectIdRefField[Section, Course](this, Course)
  object content extends MongoListField[Section, ObjectId](this) // At this level, this is either units and/or assessments
  object terms extends MongoListField[Section, ObjectId](this)
  object teachers extends MongoListField[Section, ObjectId](this)
  object students extends MongoListField[Section, ObjectId](this)

  // TODO: Write definition for teachers and students to pick them out of users
  // TODO: Add a definition for schools and a field for schools in Section
  // TODO: Add a way to keep track of blocks/periods
}

object Section extends Section with MongoMetaRecord[Section] {
  ensureIndex("course" -> 1)
}

class Term extends MongoRecord[Term] with ObjectIdPk[Term] {
  def meta = Term

  object startDate extends DateField(this)
  object endDate extends DateField(this)
  object year extends ObjectIdRefField[Term, AcadYear](this, AcadYear)
}

object Term extends Term with MongoMetaRecord[Term] {
  ensureIndex("startDate" -> 1, "unique" -> true)
  ensureIndex("endDate" -> 1, "unique" -> true)
  ensureIndex("year" -> 1)
}

class AcadYear extends MongoRecord[AcadYear] with ObjectIdPk[AcadYear] {
  def meta = AcadYear

  object startDate extends DateField(this)
  object endDate extends DateField(this)
}

object AcadYear extends AcadYear with MongoMetaRecord[AcadYear] {
  ensureIndex("startDate" -> 1, "unique" -> true)
  ensureIndex("endDate" -> 1, "unique" -> true)
}

class CourseUnit extends MongoRecord[CourseUnit] with ObjectIdPk[CourseUnit] {
  def meta = CourseUnit

  object name extends StringField(this, 40)
  object content extends MongoListField[CourseUnit, ObjectId](this) // At this level, this is either lessons and/or assessments
}

object CourseUnit extends CourseUnit with MongoMetaRecord[CourseUnit] {
  ensureIndex("name" -> 1)
}

class Lesson extends MongoRecord[Lesson] with ObjectIdPk[Lesson] {
  def meta = Lesson

  object name extends StringField(this, 40)
  object objectives extends MongoListField[Lesson, ObjectId](this)
  object content extends MongoListField[Lesson, ObjectId](this) // This could be assessments in addition to other types of content
}

object Lesson extends Lesson with MongoMetaRecord[Lesson] {
  ensureIndex("name" -> 1)
}

class Objective extends MongoRecord[Objective] with ObjectIdPk[Objective] {
  def meta = Objective

  object name extends StringField(this, 40)
  object stateId extends StringField(this, 20)
  object description extends XmlField(this)
}

object Objective extends Objective with MongoMetaRecord[Objective] {
  ensureIndex("name" -> 1, "unique" -> true)
  ensureIndex("stateId" -> 1, "unique" -> true)
}