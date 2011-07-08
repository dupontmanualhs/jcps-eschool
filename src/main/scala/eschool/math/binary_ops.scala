package eschool.math

abstract class MathBinaryOperation(val leftExpression: MathExpression, val rightExpression: MathExpression) extends MathOperation {
	def getLeftExpression: MathExpression = leftExpression
	def getRightExpression: MathExpression = rightExpression
	override def description: String = this.getClass.toString + "(" + this.getLeftExpression.description + ", " + this.getRightExpression.description + ")"
	override def toLaTeX: String = leftExpressionLaTeX + this.getName + rightExpressionLaTeX
	private def leftExpressionLaTeX: String = {
		if (this.getLeftExpression.getPrecedence < this.getPrecedence) {
			"(" + this.getLeftExpression.toLaTeX + ")"
		} else {
			this.getLeftExpression.toLaTeX
		}
	}
	private def rightExpressionLaTeX: String = {
		if (this.getRightExpression.getPrecedence <= this.getPrecedence) {
			"(" + this.rightExpression.toLaTeX + ")"
		} else {
			this.rightExpression.toLaTeX
		}
	}
}

class MathSum(leftExpression: MathExpression, rightExpression: MathExpression) extends MathBinaryOperation(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathSum(this.getLeftExpression, this.getRightExpression) //TODO
	override def getPrecedence: Int = 0
	override def getName: String = "+"
}

object MathSum {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression): MathSum = new MathSum(leftExpression, rightExpression)
}

class MathDifference(leftExpression: MathExpression, rightExpression: MathExpression) extends MathSum(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathDifference(this.getLeftExpression, this.getRightExpression) //TODO
	override def getName: String = "-"
}

object MathDifference {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression) = new MathDifference(leftExpression, rightExpression)
}

class MathProduct(leftExpression: MathExpression, rightExpression: MathExpression) extends MathBinaryOperation(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathProduct(this.getLeftExpression, this.getRightExpression) //TODO
	override def getPrecedence: Int = 1
	override def getName: String = "*"
}

object MathProduct {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression): MathProduct = new MathProduct(leftExpression, rightExpression)
}

class MathQuotient(leftExpression: MathExpression, rightExpression: MathExpression) extends MathProduct(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathQuotient(this.getLeftExpression, this.getRightExpression) //TODO
	override def getName: String = "/"
}

object MathQuotient {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression): MathQuotient = new MathQuotient(leftExpression, rightExpression)
}

class MathExponentiation(expression: MathExpression, exponent: MathExpression) extends MathBinaryOperation(expression, exponent) {
	def getExpression = super.getLeftExpression
	def getExponent = super.getRightExpression

	override def simplify: MathExpression = new MathExponentiation(this.getExpression, this.getExponent)
	override def getPrecedence: Int = 3
	override def getName: String = "^"
}

object MathExponentiation {
	def apply(expression: MathExpression, exponent: MathExpression): MathExponentiation = new MathExponentiation(expression, exponent)
}