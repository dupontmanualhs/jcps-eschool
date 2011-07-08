package eschool.math

abstract class MathUnaryOperation(val expression: MathExpression) extends MathOperation {
	def getExpression: MathExpression = expression
	override def getPrecedence: Int = 2
	override def toLaTeX: String = {
		if (this.getExpression.getPrecedence < this.getPrecedence) {
			this.getName + "(" + this.getExpression.toLaTeX + ")"
		} else {
			this.getName + " " + this.getExpression.toLaTeX
		}
	}
	override def description: String = this.getClass.toString + "(" + this.getExpression.description + ")"
}

class MathLogarithm(val base: MathConstant, expression: MathExpression) extends MathUnaryOperation(expression) {
	def getBase: MathConstant = base
	override def getName: String = "log_" + this.getBase.toLaTeX
	override def simplify: MathExpression = new MathLogarithm(this.getBase, this.getExpression)
	override def description: String = "" //TODO
}

object MathLogarithm {
	def apply(base: MathConstant, expression: MathExpression): MathLogarithm = new MathLogarithm(base, expression)
}

class MathNaturalLogarithm(expression: MathExpression) extends MathLogarithm(new MathConstantE, expression) {
	override def getName: String = "ln" //TODO
	override def description: String = "" //TODO
}

object MathNaturalLogarithm {
	def apply(expression: MathExpression): MathNaturalLogarithm = new MathNaturalLogarithm(expression)
}