package eschool.sites.model

import eschool.users.model.User

import net.liftweb.mapper._
import eschool.utils.model.MappedNodeSeq

class Page extends LongKeyedMapper[Page] with IdPK {
  def getSingleton = Page

  object site extends MappedLongForeignKey(this, Site)
  object ident extends MappedString(this, 10) //TODO: validate to make sure legal in URL
  object title extends MappedString(this, 80)
  object content extends MappedNodeSeq(this)
  object parent extends MappedLongForeignKey(this, Page)
  //TODO: need some kind of permissions object to say who can see what
}

object Page extends Page with LongKeyedMetaMapper[Page] {
  override def dbTableName = "pages"
}