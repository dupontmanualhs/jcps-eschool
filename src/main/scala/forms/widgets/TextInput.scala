package forms.widgets

import scala.xml.NodeSeq

class TextInput(
    attrs: Map[String, String] = Map(),
    isRequired: Boolean = false) extends Input(attrs, isRequired) {

  def inputType: String = "text"
}