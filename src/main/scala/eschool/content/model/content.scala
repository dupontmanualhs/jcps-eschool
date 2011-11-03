package eschool.content.model
/*
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{Box, Empty, Full}

class UnitOfStudy extends MongoRecord[UnitOfStudy] with ObjectIdPk[UnitOfStudy] {
  def meta = UnitOfStudy

  object name extends StringField(this, 40)
  object content extends MongoListField[UnitOfStudy, ObjectId](this) // This could be lessons or assessments
}

object UnitOfStudy extends UnitOfStudy with MongoMetaRecord[UnitOfStudy] {
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

class Assessment extends MongoRecord[Assessment] with ObjectIdPk[Assessment] {
  def meta = Assessment

  object name extends StringField(this, 80)
  object questions extends MongoListField[Assessment, ObjectId](this)
}

object Assessment extends Assessment with MongoMetaRecord[Assessment] {
  ensureIndex("name" -> 1)
}

class Question extends MongoRecord[Question] with ObjectIdPk[Question] {
  def meta = Question

  object content extends XmlField(this)
  object source extends StringField(this, 80)
  object subject extends StringField(this, 80)
}

object Question extends Question with MongoMetaRecord[Question] {
  ensureIndex("content" -> 1)
}

class Presentation extends MongoRecord[Presentation] with ObjectIdPk[Presentation] {
  def meta = Presentation

  object title extends StringField(this, 80)
  object pages extends MongoListField[Presentation, ObjectId](this)
}

object Presentation extends Presentation with MongoMetaRecord[Presentation] {
  ensureIndex("title" -> 1)
}

class PresentationPage extends MongoRecord[PresentationPage] with ObjectIdPk[PresentationPage] {
  def meta = PresentationPage

  object pageNumber extends IntField(this)
  object pageContent extends XmlField(this)
}

object PresentationPage extends PresentationPage with MongoMetaRecord[PresentationPage] {
  ensureIndex("pageContent" -> 1)
}
*/