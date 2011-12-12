package forms.widgets

class HiddenInput(
    attrs: Map[String, String] = Map(),
    needsMultipartForm_? : Boolean = false,
    required_? : Boolean = false) 
    extends Input(attrs, hidden_? = true, needsMultipartForm_?, required_?) {
  def inputType(): String = "hidden"
}