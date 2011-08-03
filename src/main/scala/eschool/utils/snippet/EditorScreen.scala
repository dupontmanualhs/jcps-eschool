package eschool.utils.snippet

import net.liftweb.http.{SHtml, LiftScreen}
import net.liftweb.util.FieldError
import net.liftweb.common._
import xml.{XML, Elem, NodeSeq, Unparsed}
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsCmds._
import com.sun.corba.se.spi.presentation.rmi.PresentationManager.StubFactoryFactory
import java.lang.reflect.Field

trait EditorScreen extends LiftScreen {

  /**
   * Create an mceTextarea Field with 80 columns and 10 rows
   *
   * @param name the name of the field (call-by-name)
   * @param defaultValue the starting value of the field (call-by-name)
   *
   * @param stuff - a list of filters and validations for the field
   *
   * @returns a newly minted Field{type ValueType = NodeSeq}
   */
  protected def mceTextarea(name: => String, defaultValue: => String,
                            stuff: FilterOrValidate[String]*): Field {type ValueType = String} =
      mceTextarea(name, defaultValue, 20, 80, stuff: _*)


  /**
   * Create an mceTextarea Field
   *
   * @param name the name of the field (call-by-name)
   * @param defaultValue the starting value of the field (call-by-name)
   * @param rows the number of rows in the textarea
   * @param cols the number of columns in the textarea
   *
   * @param stuff - a list of filters and validations for the field
   *
   * @returns a newly minted Field{type ValueType = String}
   */
  protected def mceTextarea(name: => String, defaultValue: => String, rows: Int, cols: Int,
      stuff: FilterOrValidate[String]*): Field {type ValueType = String} = {
    val eAttr: List[SHtml.ElemAttr] = (("rows" -> rows.toString): SHtml.ElemAttr) ::
        (("cols" -> cols.toString): SHtml.ElemAttr) ::
        (("class" -> "mceEditor"): SHtml.ElemAttr) :: grabParams(stuff)

    val validators: List[FilterOrValidate[String]] = validHtml _ :: stuff.toList

    makeField[String, Nothing](name,
      defaultValue,
      field => Full(NodeSeq.fromSeq(Seq(
        <head_merge>
        <script type="text/javascript" src="/classpath/tinymce/jscripts/tiny_mce/jquery.tinymce.js"></script>
        <script type="text/javascript" src="/classpath/tinymce/jscripts/tiny_mce/tiny_mce.js"></script>
        { Script(Call("$", AnonFunc(
            Call("$", "textarea.mceEditor") ~> JsFunc("tinymce",
              JsObj("theme" -> "advanced",
                    "theme_advanced_toolbar_location" -> "top",
                    "theme_advanced_toolbar_align" -> "left",
                    "plugins" -> "mathematik",
                    "theme_advanced_buttons2_add" -> "mathpopup"))))) }
        </head_merge>,
        SHtml.textarea(field.is.toString,
            field.set(_),
            eAttr: _*)))),
      NothingOtherValueInitializer,
      validators: _*)
  }

  def validHtml(s: String): List[FieldError] = {
    try {
      val el: Elem = XML.loadString("<dummy>" + s + "</dummy>")
      Nil
    } catch {
      case e: Exception => "Content must be valid (X)HTML."
    }
  }

  implicit def funcBoxString2funcListFieldError[T](f: T => Box[String]): T => List[FieldError] = {
    (x: T) => boxStrToListFieldError(f(x))
  }
}
