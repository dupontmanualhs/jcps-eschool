package eschool.math
import scala.math.Pi

class MathConstantPi() extends MathConstant("pi", MathNumber(Pi))

object MathConstantPi {
	def apply() = new MathConstantPi()
}