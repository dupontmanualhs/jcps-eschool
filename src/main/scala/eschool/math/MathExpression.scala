package eschool.math

trait MathExpression {
	def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double
	def containsAVariable: Boolean
	def simplify: MathExpression
	def toSmartString: String
	def getPrecedence: Int
}