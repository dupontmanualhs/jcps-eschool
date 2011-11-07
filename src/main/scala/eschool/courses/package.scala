package eschool

import users.model.{Student, StudentUtil, Teacher, TeacherUtil}
import utils.Helpers._
import net.liftweb.sitemap.LocPath._
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.{*, Menu, ConvertableToMenu}

package object courses {
  def menus: Array[ConvertableToMenu] = Array(
    Menu.param[Teacher]("Teacher Schedule", "Teacher Schedule",
        TeacherUtil.getByUsername _, _.getUser.getUsername) / "courses" / "teacher" / * >>
        Template(() => getTemplate(List("courses", "teacherSchedule"))),
    Menu.param[Student]("Student Schedule", "Student Schedule",
        StudentUtil.getByUsername _, _.getUser.getUsername) / "courses" / "student" / * >>
        Template(() => getTemplate(List("courses", "studentSchedule")))
  )
}