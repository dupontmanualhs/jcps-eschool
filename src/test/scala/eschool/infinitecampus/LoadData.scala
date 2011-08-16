package eschool.icimport

import com.mongodb.Mongo
import bootstrap.liftweb.Boot
import xml.{Node, Elem, XML}

object LoadData {
  def apply(dir: String) {
    new Boot().boot()
    dropAllData()
    loadStudents(XML.loadFile(dir + "/Students.xml"))
  }

  def dropAllData() {
    new Mongo().dropDatabase("eschool") // TODO: parameter for db name
  }

  def loadStudents(doc: Elem) {
    doc \\ "student" foreach ((student: Node) => {

    }
  }

}