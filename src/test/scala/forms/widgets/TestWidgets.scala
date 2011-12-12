package forms.widgets
import org.scalatest.junit.JUnitSuite
import org.junit.Test

class TestWidgets extends JUnitSuite {
  @Test
  def textInputs() {
    val ti1 = new TextInput(Map("class"->"foo"))
    assert(ti1.render("entry", None) === <input type="text" name="entry" class="foo" />)
    assert(ti1.render("entry", Some("abc")) === 
      <input type="text" name="entry" value="abc" class="foo" />)
  }
  
  @Test
  def passwordInputs() {
    val pi1 = new PasswordInput()
    val pi2 = new PasswordInput(renderValue_? = true)
    assert(pi1.render("pw", Some("pass")) === <input type="password" name="pw" />)
    assert(pi2.render("pw", Some("pass")) === <input type="password" name="pw" value="pass" />)
  }
}