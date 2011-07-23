package eschool.courses.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
//import net.liftweb.common.{Box, Empty, Full}
import org.bson.types.ObjectId
import eschool.utils.model.HtmlField

class Course extends MongoRecord[Course] with ObjectIdPk[Course] {
  def meta = Course

  object courseName extends StringField(this, 80)
  object courseId extends StringField(this, 20)  // This is for the state course ID
  object objectives extends MongoListField[Course, ObjectId](this)
  object courseContent extends MongoListField[Course, ObjectId](this)
}

object Course extends Course with MongoMetaRecord[Course] {
  ensureIndex("courseName" -> 1, "unique" -> true)
  ensureIndex("courseId" -> 1)
  ensureIndex("objectives" -> 1)
}

class Section extends MongoRecord[Section] with ObjectIdPk[Section] {
  def meta = Section

  object course extends ObjectIdRefField[Section, Course](this, Course)
  object content extends MongoListField[Section, ObjectId](this)
  object terms extends MongoListField[Section, ObjectId](this)
  object users extends MongoListField[Section, ObjectId](this)

  // TODO: Write definition for teachers and students to pick them out of users
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

class Content extends MongoRecord[Content] with ObjectIdPk[Content] {
  def meta = Content

  object parent extends StringField(this, 80)
  object contentType extends StringField(this, 20)
  object material extends HtmlField(this)
}

object Content extends Content with MongoMetaRecord[Content] {
  ensureIndex("parent" -> 1)
  ensureIndex("contentType" -> 1)
}