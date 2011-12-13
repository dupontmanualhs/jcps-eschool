package forms.widgets

class PasswordInput(
    attrs: Map[String, String] = Map(),
    isRequired: Boolean = false,
    val renderValue: Boolean = false) extends Input(attrs, isRequired) {

  def inputType: String = "password"
    
  override def render(name: String, value: Option[String], attrList: Map[String, String]) = {
    super.render(name, if (renderValue) value else None, attrList)
  }
}