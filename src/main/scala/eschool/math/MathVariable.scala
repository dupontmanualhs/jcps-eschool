package eschool.math

class MathVariable(aName: String) extends MathExpression {
	private val name = aName

	override def containsAVariable: Boolean = true

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = {
		if (variables.contains(this.name)) {
			val variableValue: MathExpression = variables(this.name)
			variableValue.evaluateUsingTheseVariableValues(variables)
		} else {
			1.0 // use 1.0 for default variable value if the variable is not defined
		}
	}

	override def getPrecedence: Int = 4

	override def simplify: MathExpression = new MathVariable(name)

	override def toSmartString: String = name

	override def toString: String = name
}

object MathVariable {
	def apply(aName: String) = new MathVariable(aName)
	def apply(aName: Char) = new MathVariable(aName.asInstanceOf[String])
}