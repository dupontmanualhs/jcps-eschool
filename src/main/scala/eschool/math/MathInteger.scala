package eschool.math
import scala.util.Random
import scala.math

class MathInteger(theValue: Int) extends MathNumber(theValue)

object MathInteger {
	def apply(theValue: Int) = new MathInteger(theValue)
	def random(min: Int, max: Int): MathInteger = MathInteger(getRandomIntegerBetween(min, max))
	def random(): MathInteger = MathInteger.random(-10, 10)
	private def getRandomIntegerBetween(min: Int, max: Int): Int = {
		if (max == min) {
			return min
		}
		val actualMax = math.max(min, max)
		val actualMin = math.min(min, max)
		val range = actualMax - actualMin
		val randomGenerator = new Random()
		return randomGenerator.nextInt(range + 1) + actualMin
	}
}