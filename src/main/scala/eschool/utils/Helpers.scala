package eschool.utils

import xml.NodeSeq
import net.liftweb.http.{S, Templates, NotFoundResponse, ResponseShortcutException}
import java.util.Locale

object Helpers {
  def pluralizeInformal(num: Int, word: String): String = {
    val listNoChange = List("moose", "fish", "deer", "sheep", "means", "offspring", "series", "species")
    if (listNoChange.contains(word)){
      return intToWordInformal(num) + " " + word
    }
    if (word.matches(".*child$")){
      return intToWordInformal(num) + " " + word.replaceAll("d$", "dren")
    }
    if (word.matches("^person$")){
      return intToWordInformal(num) + " " + word.replaceAll("person", "people")
    }
    if (word.matches(".*(?<![a])bus$")){
      return intToWordInformal(num) + " " + word.replaceAll("bus$", "buses")
    }
    if (word.matches("^circus$")){
      return intToWordInformal(num) + " " + word.replaceAll("s$", "ses")
    }
    if (word.matches("^vertebra$")){
      return intToWordInformal(num) + " " + word.replaceAll("bra$", "brae")
    }
    if (word.matches(".*genus$")){
      return intToWordInformal(num) + " " + word.replaceAll("us$", "era")
    }
    if (word.matches(".*corpus")){
      return intToWordInformal(num) + " " + word.replaceAll("us$", "ora")
    }
    if (word.matches(".*[otf]ax")){
      return intToWordInformal(num) + " " + word.replaceAll("x$", "xes")
    }
    if (word.matches(".*[mnf]ix")){
      return intToWordInformal(num) + " " + word.replaceAll("x$", "xes")
    }
    if (word.matches(".*(?<![aeiou])y$")){
      return intToWordInformal(num) + " " + word.replaceAll("y$", "ies")
    }
    if (word.matches(".*[cs]h$")){
      return intToWordInformal(num) + " " + word.replaceAll("h$", "hes")
    }
    if (word.matches(".*fe$")){
      return intToWordInformal(num) + " " + word.replaceAll("fe$", "ves")
    }
    if (word.matches(".*f$")){
      return intToWordInformal(num) + " " + word.replaceAll("f$", "ves")
    }
    if (word.matches(".*o$")){
      return intToWordInformal(num) + " " + word.replaceAll("o$", "oes")
    }
    if (word.matches(".*z$")){
      return intToWordInformal(num) + " " + word.replaceAll("z$", "zzes")
    }
    if (word.matches(".*us$")){
      return intToWordInformal(num) + " " + word.replaceAll("us$", "i")
    }
    if (word.matches(".*is$")){
      return intToWordInformal(num) + " " + word.replaceAll("is$", "es")
    }
    if (word.matches(".*fe$")){
      return intToWordInformal(num) + " " + word.replaceAll("fe$", "ves")
    }
    if (word.matches(".*(?<![z])on$")){
      return intToWordInformal(num) + " " + word.replaceAll("on$", "a")
    }
    if (word.matches(".*um$")){
      return intToWordInformal(num) + " " + word.replaceAll("um$", "a")
    }
    if (word.matches("[ml]ouse$")){
      return intToWordInformal(num) + " " + word.replaceAll("ouse$", "ice")
    }
    if (word.matches(".*(?<![r])a$")){
      return intToWordInformal(num) + " " + word.replaceAll("a$", "ae")
    }
    if (word.matches(".*[tgf]oo(?![d]).*{1,2}$")){
      return intToWordInformal(num) + " " + word.replaceAll("oo", "ee")
    }
    if (word.matches(".*x$")){
      return intToWordInformal(num) + " " + word.replaceAll("x$", "ces")
    }
    if (word.matches(".*eau$")){
      return intToWordInformal(num) + " " + word.replaceAll("eau$", "eaux")
    }
    if (word.matches(".*man$")){
      return intToWordInformal(num) + " " + word.replaceAll("man$", "men")
    }
    if (word.matches(".*s$")){
      return intToWordInformal(num) + " " + word.replaceAll("s$", "ses")
    }
    intToWordInformal(num) + " " + word + (if (num != 1) "s" else "")
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

  def getTemplate(path: List[String], locale: Locale): NodeSeq = {
    Templates(path, locale) openOr  {
      S.error("Could not find a template: " + path.mkString("/"))
      S.redirectTo("/error")
    }
  }

  def getTemplate(path: List[String]): NodeSeq = getTemplate(path, S.locale)

  def getRawTemplate(path: List[String], locale: Locale): NodeSeq = {
    Templates.findRawTemplate(path, locale) openOr {
      S.error("Could not find a template: " + path.mkString("/"))
      S.redirectTo("/error")
    }
  }

  def getRawTemplate(path: List[String]): NodeSeq = getRawTemplate(path, S.locale)
}