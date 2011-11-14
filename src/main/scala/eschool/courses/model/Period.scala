package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id

class Period extends Id[Long] {
  private[this] var _name: String = _
  private[this] var _order: Int = _
  
  def this(name: String, order: Int) = {
    this()
    _name = name
    _order = order
  }

  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def order: Int = _order
  def order_=(theOrder: Int) { _order = theOrder }
}