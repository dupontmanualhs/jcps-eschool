package eschool.math

//MathConstant subclasses: MathConstantPi & MathConstantE (see below)
//                                      MathNumber (Math numbers.scala)
abstract class MathConstant extends MathValue {
	def getValue: BigDecimal
	override def equals(that: Any): Boolean = {
		that match {
			case that: MathConstant => (that.getValue == this.getValue)
			case _ => false
		}
	}
}

object MathConstant {
	def apply(s: String): Option[MathConstant] = {
		s match {
			case "\\pi" => Some(MathConstantPi())
			case "e"  => Some(MathConstantE())
			case _    => MathNumber(s)
		}
	}
}

class MathConstantE extends MathConstant {
	override def getValue: BigDecimal = scala.math.E
	override def toLaTeX: String = "e"
	override def description: String = "MathConstantE"
}

object MathConstantE {
	def apply() = new MathConstantE
}

class MathConstantPi extends MathConstant {
	override def getValue: BigDecimal = scala.math.Pi
	override def toLaTeX: String = MathConstantPi.symbol
	override def description: String = "MathConstantPi"
}

object MathConstantPi {
	def apply() = new MathConstantPi
	def symbol = "\\pi"
}


