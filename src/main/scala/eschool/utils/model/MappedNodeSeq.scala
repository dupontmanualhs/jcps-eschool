package eschool.utils.model

import net.liftweb.mapper._
import net.liftweb.util.FatLazy._
import net.liftweb.common._
import java.sql.Types
import net.liftweb.json.JsonAST
import net.liftweb.http.S._
import net.liftweb.common.{Empty, Box}
import net.liftweb.http.js.{JE, JsExp}
import net.liftweb.common.Full._
import java.lang.reflect.Method
import java.util.Date
import net.liftweb.util.{FieldError, FatLazy}
import xml.Text._
import net.liftweb.http.S
import xml.{XML, NodeSeq, Elem}
import org.xml.sax.SAXParseException
import java.math.MathContext

/**
 * <p>
 * A field that maps to a NodeSeq.
 * </p>
 *
 */
abstract class MappedNodeSeq[T <: Mapper[T]] (val fieldOwner : T) extends MappedField[NodeSeq,T] {
  def defaultValue = NodeSeq.Empty

  def dbFieldClass = classOf[NodeSeq]

  private var data : NodeSeq = defaultValue
  private var orgData : NodeSeq = defaultValue

  private def st(in : NodeSeq) = {
    data = in
    orgData = in
  }

  protected def i_is_! = data
  protected def i_was_! = orgData

  override def doneWithSave() {
    orgData = data
  }

  override def readPermission_? = true
  override def writePermission_? = true

  protected def i_obscure_!(in : NodeSeq) = defaultValue

  protected def real_i_set_!(value : NodeSeq): NodeSeq = {
    if (value != data) {
      data = value
      dirty_?(true)
    }
    data
  }

  def asJsExp: JsExp = JE.Str(is.toString)
  def asJsonValue: Box[JsonAST.JString] = Full(JsonAST.JString(is.toString))

  def setFromAny(in : Any) : NodeSeq =
    in match {
      case ns: NodeSeq => set(ns)
      case n :: _ => setFromString(n.toString)
      case Some(n) => setFromString(n.toString)
      case Full(n) => setFromString(n.toString)
      case None | Empty | Failure(_, _, _) | null => set(NodeSeq.Empty)
      case n => setFromString(n.toString)
    }

  private def parseNodeSeq(in: String): NodeSeq = {
    val el: Elem = XML.loadString("<dummy>" + in + "</dummy>")
    el.child
  }

  def setFromString(in : String) : NodeSeq = {
    this.set(parseNodeSeq(in))
    data
  }

  def targetSQLType = Types.CLOB

  def jdbcFriendly(field : String) = i_is_!.toString

  def real_convertToJDBCFriendly(value: NodeSeq): Object = value.toString

  def buildSetBooleanValue(accessor : Method, columnName : String) : (T, Boolean, Boolean) => Unit = null

  def buildSetDateValue(accessor : Method, columnName : String) : (T, Date) => Unit = null

  def buildSetStringValue(accessor: Method, columnName: String): (T, String) =>
    Unit = (inst, v) => doField(inst, accessor, {case f: MappedNodeSeq[T] => f.set(if (v == null) defaultValue else parseNodeSeq(v))})

  def buildSetLongValue(accessor: Method, columnName : String) : (T, Long, Boolean) =>
    Unit = null

  def buildSetActualValue(accessor: Method, data: AnyRef, columnName: String) : (T, AnyRef) =>
    Unit = (inst, v) => doField(inst, accessor, {case f: MappedNodeSeq[T] => f.set(if (v == null) defaultValue else parseNodeSeq(v.toString))})

  def fieldCreatorString(dbType: DriverType, colName: String): String = colName + " " + dbType.clobColumnType + notNullAppender()

}
