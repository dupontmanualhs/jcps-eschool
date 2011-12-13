package forms.widgets

class HiddenInput(
    attrs: Map[String, String] = Map(),
    isRequired: Boolean = false) 
    extends Input(attrs, isRequired) {
  
  override def isHidden: Boolean = true
  
  def inputType: String = "hidden"
}