package eschool.assignments.questions

import scala.xml.NodeSeq
import scala.xml.Node
import scala.xml.Elem
import scala.collection.GenTraversableOnce
import scala.xml.Attribute
import scala.xml.Text
import scala.xml.Null

class MultChoiceAnswer(val text: NodeSeq, val isCorrect: Boolean) {
  def toXml: NodeSeq = {
    <answer>{ text }</answer> % (if (isCorrect) Attribute(None, "correct", Text("true"), Null) else Null)
  }
  
  def asFormRow(guid: String, index: Int, displayCorrect: Boolean): NodeSeq = {
    <tr>
      <td>{ <input type="radio" name={ guid } value={ index.toString } /> % 
      	(if (displayCorrect && isCorrect) Attribute(None, "checked", Text("true"), Null) else Null) }
      </td>
      <td>{ text }</td>
    </tr>
  }
  
}


class MultChoice(
    val text: NodeSeq,
    val answers: List[MultChoiceAnswer],
    val explanation: NodeSeq,
    val guid: String) {
 
  def toXml: NodeSeq = {
    <question type="MultChoice">
      <text>{ text }</text>
      <answers>
      { answers.flatMap(_.toXml) }
      </answers>
      <explanation>{ explanation }</explanation>
    </question>
  }
  
  def toHtml(displayCorrect: Boolean): NodeSeq = {
    <div class="question">
      { text }
      <table>
      { answers.zipWithIndex.flatMap((a: MultChoiceAnswer, i: Int) => a.asFormRow(guid, i, displayCorrect)) }
      </table>
      { if (displayCorrect) explanation else NodeSeq.Empty }
    </div>
  }
}

object MultChoice {
  def blank(numAnswers: Int, guid: String): MultChoice = {
    new MultChoice(NodeSeq.Empty, List.fill(numAnswers)(new MultChoiceAnswer(NodeSeq.Empty, false)), NodeSeq.Empty, guid)
  }
}
