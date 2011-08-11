package eschool.utils.model

import net.liftweb.util.Helpers._
import xml.{Elem, XML, NodeSeq}
import net.liftweb.http.S
import net.liftweb.http.js.jquery.JqJE.JqHtml
import net.liftweb.http.js.JE.JsNull
import net.liftweb.http.js.JsExp
import net.liftweb.json.JsonAST.{JNull, JNothing, JString, JValue}
import net.liftweb.common.{Empty, Full, Box}
import net.liftweb.record.{FieldHelpers, Record, TypedField, Field, MandatoryTypedField}

trait XmlTypedField extends TypedField[NodeSeq] {
  def setFromAny(in: Any): Box[NodeSeq] = in match {
    case x: NodeSeq => setBox(Full(x))
    case Full(x: NodeSeq) => setBox(Full(x))
    case Some(x: NodeSeq) => setBox(Full(x))
    case (x: NodeSeq) :: _ => setBox(Full(x))
    case _ => genericSetFromAny(in)
  }
  private def parseNodeSeq(in: String): NodeSeq = {
    val el: Elem = XML.loadString("<dummy>" + in + "</dummy>")
    el.child
  }

  def setFromString(s: String): Box[NodeSeq] = setBox(tryo(parseNodeSeq(s)))

  def textareaRows = 20
  def textareaCols = 80

  private def elem = S.fmapFunc(S.SFuncHolder(this.setFromAny(_))){
    funcName => <textarea name={funcName}
      rows={textareaRows.toString}
      cols={textareaCols.toString}
      tabindex={tabIndex toString}>{valueBox openOr ""}</textarea>
  }

  override def toForm: Box[NodeSeq] = uniqueFieldId match {
    case Full(id) =>  Full(elem % ("id" -> id))
    case _ => Full(elem)
  }

  def asJs: JsExp = valueBox.map(JqHtml(_)) openOr JsNull

  def asJValue: JValue =  valueBox.map((ns: NodeSeq) => JString(ns.toString)) openOr (JNothing: JValue)

  def setFromJValue(jvalue: JValue) = jvalue match {
    case JNothing | JNull if optional_? => setBox(Empty)
    case JString(s) => setFromString(s)
    case other => setBox(FieldHelpers.expectedA("JString", other))
  }
}

/**
 * <p>
 * A field that maps to a NodeSeq.
 * </p>
 *
 */
class XmlField[OwnerType <: Record[OwnerType]](rec: OwnerType)
    extends Field[NodeSeq, OwnerType] with MandatoryTypedField[NodeSeq] with XmlTypedField {
  def owner = rec

  def this(rec: OwnerType, value: NodeSeq) = {
    this(rec)
    set(value)
  }

  def defaultValue = NodeSeq.Empty
}
