package eschool

import users.model.{User, UserUtil}
import net.liftweb.sitemap.{ConvertableToMenu, Menu, Loc}
import net.liftweb.sitemap.Loc._

package object users {
  def menus: Array[ConvertableToMenu] = Array(
    Menu("Login") / "users" / "login" >> Unless(() => UserUtil.loggedIn_?, "You are already logged in."),
    Menu("Settings") / "users" / "settings" >> If(() => UserUtil.loggedIn_?, "Log in to access settings."),
    Menu("Logout") / "users" / "logout" >> Hidden >> If(() => UserUtil.loggedIn_?, "You can't logout if you aren't logged in."),
    Menu("List Users") / "users" / "list" >> Hidden,
    Menu("Change Password") / "users" / "changePassword" >> Hidden,
    Menu("Contact") / "users" / "contact"
  )
}