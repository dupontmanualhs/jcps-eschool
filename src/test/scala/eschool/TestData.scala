package eschool

import scala.collection.JavaConversions.asScalaSet

import bootstrap.liftweb.Boot

import users.model.UserData
import sites.model.SiteData

object TestData {
  def create() {
    new Boot().boot()
    dropAllData()
    UserData.create()
    SiteData.create()
  }

  def dropAllData() {
    // TODO: delete the H2 file
  }
}