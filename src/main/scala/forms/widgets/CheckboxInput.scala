package forms.widgets

import scala.collection.immutable.Map
import scala.xml.NodeSeq
import scala.xml.Null

class CheckboxInput(
    attrs: Map[String, String] = Map(),
    val checkFun: (Option[String] => Boolean) = CheckboxInput.defaultCheckFun(_))
  extends Widget(attrs, false) {

  def render(name: String, value: Option[String], attrList: Map[String, String]): NodeSeq = {
    <input type="checkbox" name={ name } /> %
    	(if (checkFun(value)) Widget.toMetaData(Map("checked"->"checked")) else Null) %
        (if (CheckboxInput.worthMentioning(value)) Widget.toMetaData(Map("value"->value.get)) else Null) %
        Widget.toMetaData(this.attrs).append(Widget.toMetaData(attrList))
  }
}

object CheckboxInput {
  def defaultCheckFun(value: Option[String]): Boolean = {
    value match {
      case Some(str) => str != "" && str != "false"
      case None => false
    }
  }
  
  def worthMentioning(value: Option[String]): Boolean = {
    value match {
      case Some(str) => str != "" && str != "true" && str != "false"
      case None => false
    }
  }
}