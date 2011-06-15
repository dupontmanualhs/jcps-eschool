package eschool.math

class MathDifference(aLeftExpression: MathExpression, aRightExpression: MathExpression) extends MathBinaryOperation(aLeftExpression, aRightExpression) {
	def getName: String = "-"

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		val leftExpressionEvaluated = getLeftExpression.evaluateUsingTheseVariableValues(variables)
		val rightExpressionEvaluated = getRightExpression.evaluateUsingTheseVariableValues(variables)
		return leftExpressionEvaluated - rightExpressionEvaluated
	}

	override def getPrecedence: Int = 0

	override def simplify: MathExpression = {
		new MathDifference(getLeftExpression, getRightExpression)
	}
}

object MathDifference {
	def apply(aLeftExpression: MathExpression, aRightExpression: MathExpression) = new MathDifference(aLeftExpression, aRightExpression)
	def random(): MathDifference = MathDifference(MathNumber.random(), MathNumber.random())
}