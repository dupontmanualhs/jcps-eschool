package eschool

import scala.collection.JavaConversions.asScalaSet
import bootstrap.liftweb.Boot
import users.model.UserData
import sites.model.SiteData
import bootstrap.liftweb.DataStore

object TestData {
  def create() {
    new Boot().boot()
    dropAllData()
    UserData.create()
    SiteData.create()
    DataStore.pm.close()
  }

  def dropAllData() {
    //TODO: delete H2 file
  }
}