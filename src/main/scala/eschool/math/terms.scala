package eschool.math

import collection.immutable.TreeMap

class MathTerm(coefficient: MathConstant, variableSequence: TreeMap[String, MathInteger]) extends MathExpression {
	def getCoefficient: MathConstant = coefficient
	def getVariableSequence: TreeMap[String, MathInteger] = variableSequence
	override def getPrecedence: Int = 4
	def toMathOperation: MathProduct = {
		if (this.getVariableSequence == null) {
			MathProduct(this.getCoefficient, MathInteger(1))
		} else if (this.getVariableSequence.size == 1) {
			MathProduct(this.getCoefficient, varPowToExponentiation(this.getVariableSequence.head))
		} else if (this.getCoefficient.getValue != 1) {
			MathProduct(this.getCoefficient, MathTerm(this.getVariableSequence).toMathOperation)
		} else if (this.getVariableSequence.size == 2) {
			MathProduct(varPowToExponentiation(this.getVariableSequence.head), varPowToExponentiation(this.getVariableSequence.last))
		} else {
			MathProduct(varPowToExponentiation(this.getVariableSequence.head), MathTerm(this.getVariableSequence.tail).toMathOperation)
		}
	}
	override def toLaTeX: String = { if(this.getCoefficient.getValue != 1) this.getCoefficient.toLaTeX else ""} + this.variableSequenceLaTeX
	override def description: String = "MathTerm(" + this.getCoefficient.description + {if(this.variableSequence == null || this.variableSequence.size == 0) "" else {", " + variableSequenceDescription}} + ")"
	override def simplify: MathExpression = this

	private def varPowToExponentiation(varPow: (String, MathInteger)): MathExponentiation = {
		MathExponentiation(MathVariable(varPow._1).get, varPow._2)
	}
	private def variableSequenceDescription: String = {
		(for ((name: String, pow: MathInteger) <- this.getVariableSequence) yield {
			"\"%s\" -> %s".format(name, pow.description)
		}).mkString(", ")
	}

	private def variableSequenceLaTeX: String = {
		(for ((name: String, pow: MathInteger) <- this.getVariableSequence) yield {
			if (pow.getValue != 0) {
				name +
					{ if (pow.getValue != 1) "^" + pow.toLaTeX else "" }
			} else {
				""
			}
		}).mkString
	}
}

object MathTerm {
	def apply(coefficient: MathConstant, variableSequence: TreeMap[String, MathInteger]) = new MathTerm(coefficient, variableSequence)
	def apply(variableSequence: TreeMap[String, MathInteger]): MathTerm = MathTerm(MathInteger(1), variableSequence)
	def apply(coefficient: MathConstant, variableSequence: (String, MathInteger)*): MathTerm = MathTerm(coefficient, variableSequence: _*)
	def apply(variableSequence: (String, MathInteger)*): MathTerm = MathTerm(MathInteger(1), variableSequence: _*)
	def apply(s: String): Option[MathTerm] = {
		val potentialTermSegments: Array[String] = getTermSegments(s)
		if (potentialTermSegments.size < 1) {
			None
		} else {
			getTermFromArray(potentialTermSegments)
		}
	}

	def getTermFromArray(termSegments: Array[String]): Option[MathTerm] = {
		val potentialCoefficient: Option[MathConstant] = MathConstant(termSegments.head)
		val variableSegments = arrayWithoutCoefficient(potentialCoefficient, termSegments)
		val potentialVariableSequence: Option[TreeMap[String, MathInteger]] = extractVarSequenceFromArray(variableSegments)
		val coefficient = potentialCoefficient match {
			case None => MathInteger(1)
			case Some(mathConstant) => mathConstant
		}
		(coefficient, potentialVariableSequence) match {
			case (_, None) => None
			case (coef, Some(treeMap)) => Some(new MathTerm(coef, treeMap))
		}
	}

	def arrayWithoutCoefficient(coefToRemove: Option[MathConstant], strings: Array[String]): Array[String] = {
		if (coefToRemove == None) {
			strings
		} else {
			strings.tail
		}
	}

	def getTermSegments(s: String): Array[String] = {
		val splitTermRegex = """(?=([a-hj-zA-Z](\^\d+)?))""".r
		splitTermRegex.split(s)
	}

	def extractVarSequenceFromArray(strings: Array[String]): Option[TreeMap[String, MathInteger]] = {
		if (isVariableSequence(strings)){
			Some(extractVarsPowered(strings))
		} else {
			None
		}
	}

	def isVariableSequence(strings: Array[String]): Boolean = {
		if (strings.isEmpty) true
		else strings.forall(extractVariablePowered(_).isDefined)
	}

	def extractVarsPowered(strings: Array[String]): TreeMap[String, MathInteger] = {
		TreeMap((for ((s: String) <- strings) yield {
			extractVariablePowered(s).get
		}):_*)
	}

	def extractVariablePowered(s: String): Option[(String, MathInteger)] = {
		val variable = extractVariableFromString(s)
		val power = extractPowerFromString(s)
		(variable, power) match {
			case (None, _) => None
			case (aVar, _) => Some((aVar.get, power))
		}
	}

	def extractVariableFromString(s: String): Option[String] = {
		val varRegex = """[\^]""".r
		if (varRegex.split(s).isEmpty) {
			return None
		}
		MathVariable(varRegex.split(s).head) match {
			case Some(aVar) => Some(aVar.getName)
			case _ => None
		}
	}
	def extractPowerFromString(s: String): MathInteger = {
		val powerRegex = """[\^]""".r
		if (powerRegex.split(s).isEmpty) {
			return MathInteger(1)
		}
		val powerSegment = powerRegex.split(s).tail
		MathInteger(powerSegment.mkString) match {
			case None => MathInteger(1)
			case Some(anInt) => anInt
		}
	}
}