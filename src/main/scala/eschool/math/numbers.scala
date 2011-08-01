package eschool.math

import java.math.MathContext
import util.matching.Regex

abstract class MathNumber extends MathConstant {
	//def toMathMlPresentation(): NodeSeq
	//def toMathMlContent(): NodeSeq
}

object MathNumber {
	def apply(s: String): Option[MathNumber] = {
		MathRealNumber(s) orElse MathComplexNumber(s)
	}

	def stringToDecimal(s: String): Option[BigDecimal] = {
		if (numBeginsWithPlusSign(s)) {
			stringToDecimal(s.substring(1))
		}
		try {
			Some(BigDecimal(s, MathContext.UNLIMITED))
		} catch {
			case e: NumberFormatException => None
		}
	}

	def stringToDouble(s: String): Option[Double] = {
		try {
			Some(s.toDouble)
		} catch {
			case e: NumberFormatException => None
		}
	}

	def stringToInt(num: String): Option[BigInt] = {
		val str = if (numBeginsWithPlusSign(num)) num.substring(1) else num
		try {
			Some(BigInt(str))
		} catch {
			case e: NumberFormatException => None
		}
	}

	def numBeginsWithPlusSign(num: String): Boolean = (num.length != 0 && num != null && num.charAt(0) == '+')


	def intDescription(i: BigInt): String = {
		if (i < Integer.MIN_VALUE || i > Integer.MAX_VALUE) {
			"BigInt(%s)".format(i.toString())
		} else {
			"%d".format(i)
		}
	}
}

abstract class MathRealNumber extends MathNumber {
	override def getValue: BigDecimal = this.toApproximation.getValue
	def toApproximation: MathApproximateNumber
}

object MathRealNumber {
	def apply(s: String): Option[MathNumber] = {
		MathInteger(s) orElse MathFraction(s) orElse MathDecimal(s) orElse MathApproximateNumber(s)
	}
}

abstract class MathExactNumber extends MathRealNumber

class MathFraction(val numerator: BigInt, val denominator: BigInt) extends MathExactNumber {
	def getNumerator = numerator
	def getDenominator = denominator

	def toMathOperation: MathQuotient = MathQuotient(MathInteger(numerator), MathInteger(denominator))

	override def toApproximation: MathApproximateNumber = MathApproximateNumber(this.getNumerator.toDouble./(this.getDenominator.toDouble))

	override def getPrecedence: Int = 3

	private def formatString(s: String): String = {
		/*if (this.getDenominator == 1) {
			s.format(this.getNumerator.toString())
		} else {         */
			s.format(this.getNumerator.toString(), this.getDenominator.toString())
		//}
	}

	override def toLaTeX: String = {
		/*if (this.getDenominator == 1) formatString("%s") else */formatString("\\frac{%s}{%s}")
	}

	override def description: String = {
		"MathFraction(%s, %s)".format(MathNumber.intDescription(this.getNumerator), MathNumber.intDescription(this.getDenominator))
	}
}

object MathFraction {
	def apply(numerator: BigInt, denominator: BigInt) = new MathFraction(numerator, denominator)
	def apply(numerator: MathInteger, denominator: MathInteger) = new MathFraction(numerator.getInt, denominator.getInt)
	def apply(s: String): Option[MathFraction] = MathFraction.stringToFraction(s)
	def stringToFraction(s: String): Option[MathFraction] = {
		val fractionLaTeXRegex = new Regex("""^([+-]?)\\frac\{([+-]?\d+)\}\{([+-]?\d+)\}$""", "operator", "numerator", "denominator")
		val normalFractionRegex = new Regex("""^([+-])?([+-]?\d+)/([+-]?\d+)$""", "operator", "numerator", "denominator")
		val potentialFraction = fractionLaTeXRegex.findFirstMatchIn(s) orElse normalFractionRegex.findFirstMatchIn(s)
		if (potentialFraction.isDefined) {
			Some(MathFraction(MathInteger(potentialFraction.get.group("operator") + potentialFraction.get.group("numerator")).get, MathInteger(potentialFraction.get.group("denominator")).get))
		} else {
			None
		}
	}
}

class MathInteger(anInt: BigInt) extends MathFraction(anInt, BigInt(1)) {
	def getInt = anInt
	override def getPrecedence: Int = 6
	override def getValue: BigDecimal = BigDecimal(anInt)
	override def description: String = "MathInteger(%s)".format(MathNumber.intDescription(anInt))
	override def toLaTeX: String = "" + this.getInt
}

object MathInteger {
	def apply(anInt: BigInt): MathInteger = new MathInteger(anInt)
	def apply(s: String): Option[MathInteger] = {
		MathNumber.stringToInt(s) match {
			case Some(bigInt) => Some(MathInteger(bigInt))
			case _ => None
		}
	}
}


class MathDecimal(val value: BigDecimal) extends MathExactNumber {
	override def getValue: BigDecimal = value

	def toApproximation: MathApproximateNumber = MathApproximateNumber(this.getValue.toDouble)

	def toLaTeX: String = {
		if (this.getValue.toString().contains("E")) {
			this.scientificNumberLaTeX
		} else {
			this.getValue.toString()
		}
	}

	private def scientificNumberLaTeX: String = {
		val regex = new Regex("""^([+-]?[.\d]*)?E([+-]\d+)$""", "coefficient", "power")
		val scientificNum = regex.findFirstMatchIn(this.getValue.toString()).get
		val potentialCoef: String = scientificNum.group("coefficient")
		val potentialPower: String = scientificNum.group("power")
		val coefficient: String = getCoefficientFromString(potentialCoef)
		val power = getPowerFromString(potentialPower)
		"%s*10^{%s}".format(coefficient, power)
	}

