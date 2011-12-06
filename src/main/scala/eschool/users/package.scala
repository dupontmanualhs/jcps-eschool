package eschool

import users.model.User
import net.liftweb.sitemap.{ConvertableToMenu, Menu, Loc}
import net.liftweb.sitemap.Loc._

package object users {
  def menus: Array[ConvertableToMenu] = Array(
    Menu("users.login", "Login") / "users" / "login" >> Unless(() => User.loggedIn_?, "You are already logged in."),
    Menu("users.settings", "Settings") / "users" / "settings" >> If(() => User.loggedIn_?, "Log in to access settings."),
    Menu("users.logout", "Logout") / "users" / "logout" >> Hidden >> If(() => User.loggedIn_?, "You can't logout if you aren't logged in."),
    Menu("users.list", "List Users") / "users" / "list" >> Hidden,
    Menu("users.change_password", "Change Password") / "users" / "changePassword" >> Hidden,
    Menu("users.contact", "Contact") / "users" / "contact"
  )
}