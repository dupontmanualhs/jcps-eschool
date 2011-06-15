package eschool.math

abstract class MathUnaryOperation(anExpression: MathExpression) extends MathExpression {
	private val expression = anExpression
	def getExpression = expression

	def getName: String;
	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double;
	override def simplify: MathExpression;

	override def containsAVariable = expression.containsAVariable

	override def getPrecedence: Int = 2

	override def toSmartString: String = {
		if (this.getExpression.getPrecedence < this.getPrecedence) {
			getName + "(" + this.getExpression.toSmartString + ")"
		} else {
			getName + " " + this.getExpression.toSmartString
		}
	}

	override def toString: String = {
		getName + "(" + this.expression.toSmartString + ")"
	}
}