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
	def apply(s: String): Option[MathExpression] = {
		if (!parenthesesAlignIn(s)) {
			None
		} else {
			val result: Option[MathExpression] = MathNegation(s) orElse MathValue(s) orElse MathOperation(s) orElse MathTerm(s) orElse MathPolynomial(s)
			if (!result.isDefined && hasOutsideParens(s)) {
				MathExpression(removeTrivialParts(s))
			} else {
				result
			}
		}
	}
	private def removeTrivialParts(s: String): String = {
		removeFirstPlusIn(removeSpacesIn(removeOutsideParensIn(s)))
	}

	private def hasOutsideParens(s: String): Boolean = {
		s.startsWith("(") && s.endsWith(")") ||
		s.startsWith("{") && s.endsWith("}") ||
		s.startsWith("[") && s.endsWith("]")
	}

	def parenthesesAlignIn(s: String): Boolean = {
		s.count(_ == '(') == s.count(_ == ')') &&
		s.count(_ == '{') == s.count(_ == '}') &&
		s.count(_ == '[') == s.count(_ == ']')
	}
	def removeOutsideParensIn(s: String): String = {
		if (hasOutsideParens(s)) {
			s.substring(1, s.length() - 1)
		} else {
			s
		}
	}
	private def removeSpacesIn(s: String): String = {
		""" """.r.replaceAllIn(s, "")
	}
	private def removeFirstPlusIn(s: String): String = {
		"""^\+""".r.replaceFirstIn(s, "")
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
