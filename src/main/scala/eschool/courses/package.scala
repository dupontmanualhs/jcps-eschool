package eschool

import eschool.users.model.{IStudent, ITeacher}
import eschool.users.model.jdo.{Student, Teacher}
import eschool.utils.Helpers._
import net.liftweb.sitemap.LocPath._
import net.liftweb.sitemap.Loc._
import net.liftweb.sitemap.Loc.LinkText._
import net.liftweb.sitemap.{*, Menu, ConvertableToMenu}

package object courses {
  def menus: Array[ConvertableToMenu] = Array(
    Menu.param[Teacher]("Teacher Schedule", "Teacher Schedule",
        ITeacher.getByUsername _, _.getUser.getUsername) / "courses" / "teacher" / * >>
        Template(() => getTemplate(List("courses", "teacherSchedule"))),
    Menu.param[Student]("Student Schedule", "Student Schedule",
        IStudent.getByUsername _, _.getUser.getUsername) / "courses" / "student" / * >>
        Template(() => getTemplate(List("courses", "studentSchedule")))
  )
}