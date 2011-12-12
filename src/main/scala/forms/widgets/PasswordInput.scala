package forms.widgets

class PasswordInput(
    attrs: Map[String, String] = Map(),
    hidden_? : Boolean = false,
    needsMultipartForm_? : Boolean = false,
    required_? : Boolean = false,
    val renderValue_? : Boolean = false) extends Input(attrs, hidden_?, needsMultipartForm_?, required_?) {
  def inputType: String = "password"
    
  override def render(name: String, value: Option[String], attrList: Map[String, String]) = {
    super.render(name, if (renderValue_?) value else None, attrList)
  }
}