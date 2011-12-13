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
    val pi2 = new PasswordInput(renderValue = true)
    assert(pi1.render("pw", Some("pass")) === <input type="password" name="pw" />)
    assert(pi2.render("pw", Some("pass")) === <input type="password" name="pw" value="pass" />)
  }
  
  @Test
  def hiddenInputs() {
    val hi1 = new HiddenInput(Map("class"->"foo"))
    assert(hi1.render("key1", Some("value1")) === 
        <input type="hidden" name="key1" value="value1" class="foo" />)
  }
  
  @Test
  def textareas() {
    val ta = new Textarea()
    assert(ta.render("words", Some("abc")) ===
      <textarea name="words" cols="40" rows="10">abc</textarea>)
    assert(ta.render("moreWords", None, Map("rows"->"4")) ===
      <textarea name="moreWords" cols="40" rows="4"></textarea>)
    assert(ta.render("wordsAlso", Some("xyz"), Map("cols"->"80")) ===
      <textarea name="wordsAlso" cols="80" rows="10">xyz</textarea>)
  }
  
  @Test
  def checkboxInputs() {
    val cb = new CheckboxInput()
    assert(cb.render("box", Some("true")) ===
      <input type="checkbox" name="box" checked="checked" />)
  }
}