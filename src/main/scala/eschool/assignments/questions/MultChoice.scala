package eschool.assignments.questions

import scala.xml.NodeSeq
import scala.xml.Attribute
import scala.xml.Text
import scala.xml.Null

class MultChoiceAnswer(val text: NodeSeq, val isCorrect: Boolean) {
  def this() = {
    this(NodeSeq.Empty, false)
  }
  
  def toXml: NodeSeq = {
    <answer>{ text }</answer> % (if (isCorrect) Attribute(None, "correct", Text("true"), Null) else Null)
  }
  
  def asFormRow(guid: String, index: Int, displayCorrect: Boolean): NodeSeq = {
    <tr>
    <td>
    { <input name={ guid } value={ index.toString } /> % 
      (if (displayCorrect && isCorrect) Attribute(None, "checked", Text("true"), Null) else Null) }
    </td>
    <td>{ text }</td>
    </tr>
  }
  
  def asEditRow(guid: String, index: Int): NodeSeq = {
    <tr><td></td></tr>
  }
}

class MultChoice(
    val text: NodeSeq,
    val answers: List[MultChoiceAnswer],
    val explanation: NodeSeq) {
  
  def toXml: NodeSeq = {
    <question type="MultChoice">
      <text>{ text }</text>
      <answers>{ answers.flatMap(_.toXml) }</answers>
      <explanation>{ explanation }</explanation>
    </question>
  }
  
  def asForm(guid: String, displayCorrect: Boolean): NodeSeq = {
    <div class="question">
    { text }
    <table>
    { answers.zipWithIndex.flatMap { case (ans, index) => ans.asFormRow(guid, index, displayCorrect) } }
    </table>
    { if (displayCorrect) explanation else NodeSeq.Empty }
    </div>
  }
  
  def toEdit(guid: String) {
    <table>
    <tr><td><label for={ guid + "text" }>Text</label></td><td><textarea name={ guid + "text" }>{ text }</textarea></td></tr>
    { answers.zipWithIndex.flatMap { case (ans, index) => ans.asEditRow(guid, index) } }
    <tr><td><label for={ guid + "expl" }>Explanation</label></td>
        <td><textarea name={ guid + "expl" }>{ explanation }</textarea></td></tr>
    </table>
  }
}

object MultChoice {
  def blank(numAnswers: Int): MultChoice = {
    new MultChoice(NodeSeq.Empty, List.fill(numAnswers)(new MultChoiceAnswer()), NodeSeq.Empty)
  }
}