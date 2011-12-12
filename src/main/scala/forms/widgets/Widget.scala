package forms.widgets
import scala.xml.MetaData
import scala.xml.NodeSeq
import scala.xml.Null
import scala.xml.UnprefixedAttribute

abstract class Widget(
    val attrs: Map[String, String] = Map(),
    val hidden_? : Boolean = false,
    val needsMultipartForm_? : Boolean = false,
    val required_? : Boolean = false) {

  def render(name: String, value: Option[String], attrList: Map[String, String] = Map()): NodeSeq
}

object Widget {
  def toMetaData(atts: Map[String, String]): MetaData = {
    atts.foldRight[MetaData](Null)(
        (keyValue: (String, String), rest: MetaData) => 
          new UnprefixedAttribute(keyValue._1, keyValue._2, rest))
  }
}