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

class MCQuestion extends MongoRecord[MCQuestion] with ObjectIdPk[MCQuestion] {
  def meta = MCQuestion

  object prompt extends XmlField(this)
  object choices extends MongoListField[MCQuestion, ObjectId](this)
}

object MCQuestion extends MCQuestion with MongoMetaRecord[MCQuestion] {
  ensureIndex("prompt" -> 1)
}

class MCChoice extends MongoRecord[MCChoice] with ObjectIdPk[MCChoice] {
  def meta = MCChoice

  object choice extends XmlField(this)
  object correct extends BooleanField(this)
}

object MCChoice extends MCChoice with MongoMetaRecord[MCChoice] {
  ensureIndex("choice" -> 1)
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

  object pageNumber extends StringField(this, 10)
  object pageContent extends XmlField(this)
}

object PresentationPage extends PresentationPage with MongoMetaRecord[PresentationPage] {
  ensureIndex("pageContent" -> 1)
}