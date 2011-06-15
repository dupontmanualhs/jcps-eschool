package eschool.math

abstract class MathBinaryOperation(aLeftExpression: MathExpression, aRightExpression: MathExpression) extends MathExpression {
	private val leftExpression = aLeftExpression
	private val rightExpression = aRightExpression

 	def getName: String;
	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double;
	override def simplify: MathExpression;
	override def getPrecedence: Int;

	override def containsAVariable = leftExpression.containsAVariable || rightExpression.containsAVariable

	def getLeftExpression = leftExpression

	def getRightExpression = rightExpression

	override def toSmartString: String = {
		this.leftExpressionSmartString + " " + getName + " " + this.rightExpressionSmartString
	}

	private def leftExpressionSmartString: String = {
		if (this.getLeftExpression.getPrecedence < this.getPrecedence) {
			"(" + this.getLeftExpression.toSmartString + ")"
		} else {
			this.getLeftExpression.toSmartString
		}
	}

	private def rightExpressionSmartString: String = {
		if (this.getRightExpression.getPrecedence <= this.getPrecedence) {
			"(" + this.getRightExpression.toSmartString + ")"
		} else {
			this.getRightExpression.toSmartString
		}
	}

	override def toString: String = {
		"(" + this.getLeftExpression.toString + " " + this.getName + " " + this.getRightExpression.toString + ")"
	}
}
