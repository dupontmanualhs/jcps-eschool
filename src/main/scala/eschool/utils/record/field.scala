package eschool.utils.record.field

import tools.cmd.Meta
import net.liftweb.common.{Failure, Empty, Full, Box}
import net.liftweb.json.JsonAST.{JArray, JValue}
import net.liftweb.record.{FieldHelpers, MandatoryTypedField, Field}
import net.liftweb.json.JsonParser
import xml.NodeSeq
import net.liftweb.mongodb.record.{MongoMetaRecord, BsonRecord}
import org.apache.xalan.xsltc.compiler.util.ObjectType
import org.ietf.jgss.Oid
import org.bson.types.ObjectId
import com.mongodb.{BasicDBList, DBObject}
import net.liftweb.mongodb.record.field.{ObjectIdPk, MongoFieldFlavor}

class MongoRecordListField[OwnerType <: BsonRecord[OwnerType], ObjType <: ObjectIdPk[ObjType]](rec: OwnerType)
  extends Field[List[ObjType], OwnerType]
  with MandatoryTypedField[List[ObjType]]
  with MongoFieldFlavor[List[ObjType]]
{

  import Meta.Reflection._

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
    case JArray(arr) => setBox(Full(arr.map(_.values.asInstanceOf[ObjType])))
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

  def asJValue = JArray(value.map(li => li.asInstanceOf[AnyRef] match {
    case x if primitive_?(x.getClass) => primitive2jvalue(x)
    case x if mongotype_?(x.getClass) => mongotype2jvalue(x)(owner.meta.formats)
    case x if datetype_?(x.getClass) => datetype2jvalue(x)(owner.meta.formats)
    case _ => JNothing
  }))

  /*
  * Convert this field's value into a DBObject so it can be stored in Mongo.
  */
  def asDBObject: DBObject = {
    new BasicDBList().addAll(value map _.id.get)
  }

  // set this field's value using a DBObject returned from Mongo.
  def setFromDBObject(dbo: DBObject): Box[List[ObjType]] =
    setBox(Full(dbo.asInstanceOf[BasicDBList].toList.map(
      (oid: Object) => ObjType.find(oid.asInstanceOf[ObjectId]) openOr null))
    )
}
