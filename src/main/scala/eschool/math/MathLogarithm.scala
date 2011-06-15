package eschool.math

import scala.math

class MathLogarithm(aBase: MathInteger, anExpression: MathExpression) extends MathUnaryOperation(anExpression) {
	private val base = aBase

	def getBase = base

	override def getName: String = "log" + base.toSmartString

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		math.log(getExpression.evaluateUsingTheseVariableValues(variables)) /
				math.log(base.evaluateUsingTheseVariableValues(variables))
	}

	override def simplify: MathExpression = new MathLogarithm(getBase, getExpression)
}

object MathLogarithm {
	def apply(aBase: MathInteger, anExpression: MathExpression) = new MathLogarithm(aBase, anExpression)
}