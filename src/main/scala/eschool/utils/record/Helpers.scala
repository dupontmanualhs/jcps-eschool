package eschool.utils.record

import net.liftweb.common.{Box, Empty, Full}

object Helpers {
  def toBox(s: String): Box[String] = {
    if (s.length > 0) Full(s) else Empty
  }

}