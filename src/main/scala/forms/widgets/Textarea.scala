package forms.widgets

import scala.collection.immutable.Map
import scala.xml.NodeSeq

class Textarea(
    attrMap: Map[String, String] = Map(),
    isRequired: Boolean = false)
    extends Widget(Map("cols"->"40", "rows"->"10") ++ attrMap, isRequired) {

  def render(name: String, value: Option[String], attrList: Map[String, String] = Map()): NodeSeq = {
    <textarea name={ name }>{ value.getOrElse("") }</textarea> % 
    	Widget.toMetaData(attrs).append(Widget.toMetaData(attrList))
  }

}