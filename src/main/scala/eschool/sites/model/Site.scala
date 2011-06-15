package eschool.sites.model

import net.liftweb.mapper._
import eschool.users.model.User

class Site extends LongKeyedMapper[Site] with IdPK {
  def getSingleton = Site

  object ident extends MappedString(this, 10) //TODO: validate to make sure legal in URL
  object name extends MappedString(this, 80)
  object owner extends MappedLongForeignKey(this, User)
}

object Site extends Site with LongKeyedMetaMapper[Site] {
  override def dbTableName = "sites"
}