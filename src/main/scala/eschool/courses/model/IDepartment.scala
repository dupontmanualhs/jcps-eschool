package eschool.courses.model

import bootstrap.liftweb.DataStore
import eschool.courses.model.jdo.{QDepartment, Department}


object IDepartment {
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