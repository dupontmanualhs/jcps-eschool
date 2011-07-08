package eschool.math

class MathVariable(val name: String) extends MathValue {
	require(name.length == 1, "A Variable must be 1 character long.")
	def getName: String = name
	override def simplify: MathExpression = new MathVariable(this.getName)
	override def getPrecedence: Int = 4
	override def toLaTeX: String = this.getName
	override def description: String = "MathVariable(" + this.getName + ")"
	override def toString = this.getName
}

object MathVariable {
	def apply(name: String): Option[MathVariable] = {
		if (name.length > 1 || name.charAt(0).isNotLetter || name.equalsIgnoreCase("e") || name.equalsIgnoreCase("pi") || name.equals("i") || name.contains("/")) None
		else Some(new MathVariable(name))
	}
	def apply(name: Char): Option[MathVariable] = MathVariable(name.toString)
}