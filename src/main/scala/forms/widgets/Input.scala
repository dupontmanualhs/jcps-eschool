package forms.widgets
import scala.xml.NodeSeq
import scala.xml.Null

abstract class Input(
    attrs: Map[String, String] = Map(),
    isRequired : Boolean = false) extends Widget(attrs, isRequired) {

  def inputType: String
  
  def render(name: String, value: Option[String], attrList: Map[String, String]): NodeSeq = {
    val elem = <input type={ inputType } name={ name } /> % 
      (value match {
        case Some(theVal) => Widget.toMetaData(Map("value" -> theVal))
        case None => Null
      })
    elem % Widget.toMetaData(this.attrs).append(Widget.toMetaData(attrList))
  }
}