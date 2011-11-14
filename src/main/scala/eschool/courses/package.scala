package eschool

import users.model.{Student, Teacher}
import utils.Helpers._
import net.liftweb.sitemap.LocPath._
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.{*, Menu, ConvertableToMenu}

package object courses {
  def menus: Array[ConvertableToMenu] = Array(
    Menu.param[Teacher]("Teacher Schedule", "Teacher Schedule",
        Teacher.getByUsername _, _.user.username) / "courses" / "teacher" / * >>
        Template(() => getTemplate(List("courses", "teacherSchedule"))),
    Menu.param[Student]("Student Schedule", "Student Schedule",
        Student.getByUsername _, _.user.username) / "courses" / "student" / * >>
        Template(() => getTemplate(List("courses", "studentSchedule")))
  )
}