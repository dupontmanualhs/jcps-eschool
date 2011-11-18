package eschool.users.model

import javax.jdo.annotations._
import jdo.Id
import jdo.QId
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

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

trait QPerspective[PC <: Perspective] extends QId[Long, PC] {
  private[this] lazy val _user: ObjectExpression[User] = new ObjectExpressionImpl[User](this, "_user")
  def user: ObjectExpression[User] = _user 
}

/*
object QPerspective {
  def apply[PC <: Perspective](parent: PersistableExpression[_], name: String, depth: Int): QPerspective[PC] = {
    new PersistableExpressionImpl[Perspective](parent, name) with QId[Long, PC] with QPerspective[PC]
  }
  
  def apply[PC <: Perspective](cls: Class[Perspective], name: String, exprType: ExpressionType): QPerspective[PC] = {
    new PersistableExpressionImpl[Perspective](cls, name, exprType) with QId[Long, PC] with QPerspective[PC]
  }
  
  private[this] lazy val jdoCandidate = candidate[PC]("this")
  
  def candidate[PC <: Perspective](name: String): QPerspective[PC] = QPerspective(null, name, 5)
  
  def candidate[PC <: Perspective](): QPerspective[PC] = jdoCandidate
  
  def parameter(name: String): QPerspective = QPerspective(classOf[Perspective], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QPerspective = QPerspective(classOf[Perspective], name, ExpressionType.VARIABLE)
}
*/