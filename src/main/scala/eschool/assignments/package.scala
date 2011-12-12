package eschool

import users.model.{Student, Teacher}
import utils.Helpers._
import net.liftweb.sitemap.LocPath._
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.{*, Menu, ConvertableToMenu}

package object assignments {
  def menus: Array[ConvertableToMenu] = Array(
    Menu.param[Teacher]("courses.create_question", "Create Question",
        Teacher.getByUsername _, _.user.username) / "assignments" / "createQuestion" / * >>
        Template(() => getTemplate(List("assignments", "createQuestion")))
  )
}