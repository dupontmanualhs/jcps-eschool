package eschool.utils.record

object Gender extends Enumeration {
  type Gender = Value
  val Male, Female, None = Value

  def apply(s: String): Gender = {
    s match {
      case "M" => Male
      case "F" => Female
    }
  }
}