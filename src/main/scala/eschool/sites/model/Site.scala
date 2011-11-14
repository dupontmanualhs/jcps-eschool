package eschool.sites.model

import scala.collection.JavaConverters._
import javax.jdo.annotations._
import eschool.users.model.User
import jdo.Id

@Uniques(Array(
  new Unique(members=Array("owner", "name")), 
  new Unique(members=Array("owner", "ident"))))
class Site extends Id[Long] {
  private[this] var _owner: User = _
  private[this] var _name: String = _
  private[this] var _ident: String = _
  @Join
  private[this] var _children: java.util.List[Page] = _
  
  def this(owner: User, name: String, ident: String) = {
    this()
    owner_=(owner)
    name_=(name)
    ident_=(ident)
  }

  def owner: User = _owner
  def owner_=(theOwner: User) { _owner = theOwner }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def ident: String = _ident
  def ident_=(theIdent: String) { _ident = theIdent }
  
  def children: List[Page] = {
    if (_children == null) List[Page]()
    else _children.asScala.toList
  }
  def children_=(theChildren: List[Page]) {
    theChildren foreach ((page: Page) => {
      page.parentSite = this
    })
    _children = theChildren.asJava
  } 
}

object Site {
  
}