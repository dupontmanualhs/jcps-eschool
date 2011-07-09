package eschool

import scala.collection.JavaConversions.asScalaSet

import bootstrap.liftweb.Boot

import users.model.UserData
//import sites.model.SiteData
import net.liftweb.mongodb.{DefaultMongoIdentifier, MongoDB}
import com.mongodb.Mongo

object TestData {
  def create() {
    new Boot().boot()
    dropAllData()
    UserData.create()
    //SiteData.create()
  }

  def dropAllData() {
    new Mongo().dropDatabase("eschool") // TODO: parameter for db name
  }
}