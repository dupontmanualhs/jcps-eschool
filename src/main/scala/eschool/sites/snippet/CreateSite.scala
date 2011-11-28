package eschool.sites.snippet

import net.liftweb.json.JsonDSL._
import bootstrap.liftweb.DataStore
import eschool.users.model.IUser
import eschool.users.model.jdo.User
import net.liftweb.util.FieldError
import eschool.sites.model.{QSite, Site}
import collection.immutable.ListMap
import net.liftweb.http.{S, LiftScreen}
import xml.Text

object CreateSite extends LiftScreen {
  object user extends ScreenVar[User](IUser.getCurrentOrRedirect())
  val name = text("Site Name", "",
      valMinLen(1, "Name must be at least one character."),
      valMaxLen(80, "Name must be no more than 80 characters."),
      uniqueName _)
  val path = text("Path: sites/%s/".format(user.getUsername), "",
      valMinLen(1, "Path segment must be at least one character."),
      valMaxLen(10, "Path segment must be no more than ten characters."),
      uniqueIdent _)

  def uniqueName(s: String): List[FieldError] = {
    val cand = QSite.candidate
    DataStore.pm.query[Site].filter(cand.owner.eq(user.get).and(cand.name.eq(s))).executeList() match {
      case Nil => Nil
      case _ => Text("You already have a site with that name.")
    }
  }

  def uniqueIdent(s: String): List[FieldError] = {
    val cand = QSite.candidate
    DataStore.pm.query[Site].filter(cand.owner.eq(user.get).and(cand.ident.eq(s))).executeList() match {
      case Nil => Nil
      case _ => Text("You already have a site at that path.")
    }
  }

  //override def validations =  uniqueName _ :: uniqueIdent _ :: super.validations

  def finish() {
    val newSite = new Site(user.get, name.get, path.get)
    DataStore.pm.makePersistent(newSite)
    S.redirectTo("/sites")
  }
}