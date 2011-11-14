package eschool.users.model

import javax.jdo.annotations._

import jdo.Id

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
class Perspective extends Id[Long] {
  private[this] var _user: User = _
  
  protected def this(user: User) = {
    this()
    user_=(user)
  }
  
  def user: User = _user
  def user_=(theUser: User) { _user = theUser }
}