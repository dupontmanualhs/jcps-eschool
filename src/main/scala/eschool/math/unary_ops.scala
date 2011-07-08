package eschool.math

abstract class MathUnaryOperation(val expression: MathExpression) extends MathOperation {
	def getExpression: MathExpression = expression
	override def getPrecedence: Int = 2
	override def toLaTeX: String = {
		if (this.getExpression.getPrecedence < this.getPrecedence) {
			this.getName + " (" + this.getExpression.toLaTeX + ")"
		} else {
			this.getName + " " + this.getExpression.toLaTeX
		}
	}
	override def description: String = this.getClass.toString + "(" + this.getExpression.description + ")"
}

class MathLogarithm(val base: MathConstant, expression: MathExpression) extends MathUnaryOperation(expression) {
	def getBase: MathConstant = base
	override def getName: String = "\\log_" + this.getBase.toLaTeX
	override def simplify: MathExpression = new MathLogarithm(this.getBase, this.getExpression)
	override def description: String = "MathLogarithm(Base: %s, Expression: %s)".format(this.getBase.description, this.getExpression.description)
}

object MathLogarithm {
	def apply(base: MathConstant, expression: MathExpression): MathLogarithm = new MathLogarithm(base, expression)
}

class MathNaturalLogarithm(expression: MathExpression) extends MathLogarithm(new MathConstantE, expression) {
	override def getName: String = "\\ln"
	override def description: String = "MathNaturalLogarithm(%s)".format(this.getExpression.description)
}

object MathNaturalLogarithm {
	def apply(expression: MathExpression): MathNaturalLogarithm = new MathNaturalLogarithm(expression)
}

class MathBase10Logarithm(expression: MathExpression) extends MathLogarithm(MathInteger(10), expression) {
	override def getName: String = "\\log"
	override def description: String = "MathBase10Logarithm(%s)".format(this.getExpression.description)
}

object MathBase10Logarithm {
	def apply(expression: MathExpression) = new MathBase10Logarithm(expression)
}