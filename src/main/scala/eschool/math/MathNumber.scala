package eschool.math

import scala.util.Random
import scala.collection.immutable.Map
import scala.math

class MathNumber(theValue: Double) extends MathExpression {
	private val value = theValue

	def getValue: Double = value

	override def evaluateUsingTheseVariableValues(variables: Map[String, MathExpression]): Double = this.getValue

	override def containsAVariable: Boolean = false

	override def simplify: MathExpression = new MathNumber(this.value)

	override def getPrecedence: Int = 4

	override def toSmartString: String = {
		this.toString
	}

	override def toString: String = {
		if (this.isAnInteger) {
			value.asInstanceOf[Int].toString
		} else {
			value.toString
		}
	}

	private def isAnInteger: Boolean = (this.value % 1 == 0.0)
}

object MathNumber {
	def apply(theValue: Double) = new MathNumber(theValue)
	def random(min: Double, max: Double, decimalPlaces: Int): MathNumber = {
		val randomDouble = getRandomDoubleBetween(min, max)
		val result = setToCorrectDecimalPlace(randomDouble, decimalPlaces)
		MathNumber(result)
	}
	def random(min: Double, max: Double): MathNumber = MathNumber.random(min, max, 1)
	def random():MathNumber = MathNumber.random(-10.0, 10.0)

	private def getRandomDoubleBetween(min: Double, max: Double): Double = {
		if (max == min) {
			return min
		}
		val actualMax = math.max(min, max)
		val actualMin = math.min(min, max)
		val range = actualMax - actualMin
		val randomGenerator = new Random()
		randomGenerator.nextDouble() * range + actualMin
	}

	def setToCorrectDecimalPlace(aDouble: Double, decimalPlaces: Int): Double = {
		val factor: Double = math.pow(10, decimalPlaces)
		(aDouble * factor).asInstanceOf[Int] / factor
	}
}
