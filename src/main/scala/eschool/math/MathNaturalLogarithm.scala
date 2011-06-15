/*
package eschool.math
import scala.math

class MathNaturalLogarithm(anExpression: MathExpression) extends MathLogarithm(math.E, anExpression) {
	override def getName: String = "ln"

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		math.log(getExpression.evaluateUsingTheseVariableValues(variables))
	}

	override def simplify: MathExpression = new MathNaturalLogarithm(getExpression)
}

object MathNaturalLogarithm {
	def apply(anExpression: MathExpression) = new MathNaturalLogarithm(anExpression)
}
*/