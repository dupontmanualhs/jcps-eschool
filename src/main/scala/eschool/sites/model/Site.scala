package eschool.sites.model

import net.liftweb.json.JsonDSL._
import net.liftweb.record.field.StringField
import eschool.users.model.User
import eschool.utils.model.HtmlField
import net.liftweb.mongodb.record.field._
import net.liftweb.mongodb.record.{BsonRecord, BsonMetaRecord, MongoMetaRecord, MongoRecord}

class Site extends MongoRecord[Site] with ObjectIdPk[Site] {
  def meta = Site

  object owner extends ObjectIdField[Site](this)
  object name extends StringField(this, 80)
  object ident extends StringField(this, 10)
  object pages extends MongoListField[Site, Page](this)
}

object Site extends Site with MongoMetaRecord[Site] {
  ensureIndex(("owner" -> 1) ~ ("name" -> 1), "unique" -> 1)
  ensureIndex(("owner" -> 1) ~ ("ident" -> 1), "unique" -> 1)
}

class Page extends BsonRecord[Page] {
  def meta = Page

  object name extends StringField(this, 80)
  object content extends ObjectIdRefField[Page, Content](this, Content)
  object pages extends MongoListField[Page, Page](this)
}

object Page extends Page with BsonMetaRecord[Page] {
  // TODO: indices
}

class Content extends MongoRecord[Content] with ObjectIdPk[Content] {
  def meta = Content

  object html extends HtmlField(this)
}

object Content extends Content with MongoMetaRecord[Content] {

}