package eschool.utils.model

/*import scala.xml._
import net.liftweb._
import http._
import json.JsonAST.JObject
import util._
import js._
import JsCmds._
import JE._


object TinyMCEWidget {

  def apply(): NodeSeq = new TinyMCEWidget("advanced", "") render
  def apply(jQuery: Boolean): NodeSeq = new TinyMCEWidget("advanced", "", Nil, jQuery, 15, 50) render
  def apply(rows: Int, cols: Int): NodeSeq = new TinyMCEWidget("advanced", "", Nil, true, rows, cols) render
  def apply(plugins: String, params: List[(String, String)]): NodeSeq = new TinyMCEWidget("advanced", plugins, params, true, 15, 50) render
  def apply(rows: Int, cols: Int, jQuery: Boolean): NodeSeq = new TinyMCEWidget("advanced", "", Nil, jQuery, rows, cols) render
  def apply(plugins: String, params: List[(String, String)], jQuery: Boolean): NodeSeq = new TinyMCEWidget("advanced", plugins, params, jQuery, 15, 50) render

  def init() {
    import net.liftweb.http.ResourceServer

    ResourceServer.allow({
      case "tinymce" :: _ => true
    })
  }
}

/**
 * Builds a TinyMCE widget based on the specified theme, list of buttons, and use of jQuery.
 *
 * Using this widget involves calling TinyMCEWidget.init in the Boot.scala file and creating a snippet
 * which makes use of the TinyMCEWidget to create an instance of TinyMCE.
 *
 * The theme parameter will hold the name of a theme, The plugins parameter should be a string containing the
 * plugins to be loaded separated by commas. The params parameter should contains parameters in the format
 * ("theme_advanced_buttons2_add", "advimage,save,"). The jQuery parameter determines whether jQuery specific files
 * need be loaded.
 *
 * NOTE: The apply methods and constructors which do not explicitly have a jQuery parameter assume jQuery is in use.
 */
class TinyMCEWidget(
    config: JsObj = JsObj("mode" -> "specific_textareas",
                          "editor_selector" -> "mceEditor",
                          "theme" -> "simple"),
    rows: Int = 20,
    cols: Int = 80) {

  def head: NodeSeq =
    <head_merge>
      <script type="text/javascript" src={ "/%s/tinymce/jscripts/tiny_mce/jquery.tinymce.js".format(LiftRules.resourceServerPath) } />
      <script type="text/javascript" src={ "/%s/tinymce/jscripts/tiny_mce/tiny_mce_jquery.js".format(LiftRules.resourceServerPath) } />
      { Script(Call("tinyMCE.init", config)) }
    </head_merge>


  def render: NodeSeq = {
    head ++ SHtml.textarea
  }
}
*/