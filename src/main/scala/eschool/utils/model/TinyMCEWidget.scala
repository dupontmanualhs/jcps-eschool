package eschool.utils.model

import scala.xml._
import net.liftweb._
import http._
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
 * plugins to be loaded seperated by commas. The params parameter should contains parameters in the format
 * ("theme_advanced_buttons2_add", "advimage,save,"). The jQuery parameter determines whether jQuery specific files
 * need be loaded.
 *
 * NOTE: The apply methods and constructors which do not explicitly have a jQuery parameter assume jQuery is in use.
 */
class TinyMCEWidget(theme: String, plugins: String, params: List[(String, String)], jQuery: Boolean, rows: Int, cols: Int) {

  def this(theme: String, plugins: String) = this(theme, plugins, Nil, true, 15, 50)

  def jQueryImport: NodeSeq = <script type="text/javascript" src={"/" + LiftRules.resourceServerPath + "/tinymce/jscripts/tiny_mce/jquery.tinymce.js"} />

  def head: NodeSeq =
    <head>
      <script type="text/javascript" src={"/" + LiftRules.resourceServerPath + "/tinymce/jscripts/tiny_mce/" + (if(jQuery) "tiny_mce_jquery.js" else "tiny_mce.js")} />
        {
          Script(JsRaw("""
                tinyMCE.init({
                        mode: "textareas",
                        theme: """" + theme + """",
                        plugins: """" + plugins + """",
                        """ + params.map((oneTwo: (String, String)) => oneTwo._1 + " : \"" + oneTwo._2 + "\"").mkString(",\n")
                        + """
                        })"""
                        ))
        }
    </head>


  def render: NodeSeq = {
    head ++ <textarea rows={rows.toString} cols={cols.toString} name="content" id="content" class="tinymce" />
  }
}