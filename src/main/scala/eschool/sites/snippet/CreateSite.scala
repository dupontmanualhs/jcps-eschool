package eschool.sites.snippet

import net.liftweb.json.JsonDSL._
import eschool.users.model.User
import net.liftweb.util.FieldError
import eschool.sites.model.Site
import collection.immutable.ListMap
import net.liftweb.http.{S, LiftScreen}
import org.bson.types.ObjectId
import com.foursquare.rogue.Rogue._
import xml.Text
import net.liftweb.mongodb.record.field._

object CreateSite extends LiftScreen {
  object user extends ScreenVar[User](User.getCurrentOrRedirect())
  val name = text("Site Name", "",
      valMinLen(1, "Name must be at least one character."),
      valMaxLen(80, "Name must be no more than 80 characters."),
      uniqueName _)
  val path = text("Path: sites/%s/".format(user.username.get), "",
      valMinLen(1, "Path segment must be at least one character."),
      valMaxLen(10, "Path segment must be no more than ten characters."),
      uniqueIdent _)

  def uniqueName(s: String): List[FieldError] = {
    Site where (_.owner eqs user.get.id.get) and (_.name eqs s) fetch() match {
      case Nil => Nil
      case _ => Text("You already have a site with that name.")
    }
  }

  def uniqueIdent(s: String): List[FieldError] = {
    Site where (_.owner eqs user.get.id.get) and (_.ident eqs s) fetch() match {
      case Nil => Nil
      case _ => Text("You already have a site at that path.")
    }
  }

  //override def validations =  uniqueName _ :: uniqueIdent _ :: super.validations

  def finish() {
    val newSite = Site.createRecord.owner(user.get.id.get).name(name.get)
        .ident(path.get).pages(ListMap[String, ObjectId]())
    newSite.save(true)
    S.redirectTo("/sites")
  }
}