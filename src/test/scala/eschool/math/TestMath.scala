package eschool.math

import org.junit.Assert._
import org.junit.Test
import java.lang.Math
import io.BytePickle.Def
import org.scalatest.junit.JUnitSuite

class TestMath extends JUnitSuite {
  @Test def rationals() {
    val one = MathNum("1").get
    assert(one.toString === "1")
    assert(one.toRepr === "MathRational(1, 1)")
    assert(one.toLaTeX === "1")
    val oneHalf = MathNum("1/2").get
    assert(oneHalf.toString === "1/2")
    assert(oneHalf.toRepr === "MathRational(1, 2)")
    assert(oneHalf.toLaTeX === "\\frac{1}{2}")
    val nonsense = MathNum("what?")
    assertTrue(nonsense.isEmpty)
    val moreNonsense = MathNum("who/how")
    assertTrue(moreNonsense.isEmpty)
    val bigFraction = MathNum("4567890123456/-1234567890123").get
    assert(bigFraction.toString === "4567890123456/-1234567890123")
    assert(bigFraction.toRepr === "MathRational(BigInt(4567890123456), BigInt(-1234567890123))")
    assert(bigFraction.toLaTeX === "\\frac{4567890123456}{-1234567890123}")
  }

  @Test def terminatingDecimals() {
    val one = MathNum("1.0").get
    assert(one.toString === "1.0")
    assert(one.toRepr === "MathDecimal(\"1.0\")")
    assert(one.toLaTeX === "1.0")
    val small = MathNum("1E-24").get
    assert(small.toString === "1E-24")
    assert(small.toRepr === "MathDecimal(\"1E-24\")")
    assert(small.toLaTeX === "1E-24") // TODO: should this be 1 * 10^{-24}
    val big = MathNum("2.04E+3").get
    assert(big.toString === "2.04E+3")
    assert(big.toRepr === "MathDecimal(\"2.04E+3\")")
    assert(big.toLaTeX === "2.04E+3") // TODO: should this be 2.04 * 10^3
  }

  @Test def inexacts() {
    val one = MathNum("\u22481").get
    assert(one.toString === "\u22481.0")
    assert(one.toRepr === "MathInexact(1.0)")
    assert(one.toLaTeX === "\\approx 1.0")
  }

  @Test def complex() {
    val oneI = MathNum("1i").get
    assert(oneI.toString === "0+1i")
    assert(oneI.toRepr === "MathComplex(MathRational(0, 1), MathRational(1, 1))")
    assert(oneI.toLaTeX === "0+1i")
    val approx = MathNum("\u22483/5-2/3i").get
    assert(approx.toString === "\u2248(0.6-0.6666666666666666i)")
    assert(approx.toRepr === "MathComplex(MathInexact(0.6), MathInexact(-0.6666666666666666))")
    assert(approx.toLaTeX === "\\approx(0.6-0.6666666666666666i)")
    val expts = MathNum("2E+3-4e-3i").get
    assert(expts.toString === "2E+3-0.004i")
    assert(expts.toRepr === "MathComplex(MathDecimal(\"2E+3\"), MathDecimal(\"-0.004\"))")
    assert(expts.toLaTeX === "2E+3-0.004i")
  }

}