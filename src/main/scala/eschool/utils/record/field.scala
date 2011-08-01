package eschool.utils.record.field

import net.liftweb.common._
import org.bson.types.ObjectId
import net.liftweb.json.JsonAST._
import net.liftweb.http.S
import net.liftweb.http.js.JE._
import net.liftweb.json.Printer
import net.liftweb.record._
import net.liftweb.util.Helpers._
import net.liftweb.mongodb.record.BsonRecord

/*
trait ObjectIdTypedField extends TypedField[ObjectId] {
  def owner[OwnerType <: BsonRecord[OwnerType]]: OwnerType

    def defaultValue = ObjectId.get

  def setFromAny(in: Any): Box[ObjectId] = in match {
    case oid: ObjectId => setBox(Full(oid))
    case Some(oid: ObjectId) => setBox(Full(oid))
    case Full(oid: ObjectId) => setBox(Full(oid))
    case (oid: ObjectId) :: _ => setBox(Full(oid))
    case s: String => setFromString(s)
    case Some(s: String) => setFromString(s)
    case Full(s: String) => setFromString(s)
    case null|None|Empty => setBox(defaultValueBox)
    case f: Failure => setBox(f)
    case o => setFromString(o.toString)
  }

  def setFromJValue(jvalue: JValue): Box[ObjectId] = jvalue match {
    case JNothing|JNull if optional_? => setBox(Empty)
    case JObject(JField("$oid", JString(s)) :: Nil) => setFromString(s)
    case JString(s) => setFromString(s)
    case other => setBox(FieldHelpers.expectedA("JObject", other))
  }

  def setFromString(in: String): Box[ObjectId] =
    if (ObjectId.isValid(in))
      setBox(Full(new ObjectId(in)))
    else
      setBox(Failure("Invalid ObjectId string: "+in))

  private def elem =
    S.fmapFunc(S.SFuncHolder(this.setFromAny(_))){funcName =>
      <input type="text"
        name={funcName}
        value={valueBox.map(s => s.toString) openOr ""}
        tabindex={tabIndex toString}/>
    }

  def toForm =
    uniqueFieldId match {
      case Full(id) => Full(elem % ("id" -> id))
      case _ => Full(elem)
    }

  def asJs = asJValue match {
    case JNothing => JsNull
    case jv => JsRaw(Printer.compact(render(jv)))
  }

  def asJValue: JValue = valueBox.map(v => Meta.objectIdAsJValue(v, owner.meta.formats)) openOr (JNothing: JValue)
}

class ObjectIdField[OwnerType <: BsonRecord[OwnerType]](rec: OwnerType)
  extends Field[ObjectId, OwnerType]
  with MandatoryTypedField[ObjectId]
  with ObjectIdTypedField
{
  def owner = rec
}

class OptionalObjectIdField[OwnerType <: BsonRecord[OwnerType]](rec: OwnerType)
  extends Field[ObjectId, OwnerType]
  with OptionalTypedField[ObjectId]
  with ObjectIdTypedField
{
  def owner = rec
}
*/
