package eschool.content.model

import net.liftweb.mongodb.record._
import net.liftweb.mongodb.record.field._
import net.liftweb.record.field._
import net.liftweb.json.JsonDSL._
import net.liftweb.common.{Box, Empty, Full}
import org.bson.types.ObjectId
import eschool.utils.model.XmlField

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