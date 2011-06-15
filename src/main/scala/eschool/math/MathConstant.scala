package eschool.math

class MathConstant(aName: String, aNumber: MathNumber) extends MathExpression {
	private val name = aName
	private val value = aNumber

	override def containsAVariable = false

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		this.value.evaluateUsingTheseVariableValues(variables)
	}

	override def getPrecedence: Int = 4

	override def simplify: MathExpression = value

	override def toSmartString = name

	override def toString: String = name + " = " + value
}

object MathConstant {
	def apply(aName: String, aNumber: MathNumber) = new MathConstant(aName, aNumber)
	def apply(aName: String, aDouble: Double) = new MathConstant(aName, MathNumber(aDouble))
}