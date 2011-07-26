package eschool.math

class MathPolynomial(terms: List[MathTerm]) extends MathExpression {
	def getTerms: List[MathTerm] = terms
	def simplify: MathExpression = new MathPolynomial(this.getTerms)
	def getPrecedence: Int = 1
	def toMathOperation: MathOperation = {
		val termOperations: List[MathOperation] = this.getTerms.map(_.toMathOperation).toList
		if (termOperations.size >= 1) {
			termOperations.tail.foldLeft(termOperations.head)((x: MathOperation, y: MathOperation) => x.+(y))
		} else {
			MathTerm("0").get.toMathOperation
		}
	}
	def toLaTeX: String = {
		if (this.unrefinedLaTeX.startsWith("+ ")) {
			this.unrefinedLaTeX.substring(2)
		} else { //first term is negative
			this.unrefinedLaTeX.substring(0, 1) + this.unrefinedLaTeX.substring(2) //get rid of space in "- <mathterm>"
		}
	}
	def unrefinedLaTeX: String = {
		(for (term <- this.getTerms) yield monomialLaTeX(term)).mkString(" ")
	}
	def monomialLaTeX(monomial: MathTerm): String = {
		val coefficient = monomial.getCoefficient.getValue
		 if (coefficient != null && coefficient > 0) {
			 "+ %s".format(monomial.toLaTeX)
		 } else {
			 "- %s".format(withoutNegativeSign(monomial.toLaTeX))
		 }
	}
	private def withoutNegativeSign(str: String): String = {
		if (str.length > 1) str.substring(1) else ""
	}
	def description: String = "MathPolynomial" + {
		(for (term <- this.getTerms) yield {
			term.description
		}).mkString("(", ", ", ")")
	}
	override def equals(that: Any): Boolean = {
		that match {
			case that: MathPolynomial => this.toMathOperation == that.toMathOperation
			case _ => false
		}
	}
}

object MathPolynomial {
	def apply(terms: List[MathTerm]) = new MathPolynomial(terms)
	def apply(str: String): Option[MathPolynomial] = {
		val s = removeAllSpacesIn(str)
		val terms: List[String] = getAllTerms(s)
		if (allTermsAreValid(terms)) {
			val mathTerms: List[MathTerm] = convertToMathTerms(terms)
			Some(MathPolynomial(mathTerms))
		} else {
			None
		}
	}
	def removeAllSpacesIn(s: String): String = {
		""" """.r.replaceAllIn(s, "")
	}
	def getAllTerms(s: String): List[String] = {
		val regexSplit = """(?<=\S)(?=[+-])""".r
		regexSplit.split(s).toList
	}
	def allTermsAreValid(terms: List[String]): Boolean = {
		terms.forall(MathTerm(_) isDefined)
	}
	def convertToMathTerms(strings: List[String]): List[MathTerm] = {
		(for (s <- strings) yield {
			MathTerm(s).get
		}).toList
	}
}