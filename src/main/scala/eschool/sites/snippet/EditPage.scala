package eschool.sites.snippet

import eschool.users.model.User
import eschool.sites.model.{Page, Site}
import net.liftweb.util.FieldError
import com.foursquare.rogue.Rogue._
import net.liftweb.common.{Box, Full}
import net.liftweb.http.{LiftRules, SHtml, LiftScreen, S}
import xml._
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsCmds._


trait MyLiftScreen extends LiftScreen {
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
  protected def mceTextarea(name: => String, defaultValue: => String, stuff: FilterOrValidate[String]*): Field {type ValueType = String} =
    mceTextarea(name, defaultValue, 10, 80, stuff: _*)


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
  protected def mceTextarea(name: => String, defaultValue: => String, rows: Int, cols: Int, stuff: FilterOrValidate[String]*):
  Field {type ValueType = String} = {
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
        <script type="text/javascript">{ Unparsed(
        """$(function() {
           $('textarea.mceEditor').tinymce({
              theme : "advanced",
              theme_advanced_toolbar_location : "top",
              theme_advanced_toolbar_align : "left"
            });
        })""" ) }
        </script>
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

}

class EditPage(userSiteAndPage: (User, Site, Page)) extends MyLiftScreen {
  object currentUser extends ScreenVar[User](User.getCurrentOrRedirect())
  val (user: User, site: Site, page: Page) = userSiteAndPage
  var contentNodeSeq: NodeSeq = page.content.get
  if (currentUser.id.get != user.id.get) {
    S.error("You do not have permission to edit that page.")
    S.redirectTo(S.referer openOr "/index")
  }

  val name = text("Page Name", page.name.get,
      valMinLen(1, "The page name must be at least one character."),
      valMaxLen(80, "The page name must be 80 characters or fewer."),
      uniqueName _)
  val content = mceTextarea("Content", page.content.get.toString, 30, 80)

  def finish() {
    page.name(name.get).content(XML.loadString("<dummy>" + content.get + "</dummy>").child).save(true)
  }


  def uniqueName(s: String): List[FieldError] = {
    if (s != page.name.get) {
      page.parentPage.valueBox match {
        case Full(oid) => {
          Page where (_.parentPage eqs oid) and (_.name eqs s) fetch() match {
            case Nil => Nil
            case _ => "You already have a page with that name."
          }
        }
        case _ => page.parentSite.valueBox match {
          case Full(oid) => {
            Page where (_.parentSite eqs oid) and (_.name eqs s) fetch() match {
              case Nil => Nil
              case _ => "You already have a page with that name."
            }
          }
          case _ => throw new Exception("We have a page without a parent. That shouldn't happen.")
        }
      }
    } else {
      Nil
    }
  }
}