	def getCoefficientFromString(s: String): String = {
		(MathNumber.stringToInt(s) orElse MathNumber.stringToDecimal(s)).getOrElse("1").toString
	}

	def getPowerFromString(s: String): String = {
		MathInteger(s) match {
			case Some(aMathInt) => aMathInt.toString
			case _ => ""
		}
	}

	def description: String = "MathDecimal(\"%s\")".format(this.getValue.toString())
}

object MathDecimal {
	def apply(bigDecimal: BigDecimal) = new MathDecimal(bigDecimal)

	def apply(s: String): Option[MathDecimal] = {
		MathNumber.stringToDecimal(s).map(MathDecimal(_))
	}

	def getPowerFromString(s: String): String = {
		MathInteger(s) match {
			case Some(aMathInt) => aMathInt.toString
			case _ => ""
		}
	}
}


class MathApproximateNumber(val value: BigDecimal) extends MathRealNumber {
	override def getValue: BigDecimal = value

	def toApproximation: MathApproximateNumber = this

	override def toLaTeX: String = MathApproximateNumber.prefix + this.getValue.toString()

	def description: String = "MathApproximateNumber(%s)".format(this.getValue.toString())
}

object MathApproximateNumber {
	val prefix: String = "\\approx"
	val symbol: String = "\u2248"
	def apply(d: BigDecimal) = new MathApproximateNumber(d)
	def apply(s: String): Option[MathNumber] = {
		val approxRegex = new Regex("""^\\approx(.+)$""", "value")       //TODO: does not work for unicode (yet)
		val potentialApprox = approxRegex.findFirstMatchIn(s)
		if (potentialApprox.isDefined) {
			MathExpression(potentialApprox.get.group("value")) match {
				case Some(aBigDecimal: MathDecimal) => Some(MathApproximateNumber(aBigDecimal.getValue))
				case Some(aComplexNum: MathComplexNumber) => Some(MathComplexNumber(MathApproximateNumber(aComplexNum.getReal.getValue), MathApproximateNumber(aComplexNum.getImaginary.getValue)))
				case _ => None
			}
		} else {
			None
		}
	}
}

class MathComplexNumber(val real: MathRealNumber, val imag: MathRealNumber) extends MathNumber {
	def getReal: MathRealNumber = real
	def getImaginary: MathRealNumber = imag
	override def getValue: BigDecimal = null
	override def getPrecedence: Int = -1

	// if either real or imag are approximate, both are coerced to approximate
	def isApproximation: Boolean = real.isInstanceOf[MathApproximateNumber] || imag.isInstanceOf[MathApproximateNumber]

	private def realToLaTeX: String = {
		if (isApproximation) {
			this.getReal.toApproximation.getValue.toString()
		} else {
			this.getReal.toLaTeX
		}
	}

	private def imaginaryToLaTeX: String = {
		if (isApproximation) {
			this.getImaginary.toApproximation.getValue.toString()
		} else {
			this.getImaginary.toLaTeX match {
				case "1" => ""
				case "-1" => "-"
				case str: String => str
			}
		}
	}

	private def complexString: String = {
		realToLaTeX + getOperand + imaginaryToLaTeX + "i"
	}

	private def getOperand: String = if (imaginaryToLaTeX.startsWith("-")) "" else "+"

	def toLaTeX: String = {
		if (isApproximation) "\\approx(%s)".format(complexString) else complexString
	}

	def description: String = "MathComplexNumber(%s, %s)".format(getReal.description, getImaginary.description)

	override def equals(that: Any): Boolean = {
		that match {
			case that: MathComplexNumber => this.getReal == that.getReal && this.getImaginary == that.getImaginary
			case _ => false
		}
	}
}

object MathComplexNumber {
	def apply(real: MathRealNumber, imaginary: MathRealNumber): MathComplexNumber = {
		if (real.isInstanceOf[MathApproximateNumber] || imaginary.isInstanceOf[MathApproximateNumber]) {
			new MathComplexNumber(real.toApproximation, imaginary.toApproximation)
		} else {
			new MathComplexNumber(real, imaginary)
		}
	}

	def apply(s: String): Option[MathComplexNumber] = {
		/*val complexString = new ComplexNumberString(s)
		complexString.toMathComplexNumber       */
		val basicComplexRegex = new Regex("""^()(.*)?i$""", "real", "imaginary")
		val complexRegex = new Regex("""^(.*)(?=[+-](?<!E))(.*)?i$""", "real", "imaginary")
		val potentialComplex = complexRegex.findFirstMatchIn(s) orElse  basicComplexRegex.findFirstMatchIn(s)
		if (potentialComplex.isDefined) {
			val potentialRealPart = {
				potentialComplex.get.group("real") match {
					case "" => "0"
					case str: String => str
				}
			}
			val potentialImaginaryPart = {
				potentialComplex.get.group("imaginary") match {
					case "" => "1"
					case "+" => "1"
					case "-" => "-1"
					case str: String => str
				}
			}
			(MathRealNumber(potentialRealPart), MathRealNumber(potentialImaginaryPart)) match {
				case (Some(real: MathRealNumber), Some(imag: MathRealNumber)) => Some(MathComplexNumber(real, imag))
				case _ => None
			}
		} else {
			None
		}
	}
}