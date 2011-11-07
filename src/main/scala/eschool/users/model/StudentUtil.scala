package eschool.users.model
import net.liftweb.common._
import bootstrap.liftweb.DataStore

object StudentUtil {
  def getByUsername(username: String): Box[Student] = {
    UserUtil.getByUsername(username) match {
      case Full(user) => {
        val cand = QStudent.candidate
        DataStore.pm.query[Student].filter(cand.user.eq(user)).executeOption
      }
      case _ => Empty
    }
  }

}