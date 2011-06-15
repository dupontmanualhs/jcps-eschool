package eschool.math
import scala.math.E

class MathConstantE() extends MathConstant("e", MathNumber(E))

object MathConstantE {
	def apply() = new MathConstantE()
}