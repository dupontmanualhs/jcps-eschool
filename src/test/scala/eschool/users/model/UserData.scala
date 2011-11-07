package eschool.users.model

import jdohelpers.Gender
import bootstrap.liftweb.DataStore

object UserData {
  def create() {
    val mike = new User("madams4", "Michael", null, "Adams", "Mike", Gender.MALE, "mike@jcpsky.net", "mike4")
    val bob = new User("rsmith1", "Robert", "John", "Smith", "Bob", Gender.MALE, "bob@jcpsky.net", "robert1")
    val mary = new User("mjones02", "Mary", "Allison", "Jones", null, Gender.FEMALE, "mary@stu.jcpsky.net", "mary1")
    val steve = new User("sjohnson03", "Stephen", "Eric", "Johnson", "Steve", Gender.MALE, "steve@stu.jcpsky.net", "steve1")
    DataStore.pm.makePersistent(mike)
    DataStore.pm.makePersistent(bob)
    DataStore.pm.makePersistent(mary)
    DataStore.pm.makePersistent(steve)
  }
}