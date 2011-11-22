package eschool

import users.model.IUser
import users.model.jdo.User
import net.liftweb.sitemap.{ConvertableToMenu, Menu, Loc}
import net.liftweb.sitemap.Loc._

package object users {
  def menus: Array[ConvertableToMenu] = Array(
    Menu("Login") / "users" / "login" >> Unless(() => IUser.loggedIn_?, "You are already logged in."),
    Menu("Settings") / "users" / "settings" >> If(() => IUser.loggedIn_?, "Log in to access settings."),
    Menu("Logout") / "users" / "logout" >> Hidden >> If(() => IUser.loggedIn_?, "You can't logout if you aren't logged in."),
    Menu("List Users") / "users" / "list" >> Hidden,
    Menu("Change Password") / "users" / "changePassword" >> Hidden,
    Menu("Contact") / "users" / "contact"
  )
}