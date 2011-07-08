package eschool.math

//MathConstant subclasses: MathConstantPi & MathConstantE (see below)
//                                      MathNumber (math numbers.scala)
abstract class MathConstant extends MathValue {
	def getValue: BigDecimal
}

object MathConstant {
	def apply(s: String): Option[MathConstant] = {
		s match {
			case "pi" => Some(new MathConstantPi)
			case "e"  => Some(new MathConstantE)
			case _    => MathNumber(s)
		}
	}
}

class MathConstantE extends MathConstant {
	override def getValue: BigDecimal = scala.math.E
	override def toLaTeX: String = "e"
	override def description: String = "MathConstantE"
	override def toString = "e"
}

object MathConstantE {
	def apply() = new MathConstantE
}

class MathConstantPi extends MathConstant {
	override def getValue: BigDecimal = scala.math.Pi
	override def toLaTeX: String = "\\pi"
	override def description: String = "MathConstantPi"
	override def toString = MathConstantPi.symbol
}

object MathConstantPi {
	def apply() = new MathConstantPi
	def symbol: String = "\u03C0"
}