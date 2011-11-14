package eschool.users.model

import jdohelpers.Gender
import bootstrap.liftweb.DataStore

object UserData {
  def create() {
    val mike = new User("madams4", "Michael", None, "Adams", Some("Mike"), Gender.MALE, "mike@jcpsky.net", "mike4")
    val bob = new User("rsmith1", "Robert", Some("John"), "Smith", Some("Bob"), Gender.MALE, "bob@jcpsky.net", "robert1")
    val mary = new User("mjones02", "Mary", Some("Allison"), "Jones", None, Gender.FEMALE, "mary@stu.jcpsky.net", "mary1")
    val steve = new User("sjohnson03", "Stephen", Some("Eric"), "Johnson", Some("Steve"), Gender.MALE, "steve@stu.jcpsky.net", "steve1")
    DataStore.pm.makePersistent(mike)
    DataStore.pm.makePersistent(bob)
    DataStore.pm.makePersistent(mary)
    DataStore.pm.makePersistent(steve)
    DataStore.pm.commitTransaction()
  }
}