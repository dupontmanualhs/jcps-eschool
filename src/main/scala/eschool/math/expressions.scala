package eschool.math

//MathExpression subclasses: MathOperation & MathValue (see below),
//                                         MathTerm & MathVariablePowered (not yet implemented)
trait MathExpression {
	def simplify: MathExpression
	def getPrecedence: Int
	def toLaTeX: String
	def description: String
	override def toString = this.toLaTeX
}

object MathExpression {
	def apply(s: String): Option[MathExpression] = {
		MathNumber(s) orElse MathTerm(s)
	}
}

//MathOperation subclasses: MathUnaryOperation (unary_ops.scala),
//                                        MathBinaryOperation (binary_ops.scala)
abstract class MathOperation extends MathExpression {
	def getName: String
}

//MathValue subclasses: MathConstant (constants.scala)
//                                 MathVariable (vars.scala)
abstract class MathValue extends MathExpression {
	override def simplify: MathExpression = this
	override def getPrecedence: Int = 4
}

object MathValue {
	def apply(s: String): Option[MathValue] = {
		MathConstant(s) orElse MathVariable(s)
	}

	def apply(c: Char): Option[MathVariable] = {
		MathVariable(c)
	}
}
