package eschool.math

import org.scalatest.junit.AssertionsForJUnit
import org.junit.Test
import org.junit.Assert._
import org.specs.runner.JUnitSuite

class TestValues extends AssertionsForJUnit {
	@Test def integers() {
		val one: MathValue = MathValue("1").get
		assert(one.toString === "1")
		assert(one.description === "MathInteger(1)")
		assert(one.toLaTeX === "1")
		val negative: MathValue = MathValue("-1").get
		assert(negative.toString === "-1")
		assert(negative.description === "MathInteger(-1)")
		assert(negative.toLaTeX === "-1")
	}

	@Test def fractions() {
		val four: MathValue = MathValue("4/1").get
		assert(four.toString === "4")
		assert(four.description === "MathFraction(4, 1)")
		assert(four.toLaTeX === "4")
		val oneHalf = MathValue("1/2").get
		assert(oneHalf.toString === "\\frac{1}{2}")
		assert(oneHalf.description === "MathFraction(1, 2)")
		assert(oneHalf.toLaTeX === "\\frac{1}{2}")
		val bigFraction = MathValue("4567890123456/-1234567890123").get
		assert(bigFraction.toString === "\\frac{4567890123456}{-1234567890123}")
		assert(bigFraction.description === "MathFraction(BigInt(4567890123456), BigInt(-1234567890123))")
		assert(bigFraction.toLaTeX === "\\frac{4567890123456}{-1234567890123}")
	}

	@Test def terminatingDecimals() {
		val one = MathValue("1.0").get
		assert(one.toString === "1.0")
		assert(one.description === "MathDecimal(\"1.0\")")
		assert(one.toLaTeX === "1.0")
		val small = MathValue("1E-24").get
		assert(small.toString === "1E-24")
		assert(small.description === "MathDecimal(\"1E-24\")")
		assert(small.toLaTeX === "1E-24") // TODO: should this be 1 * 10^{-24}
		val big = MathValue("2.04E+3").get
		assert(big.toString === "2.04E+3")
		assert(big.description === "MathDecimal(\"2.04E+3\")")
		assert(big.toLaTeX === "2.04E+3") // TODO: should this be 2.04 * 10^3
	}

	@Test def approximations() {
		val one = MathValue("\u22481").get
		assert(one.toString === "\\approx 1.0")
		assert(one.description === "MathApproximateNumber(1.0)")
		assert(one.toLaTeX === "\\approx 1.0")
		val oneHalf = MathValue("\u22480.5").get
		assert(oneHalf.toString === "\\approx 0.5")
		assert(oneHalf.description === "MathApproximateNumber(0.5)")
		assert(oneHalf.toLaTeX === "\\approx 0.5")
	}

	@Test def complex() {
		val oneI = MathComplexNumber("0+1i").get
		assert(oneI.description === "MathComplexNumber(MathInteger(0), MathInteger(1))")
		assert(oneI.toLaTeX === "0+1i")
		val approx = MathValue("\u22483/5-2/3i").get
		assert(approx.description === "MathComplexNumber(MathApproximateNumber(0.6), MathApproximateNumber(-0.6666666666666666))")
		assert(approx.toLaTeX === "\\approx(0.6-0.6666666666666666i)")
		val expts = MathValue("2E+3-4E-3i").get
		assert(expts.description === "MathComplexNumber(MathDecimal(\"2E+3\"), MathDecimal(\"-0.004\"))")
		assert(expts.toLaTeX === "2E+3-0.004i")
		val withParens = MathValue("(34+6i)").get
		assert(withParens.description === "MathComplexNumber(MathInteger(34), MathInteger(6))")
		assert(withParens.toLaTeX === "34+6i")
		val neg = MathValue("34-6i").get
		assert(neg.description === "MathComplexNumber(MathInteger(34), MathInteger(-6))")
		assert(neg.toLaTeX === "34-6i")
		val variable = MathComplexNumber("radius")
		assertTrue(variable == None)
		val mess = MathComplexNumber("xyz+4i")
		assertTrue(mess == None)
		val xVar = MathComplexNumber("x")
		assertTrue(xVar == None)
	}

	@Test def constantE() {
		val etest1 = MathConstantE()
		assert(etest1.description === "MathConstantE")
		assert(etest1.toLaTeX === "e")
		val etest2 = MathConstant("e").get
		assert(etest2.description === "MathConstantE")
		assert(etest2.toLaTeX === "e")
		val etest3 = MathValue("e").get
		assert(etest3.description === "MathConstantE")
		assert(etest3.toLaTeX === "e")
	}

	@Test def constantPi() {
		val piTest1 = MathConstantPi()
		assert(piTest1.description === "MathConstantPi")
		assert(piTest1.toLaTeX === "\\pi")
		val piTest2 = MathConstant("pi").get
		assert(piTest2.description === "MathConstantPi")
		assert(piTest2.toLaTeX === "\\pi")
		val piTest3 = MathValue("pi").get
		assert(piTest3.description === "MathConstantPi")
		assert(piTest3.toLaTeX === "\\pi")
	}

	@Test def variables() {
		val xVar = MathVariable("x").get
		assert(xVar.description === "MathVariable(x)")
		assert(xVar.toLaTeX === "x")
		val xChar = MathVariable('x').get
		assert(xChar.description === "MathVariable(x)")
		assert(xChar.toLaTeX === "x")
		val xVar2 = MathValue("x").get
		assert(xVar2.description === "MathVariable(x)")
		assert(xVar2.toLaTeX === "x")
		val xChar2 = MathValue('x').get
		assert(xChar2.description === "MathVariable(x)")
		assert(xChar2.toLaTeX === "x")
		val radius = MathValue("radius").get
		assert(radius.description === "MathVariable(radius)")
		assert(radius.toLaTeX === "radius")
		val pi = MathVariable("pi")
		assertTrue(pi == None)
	}
}