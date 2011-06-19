package eschool.math

import scala.math.max
import scala.Some
import java.math.MathContext
import java.lang.Boolean

abstract class MathNum {
  def toLaTeX: String
  def toRepr: String
  //def toMathMlPresentation(): NodeSeq
  //def toMathMlContent(): NodeSeq
}

object MathNum {
  def apply(s: String): Option[MathNum] = {
    MathReal(s) orElse MathComplex(s)
  }

  def stringToDecimal(s: String): Option[BigDecimal] = {
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
    try {
      Some(BigInt(num))
    } catch {
      case e: NumberFormatException => None
    }
  }

  def intRepr(i: BigInt): String = {
    if (i < Integer.MIN_VALUE || i > Integer.MAX_VALUE) {
      "BigInt(%s)".format(i.toString)
    } else {
      "%d".format(i)
    }
  }
}

abstract class MathReal extends MathNum {
  def toInexact: MathInexact
}

object MathReal {
  def apply(s: String): Option[MathReal] = {
    MathRational(s) orElse MathDecimal(s) orElse MathInexact(s)
  }
}

abstract class MathExact extends MathReal {

}

class MathRational(val num: BigInt, val denom: BigInt) extends MathExact {
  private def formatString(s: String): String = {
    if (denom == 1) {
      s.format(num)
    } else {
      s.format(num, denom)
    }
  }

  def toInexact: MathInexact = MathInexact(num.toDouble./(denom.toDouble))

  override def toString: String = {
    if (denom == 1) formatString("%d") else formatString("%d/%d")
  }

  def toLaTeX: String = {
    if (denom == 1) formatString("%d") else formatString("\\frac{%d}{%d}")
  }

  def toRepr: String = {
    "MathRational(%s, %s)".format(MathNum.intRepr(num), MathNum.intRepr(denom))
  }
}

object MathRational {
  def apply(num: BigInt, denom: BigInt) = new MathRational(num, denom)

  def apply(s: String): Option[MathRational] = {
    MathNum.stringToInt(s) match {
      case Some(bigInt) => Some(MathRational(bigInt, BigInt(1)))
      case None => {
        if (s.contains("/")) {
          (MathNum.stringToInt(s.substring(0, s.indexOf("/"))), MathNum.stringToInt(s.substring(s.indexOf("/") + 1))) match {
            case (Some(num), Some(denom)) => Some(MathRational(num, denom))
            case _ => None
          }
        } else {
          None
        }
      }
    }
  }
}


class MathDecimal(val value: BigDecimal) extends MathExact {
  def toInexact: MathInexact = MathInexact(value.toDouble)

  override def toString: String = value.toString

  def toLaTeX: String = toString

  def toRepr: String = "MathDecimal(\"%s\")".format(value.toString)
}

object MathDecimal {
  def apply(bigDecimal: BigDecimal) = new MathDecimal(bigDecimal)

  def apply(s: String): Option[MathDecimal] = {
    MathNum.stringToDecimal(s).map(MathDecimal(_))
  }
}


class MathInexact(val value: Double) extends MathReal {
  def toInexact: MathInexact = this

  override def toString: String = "\u2248" + value.toString

  def toLaTeX: String = "\\approx " + value.toString

  def toRepr:String = "MathInexact(" + value.toString + ")"
}

object MathInexact {
  def apply(d: Double) = new MathInexact(d)

  def apply(s: String): Option[MathInexact] = {
    if (s.startsWith("\u2248")) MathReal(s.substring(1)).map(_.toInexact)
    else None
  }
}

class MathComplex(val real: MathReal, val imag: MathReal) extends MathNum {
  // if either real or imag are Inexact, both are coerced to inexact
  def isInexact: Boolean = real.isInstanceOf[MathInexact] || imag.isInstanceOf[MathInexact]

  private def realString: String = if (isInexact) real.toInexact.value.toString else real.toString

  private def imagString: String = if (isInexact) imag.toInexact.value.toString else imag.toString

  private def numString: String = {
    realString + (if (imagString.startsWith("-")) "" else "+") + imagString + "i"
  }

  override def toString: String = {
    if (isInexact) "\u2248(%s)".format(numString) else numString
  }

  def toLaTeX: String = {
    if (isInexact) "\\approx(%s)".format(numString) else numString
  }

  def toRepr: String = "MathComplex(%s, %s)".format(real.toRepr, imag.toRepr)
}

object MathComplex {
  def apply(real: MathReal, imag: MathReal): MathComplex = {
    if (real.isInstanceOf[MathInexact] || imag.isInstanceOf[MathInexact]) {
      new MathComplex(real.toInexact, imag.toInexact)
    } else {
      new MathComplex(real, imag)
    }
  }

  def apply(s: String): Option[MathComplex] = {
    val stripParens = if (s.startsWith("\u2248(") && s.endsWith(")")) {
      s.substring(0,1) + s.substring(2, s.length - 1)
    } else {
      s
    }
    if (!stripParens.endsWith("i")) {
      None
    } else {
      val newS = stripParens.substring(0, stripParens.length - 1).toUpperCase
      // finds the last + or - in the string not preceded by E
      def breakingSign: Int =
        (newS.length - 1).to(0, -1).find((i: Int) =>
          (newS.charAt(i) == '+' || newS.charAt(i) == '-' &&
            (i == 0 || newS.charAt(i - 1) != 'E'))).getOrElse(-1)
      if (breakingSign <= 0) {
        MathReal(newS) map (MathComplex(MathRational(0, 1), _))
      } else {
        val realPart = newS.substring(0, breakingSign)
        val imagStart = if (newS.charAt(breakingSign) == '+') (breakingSign + 1)
                        else breakingSign
        val imagPart = newS.substring(imagStart)
        (MathReal(realPart), MathReal(imagPart)) match {
          case (Some(re), Some(im)) => Some(MathComplex(re, im))
          case _ => None
        }
      }
    }
  }
}
