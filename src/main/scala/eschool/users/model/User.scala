package eschool.users.model

object User {
  private object currentId extends SessionVar[Box[Long]](Empty)
  private object currentUser extends RequestVar[Box[User]](currentId.get.flatMap(User.find(_)))

  def getByUsername(username: String): Box[User] = {
    User where (_.username eqs username) get()
  }

  def authenticate(username: String, password: String): Box[User] = {
    val usersByName = User.findAll("username" -> username)
    usersByName match {
      case user :: Nil => authenticate(user, password)
      case _ => Empty // maybe more than one user, which shouldn't happen
    }
  }

  def authenticate(user: User, password: String): Box[User] = {
    if (user.password.isMatch(password)) {
      Full(user)
    } else {
      Empty
    }
  }

  def loggedIn_? = current.isDefined

  def getCurrent: Box[User] = current.get.flatMap(User.find(_)) // TODO: fix when bug fixed

  def getCurrentOrRedirect(): User = getCurrent openOr {
    S.notice("You must login to access that page.")
    S.redirectTo("/users/login")
  }
}