package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id

class AcademicYear extends Id[Long] {
  @Unique
  @Column(allowsNull="false")
  private[this] var _name: String = _
  
  def this(name: String) = {
    this()
    _name = name
  }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
}

object QAcademicYear 