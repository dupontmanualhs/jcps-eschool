package eschool.math

//MathExpression subclasses: MathOperation & MathValue (see below),
//                                         MathTerm & MathVariablePowered (not yet implemented)
trait MathExpression {
	def simplify: MathExpression
	def getPrecedence: Int
	def toLaTeX: String
	def description: String
	override def toString = this.toLaTeX
	def +(operand: MathExpression): MathOperation = MathSum(this, operand)
	def -(operand: MathExpression): MathOperation = MathDifference(this, operand)
	def *(operand: MathExpression): MathOperation = MathProduct(this, operand)
	def /(operand: MathExpression): MathOperation = MathQuotient(this, operand)
}

object MathExpression {
	def apply(s: String): Option[MathExpression] = {
		MathTerm(s) orElse MathPolynomial(s) orElse MathNumber(s) orElse MathTerm(s)
	}
}

//MathOperation subclasses: MathUnaryOperation (unary_ops.scala),
//                                        MathBinaryOperation (binary_ops.scala)
abstract class MathOperation extends MathExpression {
	def getOperator: String
	def getDescString: String
}

//MathValue subclasses: MathConstant (constants.scala)
//                                 MathVariable (vars.scala)
abstract class MathValue extends MathExpression {
	override def simplify: MathExpression = this
	override def getPrecedence: Int = 6
}

object MathValue {
	def apply(s: String): Option[MathValue] = {
		MathConstant(s) orElse MathVariable(s)
	}

	def apply(c: Char): Option[MathVariable] = {
		MathVariable(c)
	}
}

trait Operationable {
	def toMathOperation: MathOperation
}
