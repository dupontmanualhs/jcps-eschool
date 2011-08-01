package eschool.sites.snippet

import net.liftweb.http.LiftScreen
import eschool.users.model.User

object CreateSite extends LiftScreen {
  val user = User.getCurrentOrRedirect()
  val name = field("Site Name", "")
  val path = field("Path: sites/%s/".format(user.username.get), "")

  def finish() {

  }

}