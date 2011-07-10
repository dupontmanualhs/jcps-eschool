package eschool.math

abstract class MathBinaryOperation(val leftExpression: MathExpression, val rightExpression: MathExpression) extends MathOperation {
	def getLeftExpression: MathExpression = leftExpression
	def getRightExpression: MathExpression = rightExpression
	override def description: String = this.getDescString + "(" + this.getLeftExpression.description + ", " + this.getRightExpression.description + ")"
	override def toLaTeX: String = this.leftExpressionLaTeX + this.getOperator + this.rightExpressionLaTeX
	def leftExpressionLaTeX = expressionLaTeX(this.getLeftExpression)
	def rightExpressionLaTeX = expressionLaTeX(this.getRightExpression)
	private def expressionLaTeX(expression: MathExpression): String = {
		if (expression.getPrecedence < this.getPrecedence || isNegative(expression)) {
			"(" + expression.toLaTeX + ")"
		} else {
			expression.toLaTeX
		}
	}

	def isNegative(expression: MathExpression): Boolean = {
		expression.isInstanceOf[MathConstant] && expression.asInstanceOf[MathConstant].getValue < 0
	}

	override def equals(that: Any): Boolean = {
		that match {
			case that: MathBinaryOperation => this.equals(that)
			case _ => false
		}
	}
	
	private def equals(that: MathBinaryOperation): Boolean = {
		this.getClass == that.getClass &&
		that.getLeftExpression == this.getLeftExpression &&
		that.getRightExpression == this.getRightExpression
	}
}

class MathSum(leftExpression: MathExpression, rightExpression: MathExpression) extends MathBinaryOperation(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathSum(this.getLeftExpression, this.getRightExpression) //TODO
	override def getPrecedence: Int = 0
	override def getOperator: String = "+"
	override def getDescString: String = "MathSum"
}

object MathSum {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression): MathSum = new MathSum(leftExpression, rightExpression)
}

class MathDifference(leftExpression: MathExpression, rightExpression: MathExpression) extends MathBinaryOperation(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathDifference(this.getLeftExpression, this.getRightExpression) //TODO
	override def getPrecedence: Int = 1
	override def getOperator: String = "-"
	override def getDescString: String = "MathDifference"
}

object MathDifference {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression) = new MathDifference(leftExpression, rightExpression)
}

class MathProduct(leftExpression: MathExpression, rightExpression: MathExpression) extends MathBinaryOperation(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathProduct(this.getLeftExpression, this.getRightExpression) //TODO
	override def getPrecedence: Int = 2
	override def getDescString: String = "MathProduct"
	override def getOperator: String = "*"
}

object MathProduct {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression): MathProduct = new MathProduct(leftExpression, rightExpression)
}

class MathQuotient(leftExpression: MathExpression, rightExpression: MathExpression) extends MathBinaryOperation(leftExpression, rightExpression) {
	override def simplify: MathExpression = new MathQuotient(this.getLeftExpression, this.getRightExpression) //TODO
	override def getPrecedence: Int = 3
	override def getOperator: String = "/"
	override def getDescString: String = "MathQuotient"
}

object MathQuotient {
	def apply(leftExpression: MathExpression, rightExpression: MathExpression): MathQuotient = new MathQuotient(leftExpression, rightExpression)
}

class MathExponentiation(expression: MathExpression, exponent: MathExpression) extends MathBinaryOperation(expression, exponent) {
	def getExpression = super.getLeftExpression
	def getExponent = super.getRightExpression

	override def simplify: MathExpression = new MathExponentiation(this.getExpression, this.getExponent)
	override def getPrecedence: Int = 5
	override def getOperator: String = "^"
	override def getDescString: String = "MathExponentiation"
	override def toLaTeX: String = super.leftExpressionLaTeX + this.getOperator + "{" + this.getExponent.toLaTeX + "}"
}

object MathExponentiation {
	def apply(expression: MathExpression, exponent: MathExpression): MathExponentiation = new MathExponentiation(expression, exponent)
}

class MathLogarithm(val base: MathExpression, expression: MathExpression) extends MathBinaryOperation(base, expression) {
	def getBase: MathExpression = super.getLeftExpression
	def getExpression: MathExpression = super.getRightExpression
	override def getPrecedence: Int = 4
	override def toLaTeX: String = this.getOperator + " " + this.rightExpressionLaTeX
	override def getOperator: String = "\\log_" + this.getBase.toLaTeX
	override def getDescString: String = "MathLogarithm"
	override def simplify: MathExpression = new MathLogarithm(this.getBase, this.getExpression)
	override def description: String = "MathLogarithm(Base: %s, Expression: %s)".format(this.getBase.description, this.getExpression.description)
}

object MathLogarithm {
	def apply(base: MathConstant, expression: MathExpression): MathLogarithm = new MathLogarithm(base, expression)
}

class MathNaturalLogarithm(expression: MathExpression) extends MathLogarithm(new MathConstantE, expression) {
	override def getOperator: String = "\\ln"
	override def description: String = "MathNaturalLogarithm(%s)".format(this.getExpression.description)
}

object MathNaturalLogarithm {
	def apply(expression: MathExpression): MathNaturalLogarithm = new MathNaturalLogarithm(expression)
}

class MathBase10Logarithm(expression: MathExpression) extends MathLogarithm(MathInteger(10), expression) {
	override def getOperator: String = "\\log"
	override def description: String = "MathBase10Logarithm(%s)".format(this.getExpression.description)
}

object MathBase10Logarithm {
	def apply(expression: MathExpression) = new MathBase10Logarithm(expression)
}

class MathRoot(val index: MathExpression, val radicand: MathExpression) extends MathExponentiation(radicand, MathQuotient(MathInteger(1), index)) {
	def getIndex: MathExpression = index
	def getRadicand: MathExpression = radicand
	override def getLeftExpression = getRadicand
	override def getRightExpression = getIndex

	override def toLaTeX = "\\sqrt[%s]{%s}".format(getIndex, getRadicand)
	override def getDescString: String = "MathRoot"
	override def description: String = this.getDescString + "(Index: %s, Radicand: %s)".format(this.getIndex.description, this.getRadicand.description)
}

object MathRoot {
	def apply(index: MathExpression, radicand: MathExpression) = new MathRoot(index, radicand)
}

class MathSquareRoot(radicand: MathExpression) extends MathRoot(MathInteger(2), radicand) {
	override def toLaTeX: String = "\\sqrt{%s}".format(super.getRadicand)
	override def getDescString: String = "MathSquareRoot"
	override def description: String = this.getDescString + "(" + this.getRadicand.description + ")"
}

object MathSquareRoot {
	def apply(radicand: MathExpression) = new MathSquareRoot(radicand)
}

class MathCubeRoot(radicand: MathExpression) extends MathRoot(MathInteger(3), radicand) {
	override def toLaTeX: String = "\\sqrt[3]{%s}".format(super.getRadicand)
	override def getDescString: String = "MathCubeRoot"
	override def description: String = this.getDescString + "(" + this.getRadicand.description + ")"
}

object MathCubeRoot {
	def apply(radicand: MathExpression) = new MathCubeRoot(radicand)
}