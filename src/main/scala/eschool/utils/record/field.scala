package eschool.utils.record.field

import scala.collection.JavaConversions._

import net.liftweb.common.{Failure, Empty, Full, Box}
import net.liftweb.record.{FieldHelpers, MandatoryTypedField, Field}
import net.liftweb.json.JsonParser
import xml.NodeSeq
import org.bson.types.ObjectId
import net.liftweb.json.JsonAST._
import net.liftweb.util.Helpers.tryo
import net.liftweb.mongodb.record.field.{ObjectIdPk, MongoFieldFlavor}
import net.liftweb.mongodb.record.{MongoRecord, MongoMetaRecord, BsonRecord}
import com.mongodb.{BasicDBObject, BasicDBList, DBObject}

/**
 TODO: Eventually fix this, but can't use because Rogue isn't compatible

class MongoRecordField[OwnerType <: BsonRecord[OwnerType], ObjType <: MongoRecord[ObjType] with ObjectIdPk[ObjType]]
(rec: OwnerType, meta: MongoMetaRecord[ObjType])
  extends Field[ObjType, OwnerType]
  with MandatoryTypedField[ObjType]
  with MongoFieldFlavor[ObjType]
{
  def owner = rec

  def defaultValue = null.asInstanceOf[ObjType]

  def setFromAny(in: Any): Box[ObjType] = {
    in match {
      case dbo: DBObject => setFromDBObject(dbo)
      case Some(obj: ObjType) => setBox(Full(obj))
      case Full(obj: ObjType) => setBox(Full(obj))
      case s: String => setFromString(s)
      case Some(s: String) => setFromString(s)
      case Full(s: String) => setFromString(s)
      case null|None|Empty => setBox(defaultValueBox)
      case f: Failure => setBox(f)
      case o => setFromString(o.toString)
    }
  }

  def setFromJValue(jvalue: JValue) = jvalue match {
    case JNothing|JNull if optional_? => setBox(Empty)
    case js: JString => setBox(meta.find(new ObjectId(js.toString)))
    case other => setBox(FieldHelpers.expectedA("JString", other))
  }

  // parse String into a JObject
  def setFromString(in: String): Box[ObjType] = tryo(JsonParser.parse(in)) match {
    case Full(jv: JValue) => setFromJValue(jv)
    case f: Failure => setBox(f)
    case other => setBox(Failure("Error parsing String into a JValue: "+in))
  }

  // TODO: This should return a select form
  def toForm: Box[NodeSeq] = Empty

  def asJValue = JString(value.id.get.toString)

  def asDBObject: DBObject = {
    new BasicDBObject("id", value.id.get)
  }

  def setFromDBObject(dbo: DBObject): Box[ObjType] = {
    meta.find(new ObjectId(dbo.get("id").asInstanceOf[String]))
  }
}

class MongoRecordListField[OwnerType <: BsonRecord[OwnerType], ObjType <: MongoRecord[ObjType] with ObjectIdPk[ObjType]]
(rec: OwnerType, meta: MongoMetaRecord[ObjType])
  extends Field[List[ObjType], OwnerType]
  with MandatoryTypedField[List[ObjType]]
  with MongoFieldFlavor[List[ObjType]]
{
  def owner = rec

  def defaultValue = List[ObjType]()

  def setFromAny(in: Any): Box[List[ObjType]] = {
    in match {
      case dbo: DBObject => setFromDBObject(dbo)
      case list: List[ObjType] => setBox(Full(list))
      case Some(list: List[ObjType]) => setBox(Full(list))
      case Full(list: List[ObjType]) => setBox(Full(list))
      case s: String => setFromString(s)
      case Some(s: String) => setFromString(s)
      case Full(s: String) => setFromString(s)
      case null|None|Empty => setBox(defaultValueBox)
      case f: Failure => setBox(f)
      case o => setFromString(o.toString)
    }
  }

  def setFromJValue(jvalue: JValue) = jvalue match {
    case JNothing|JNull if optional_? => setBox(Empty)
    case JArray(arr) => setBox(Full(arr map ((v: JValue) => {
      (meta.find(new ObjectId(v.asInstanceOf[JString].toString)) openOr null).asInstanceOf[ObjType]
    })))
    case other => setBox(FieldHelpers.expectedA("JArray", other))
  }

  // parse String into a JObject
  def setFromString(in: String): Box[List[ObjType]] = tryo(JsonParser.parse(in)) match {
    case Full(jv: JValue) => setFromJValue(jv)
    case f: Failure => setBox(f)
    case other => setBox(Failure("Error parsing String into a JValue: "+in))
  }

  // TODO: This should return a multiple select form
  def toForm: Box[NodeSeq] = Empty

  def asJValue = JArray(value.map((obj: ObjType) => JString(obj.id.get.toString)))

  /*
  * Convert this field's value into a DBObject so it can be stored in Mongo.
  */
  def asDBObject: DBObject = {
    val dbo: BasicDBList = new BasicDBList()
    dbo.addAll(value map ((obj: ObjType) => obj.id.get))
    dbo
  }

  // set this field's value using a DBObject returned from Mongo.
  def setFromDBObject(dbo: DBObject): Box[List[ObjType]] = {
    def jStringToObj(js: JString): ObjType = {
      (meta.find(new ObjectId(js.toString)) openOr null).asInstanceOf[ObjType]
    }
    val listOfOids: List[JString] = iterableAsScalaIterable(
      dbo.asInstanceOf[BasicDBList]).toList.map(_.asInstanceOf[JString])
    setBox(Full(listOfOids.map(jStringToObj _)))
  }
}
*/