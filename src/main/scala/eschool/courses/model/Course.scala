package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id

class Course extends Id[Long] {
  private[this] var _name: String = _
  @Unique
  private[this] var _masterNumber: String = _
  private[this] var _department: Department = _
  
  def this(name: String, masterNumber: String, department: Department) = {
    this()
    _name = name
    _masterNumber = masterNumber
    _department = department
  }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def masterNumber: String = _masterNumber
  def masterNumber_=(theMasterNumber: String) { _masterNumber = theMasterNumber }
  
  def department: Department = _department
  def department_=(theDepartment: Department) { _department = theDepartment }
}