/*package eschool

import scala.collection.JavaConversions.asScalaSet
import bootstrap.liftweb.Boot
import users.model.UserData
import sites.model.SiteData
import bootstrap.liftweb.DataStore
import java.io.File

object TestData {
  def create() {
    new Boot().boot()
    dropAllData()
    UserData.create()
    SiteData.create()
    DataStore.pm.close()
  }

  def dropAllData() {
    val db = new File("data.h2.db");
    if (db.exists()) db.delete();
  }
}*/