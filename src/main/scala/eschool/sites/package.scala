package eschool

import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap.{ConvertableToMenu, Menu}
import net.liftweb.sitemap.Loc.Unless._
import eschool.users.model.User

package object sites {
  def menus: Array[ConvertableToMenu] = Array(
    Menu.i("Sites") / "sites" / "index" >> Hidden >>
        Unless(() => User.loggedIn_?, "You must be logged in to use the Sites system.") submenus(
      Menu.i("Page") / "sites" / "page" >> Hidden
    )
  )

}