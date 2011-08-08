package eschool.utils.snippet

import net.liftweb.util._
import Helpers._
import net.liftweb.http.js.JE._
import net.liftweb.http.js.JsCmds._

object TreeNav {
  def render = <head_merge>{
    Script(Call("$", AnonFunc(
      Call("$", "#navTree") ~> JsFunc("jstree", JsObj(
        "themes" ->
          JsObj(
            "theme" -> "classic",
            "dots" -> JsFalse,
            "icons" -> JsFalse,
            "url" -> "/classpath/js/themes/classic/style.css"
        ),
        "core" -> JsObj("animation" -> 250),
        "xml_data" -> JsObj("data" -> treeData.toString()),
        "plugins" -> JsArray("themes", "xml_data")
      ))
    )))
    }</head_merge>

  def treeData = {

    val foo = false

    val defaultData = {
      <root>
        <item id="1" parent_id="0">
          <content><name>Unit 1</name></content>
        </item>

        <item id="1.1" parent_id="1">
          <content><name>Lesson 1</name></content>
        </item>

        <item id="1.2" parent_id="1">
          <content><name>Lesson 2</name></content>
        </item>

        <item id="1.3" parent_id="1">
          <content><name>Lesson 3</name></content>
        </item>

        <item id="1.4" parent_id="1">
          <content><name>Lesson 4</name></content>
        </item>

        <item id="1.5" parent_id="1">
          <content><name>Lesson 5</name></content>
        </item>

        <item id="2" parent_id="0">
          <content><name>Unit 2</name></content>
        </item>

        <item id="3" parent_id="0">
          <content><name>Unit 3</name></content>
        </item>

        <item id="3.1" parent_id="3">
          <content><name>Lesson 1</name></content>
        </item>

        <item id="3.2" parent_id="3">
          <content><name>Lesson 2</name></content>
        </item>

        <item id="3.3" parent_id="3">
          <content><name>Lesson 3</name></content>
        </item>

        <item id="4" parent_id="0">
          <content><name>Unit 4</name></content>
        </item>

        <item id="4.1" parent_id="4">
          <content><name>Lesson 1</name></content>
        </item>

        <item id="4.2" parent_id="4">
          <content><name>Lesson 2</name></content>
        </item>

        <item id="4.3" parent_id="4">
          <content><name>Lesson 3</name></content>
        </item>

        <item id="4.4" parent_id="4">
          <content><name>Lesson 4</name></content>
        </item>

        <item id="4.5" parent_id="4">
          <content><name>Lesson 5</name></content>
        </item>

        <item id="4.6" parent_id="4">
          <content><name>Lesson 6</name></content>
        </item>

        <item id="5" parent_id="0">
          <content><name>Unit 5</name></content>
        </item>

        <item id="6" parent_id="0">
          <content><name>Unit 6</name></content>
        </item>
      </root>
    }

    if(foo == true)
      defaultData
    else
      defaultData

  }
}