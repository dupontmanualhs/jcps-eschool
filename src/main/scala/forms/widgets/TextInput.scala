package forms.widgets

import scala.xml.NodeSeq

class TextInput(
    attrs: Map[String, String] = Map(),
    hidden_? : Boolean = false,
    needsMultipartForm_? : Boolean = false,
    required_? : Boolean = false) extends Input(attrs, hidden_?, needsMultipartForm_?, required_?) {
  def inputType: String = "text"
}