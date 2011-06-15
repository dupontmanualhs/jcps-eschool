package eschool.math

class MathQuotient(aLeftExpression: MathExpression, aRightExpression: MathExpression) extends MathBinaryOperation(aLeftExpression, aRightExpression) {
	def getName: String = "/"

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		val leftExpressionEvaluated = getLeftExpression.evaluateUsingTheseVariableValues(variables)
		val rightExpressionEvaluated = getRightExpression.evaluateUsingTheseVariableValues(variables)
		if (rightExpressionEvaluated != 0) {
			return leftExpressionEvaluated / rightExpressionEvaluated
		} else {
			throw new ArithmeticException("cannot divide by zero")
		}
	}

	override def getPrecedence: Int = 1

	override def simplify: MathExpression = {
		new MathQuotient(getLeftExpression, getRightExpression)
	}
}

object MathQuotient {
	def apply(aLeftExpression: MathExpression, aRightExpression: MathExpression) = new MathQuotient(aLeftExpression, aRightExpression)
}