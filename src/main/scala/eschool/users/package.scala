package eschool

import users.model.User
import net.liftweb.sitemap.{ConvertableToMenu, Menu, Loc}
import net.liftweb.sitemap.Loc._

package object users {
  def menus: Array[ConvertableToMenu] = Array(
    Menu("Login") / "users" / "login" >> Hidden >> Unless(() => User.loggedIn_?, "You are already logged in."),
    Menu("Settings") / "users" / "settings" >> If(() => User.loggedIn_?, "Log in to access settings."),
    Menu("Logout") / "users" / "logout" >> Hidden >> If(() => User.loggedIn_?, "You can't logout if you aren't logged in."),
    Menu("List Users") / "users" / "list" >> Hidden
  )
}