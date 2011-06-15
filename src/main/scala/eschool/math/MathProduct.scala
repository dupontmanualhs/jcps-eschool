package eschool.math

class MathProduct(aLeftExpression: MathExpression, aRightExpression: MathExpression) extends MathBinaryOperation(aLeftExpression, aRightExpression) {
	def getName: String = "*"

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		val leftExpressionEvaluated = getLeftExpression.evaluateUsingTheseVariableValues(variables)
		val rightExpressionEvaluated = getRightExpression.evaluateUsingTheseVariableValues(variables)
		return leftExpressionEvaluated * rightExpressionEvaluated
	}

	override def getPrecedence: Int = 1

	override def simplify: MathExpression = {
		new MathProduct(getLeftExpression, getRightExpression)
	}
}

object MathProduct {
	def apply(aLeftExpression: MathExpression, aRightExpression: MathExpression) = new MathProduct(aLeftExpression, aRightExpression)
}