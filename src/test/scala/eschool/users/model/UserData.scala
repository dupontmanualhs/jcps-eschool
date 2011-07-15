package eschool.users.model

import eschool.utils.record.Gender
import net.liftweb.common.Full

class TestUser(
    val username: String,
    val first: String,
    val middle: String,
    val last: String,
    val preferred: String,
    val isMale: Boolean,
    val email: String,
    val password: String,
    val guid: String) {

  def create(): User = {
    val dbUser: User = User.createRecord
    dbUser.username(username)
    dbUser.first(first)
    dbUser.middle(middle)
    dbUser.last(last)
    dbUser.preferred(preferred)
    dbUser.gender(if (isMale) Gender.Male else Gender.Female)
    dbUser.email(email)
    dbUser.password.setPassword(password)
    dbUser.guid(guid)
    dbUser.save(true)
    return dbUser
  }

}


object UserData {
  def create() {
    val users = List[TestUser](
        new TestUser("rsmith1", "Robert", "John", "Smith", "Bob", true, "bob@jcpsky.net", "robert1", "G1"),
        new TestUser("mjones02", "Mary", "Allison", "Jones", null, false, "mary@stu.jcpsky.net", "mary1", "G2"),
        new TestUser("sjohnson03", "Stephen", "Eric", "Johnson", "Steve", true, "steve@stu.jcpsky.net", "steve1", "G3")
    )
    users.foreach(_.create())
  }
}