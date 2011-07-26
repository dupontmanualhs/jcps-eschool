package eschool.math

//MathExpression subclasses: MathOperation & MathValue (see below),
//                                         MathTerm
trait MathExpression {
	def simplify: MathExpression
	def getPrecedence: Int
	def toLaTeX: String
	def description: String
	override def toString = this.toLaTeX
	def +(operand: MathExpression): MathOperation = MathSum(List[MathExpression](this, operand))
	def -(operand: MathExpression): MathOperation = MathDifference(List[MathExpression](this, operand))
	def *(operand: MathExpression): MathOperation = MathProduct(List[MathExpression](this, operand))
	def /(operand: MathExpression): MathOperation = MathQuotient(List[MathExpression](this, operand))
	def isNegative: Boolean = {
		this match {
			case constant: MathConstant => constant != null && constant.getValue < 0
			case term: MathTerm => term.getCoefficient != null && term.getCoefficient.getValue < 0
			case neg: MathNegation => true
			case _ => false
		}
	}
}

object MathExpression {
	def apply(str: String): Option[MathExpression] = {
		if (parenthesesAlignIn(str)) {
			val s = removeTrivialParts(str)
			MathOperation(s) orElse MathValue(s) orElse MathTerm(s) orElse MathPolynomial(s)
		} else {
			None
		}

	}
	private def removeTrivialParts(s: String): String = {
	    removeSpacesIn(removeOutsideParensIn(s))
	}
	private def parenthesesAlignIn(s: String): Boolean = {
		s.count(_ == ')') == s.count(_ == '(')
	}
	private def removeOutsideParensIn(s: String): String = {
		if (s.startsWith("(") && s.endsWith(")")) {
			removeOutsideParensIn(s.substring(1, s.length() - 1))
		} else if (s.startsWith("{") && s.endsWith("}")) {
			removeOutsideParensIn(s.substring(1, s.length() - 1))
		} else {
			s
		}
	}
	private def removeSpacesIn(s: String): String = {
		""" """.r.replaceAllIn(s, "")
	}
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
