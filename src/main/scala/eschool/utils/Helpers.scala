package eschool.utils

object Helpers {
  def pluralizeInformal(num: Int, word: String, ending: String = "s"): String = {
    intToWordInformal(num) + " " + word + (if (num != 1) ending else "")
  }

  def intToWordInformal(num: Int) = {
    if (0 <= num && num <= 10) numMap(num) else num.toString
  }

  val numMap = Map[Int, String](
    0 -> "zero",
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four",
    5 -> "five",
    6 -> "six",
    7 -> "seven",
    8 -> "eight",
    9 -> "nine",
    10 -> "ten"
  )
}