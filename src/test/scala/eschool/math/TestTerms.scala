package eschool.math

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import org.junit.Assert._
import org.specs.runner.JUnitSuite
import collection.immutable.TreeMap

class TestTerms extends AssertionsForJUnit {
	@Test def terms() {
		val sixX2 = MathTerm(MathInteger(6), TreeMap[String, MathInteger]("x" -> MathInteger(2)))
		assert(sixX2.toLaTeX === "6x^2")
		assert(sixX2.description === "MathTerm(MathInteger(6), \"x\" -> MathInteger(2))")
		assert(sixX2.toMathOperation.toLaTeX === MathProduct(MathInteger(6), MathExponentiation(MathVariable("x").get, MathInteger(2))).toLaTeX)
		val fourx3y5z = MathTerm(MathInteger(4), TreeMap[String, MathInteger]("x" -> MathInteger(3), "y" -> MathInteger(5), "z" -> MathInteger(1)))
		assert(fourx3y5z.toLaTeX === "4x^3y^5z")
		assert(fourx3y5z.toMathOperation.toLaTeX === MathProduct(MathInteger(4), MathProduct(MathExponentiation(MathVariable("x").get, MathInteger(3)), MathProduct(MathExponentiation(MathVariable("y").get, MathInteger(5)), MathExponentiation(MathVariable("z").get, MathInteger(1))))).toLaTeX)
		val ninex0y7 = MathTerm(MathInteger(9), TreeMap[String, MathInteger]("x" -> MathInteger(0), "y" -> MathInteger(7)))
		assert(ninex0y7.toLaTeX === "9y^7")
		val eight = MathTerm("8").get
		assert(eight.description === "MathTerm(MathInteger(8))")
		val eightx2 = MathTerm("8x^2").get
		assert(eightx2.toLaTeX === "8x^2")
		val x2 = MathTerm("x^2").get
		assert(x2.description === "MathTerm(MathInteger(1), \"x\" -> MathInteger(2))")
		assert(x2.toLaTeX === "x^2")
		val mess = MathTerm("^^^")
		assertTrue(mess == None)
		val withParens = MathTerm("6(x^2)(y^3)")
		assertTrue(withParens == None)
	}
}