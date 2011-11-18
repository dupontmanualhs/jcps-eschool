package jdo

import javax.jdo.annotations._

import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
trait Id[T] {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: T = _

  def id: T = _id
}

trait QId[ID, PC <: Id[ID]] extends PersistableExpression[PC] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id
}

/*
object QId {
  def apply[ID, PC <: Id[ID]](parent: PersistableExpression[PC], name: String, depth: Int): QId[ID, PC] = {
    new PersistableExpressionImpl[PC](parent, name) with QId[ID, PC]
  }
  
  def apply[ID, PC <: Id[ID]](cls: Class[PC], name: String, exprType: ExpressionType): QId[ID, PC] = {
    new PersistableExpressionImpl[PC](cls, name, exprType) with QId[ID, PC]
  }
  
  def jdoCandidate[ID, PC <: Id[ID]]: QId[ID, PC] = candidate("this")
  
  def candidate[ID, PC <: Id[ID]](name: String): QId[ID, PC] = QId[ID, PC](null, name, 5)
  
  def candidate[ID, PC <: Id[ID]](): QId[ID, PC] = jdoCandidate[ID, PC]
  
  def parameter[ID, PC <: Id[ID]](name: String): QId[ID, PC] = QId[ID, PC](classOf[_], name, ExpressionType.PARAMETER)
  
  def variable[ID, PC <: Id[ID]](name: String): QId[ID, PC] = QId[ID, PC](classOf[PC], name, ExpressionType.VARIABLE)
  
}
*/