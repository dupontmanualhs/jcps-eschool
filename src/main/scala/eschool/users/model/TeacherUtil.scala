package eschool.users.model
import net.liftweb.common._
import bootstrap.liftweb.DataStore

object TeacherUtil {
  def getByUsername(username: String): Box[Teacher] = {
    UserUtil.getByUsername(username) match {
      case Full(user) => {
        val cand = QTeacher.candidate
        DataStore.pm.query[Teacher].filter(cand.user.eq(user)).executeOption
      }
      case _ => Empty
    }
  }

}