package eschool.math

abstract class MathUnaryOperation(val expression: MathExpression) extends MathOperation {
	def getExpression: MathExpression = expression
	override def getPrecedence: Int = 2
	override def toLaTeX: String = {
		if (this.getExpression.getPrecedence < this.getPrecedence) {
			this.getOperator + " (" + this.getExpression.toLaTeX + ")"
		} else {
			this.getOperator + " " + this.getExpression.toLaTeX
		}
	}
	override def description: String = this.getDescString + "(" + this.getExpression.description + ")"

	override def equals(that: Any): Boolean = {
		that match {
			case that: MathUnaryOperation => this.equals(that)
			case _ => false
		}
	}

	private def equals(that: MathUnaryOperation): Boolean = {
		this.getClass == that.getClass && that.getExpression == this.getExpression
	}
}

class MathNegation(expression: MathExpression) extends MathUnaryOperation(expression) {
	override def getOperator: String = "-"
	override def getDescString: String = "MathNegation"
	override def simplify: MathExpression = new MathNegation(this.getExpression)
	override def toLaTeX: String = {
		if (this.getExpression.getPrecedence < this.getPrecedence || isNegative(this.getExpression)) {
			this.getOperator + "(" + this.getExpression.toLaTeX + ")"
		} else {
			this.getOperator + "" + this.getExpression.toLaTeX
		}
	}
	private def isNegative(expr: MathExpression): Boolean = {
		expr match {
			case neg: MathNegation => true
			case real: MathRealNumber if (real.getValue < 0) => true
			case _ => false
		}
	}
}

object MathNegation {
	def apply(expression: MathExpression) = new MathNegation(expression)
}

