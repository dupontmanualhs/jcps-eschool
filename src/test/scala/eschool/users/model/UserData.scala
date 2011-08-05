package eschool.users.model

import eschool.utils.record.Gender

class DbUser(
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
    val users = List[DbUser](
        new DbUser("madams4", "Michael", null, "Adams", "Mike", true, "mike@jcpsky.net", "mike4", "G4"),
        new DbUser("rsmith1", "Robert", "John", "Smith", "Bob", true, "bob@jcpsky.net", "robert1", "G1"),
        new DbUser("mjones02", "Mary", "Allison", "Jones", null, false, "mary@stu.jcpsky.net", "mary1", "G2"),
        new DbUser("sjohnson03", "Stephen", "Eric", "Johnson", "Steve", true, "steve@stu.jcpsky.net", "steve1", "G3")
    )
    users.foreach(_.create())
    val s1 = Student.createRecord.user(User.getByUsername("mjones02").open_!.id.get).grade(10)
    s1.save(true)
    val s2 = Student.createRecord.user(User.getByUsername("sjohnson03").open_!.id.get).grade(12)
    s2.save(true)
    val t1 = Teacher.createRecord.user(User.getByUsername("rsmith1").open_!.id.get)
    t1.save(true)
    val t2 = Teacher.createRecord.user(User.getByUsername("madams4").open_!.id.get)
    t2.save(true)
    val p1 = Guardian.createRecord.user(User.getByUsername("madams4").open_!.id.get).children(
      List(s1.id.get))
    p1.save(true)
  }
}