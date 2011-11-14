package eschool.courses.model

import javax.jdo.annotations._
import bootstrap.liftweb.DataStore
import jdo.Id

class Department extends Id[Long] {
  private[this] var _name: String = _

  def this(name: String) = {
    this()
    _name = name
  }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
}

object Department {
  def getOrCreate(name: String): Department = {
    val cand = QDepartment.candidate
    DataStore.pm.query[Department].filter(cand.name.eq(name)).executeOption() match {
      case Some(dept) => dept
      case None => {
        val dept = new Department(name)
        DataStore.pm.makePersistent(dept)
      }
    }
  }
}
