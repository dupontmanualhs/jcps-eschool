package eschool.math

class MathSum(aLeftExpression: MathExpression, aRightExpression: MathExpression) extends MathBinaryOperation(aLeftExpression, aRightExpression) {
	def getName: String = "+"

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		val leftExpressionEvaluated = getLeftExpression.evaluateUsingTheseVariableValues(variables)
		val rightExpressionEvaluated = getRightExpression.evaluateUsingTheseVariableValues(variables)
		return leftExpressionEvaluated + rightExpressionEvaluated
	}

	override def getPrecedence: Int = 0

	override def simplify: MathExpression = {
		new MathSum(getLeftExpression, getRightExpression)
	}
}

object MathSum {
	def apply(aLeftExpression: MathExpression, aRightExpression: MathExpression) = new MathSum(aLeftExpression, aRightExpression)
	def random(): MathSum = MathSum(MathNumber.random(), MathNumber.random())
}