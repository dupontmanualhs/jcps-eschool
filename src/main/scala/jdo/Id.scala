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

/*
trait QClass[PC] extends PersistableExpressionImpl[PC] with PersistableExpression[PC] {
  
}

trait QClassCompanion[PC, T <: QClass[PC]] {
  def persistentSupertype[T2](typ: Class[T2]): Class[_] = {
    typ.getSuperclass match {
      case null => null
      case superType: PersistenceCapable => superType
      case superType => persistentSupertype(superType)
    }
  }
  
  def apply(parent: PersistableExpression[PC], name: String, depth: Int): T = {
    new PersistableExpressionImpl[PC](parent, name) with T
  }
}
*/

trait QId[ID] extends PersistableExpressionImpl[Id[ID]] with PersistableExpression[Id[ID]] {
  lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  
  def id: NumericExpression[Long] = _id
  
}

object QId {
  def apply[ID, PC <: Id[ID]](parent: PersistableExpression[PC], name: String, depth: Int): QId[ID] = {
    new PersistableExpressionImpl[PC](parent, name) with QId[ID]
  }
  
  def apply[ID, PC <: Id[ID]](cls: Class[PC], name: String, exprType: ExpressionType): QId[ID] = {
    new PersistableExpressionImpl[PC](cls, name, exprType) with QId[ID]
  }
  
  def jdoCandidate[ID]: QId[ID] = candidate("this")
  
  def candidate[ID, PC <: Id[ID]](name: String): QId[ID] = QId[ID, PC](null, name, 5)
  
  def candidate[ID](): QId[ID] = jdoCandidate[ID]
  
  def parameter[ID](name: String): QId[ID] = QId[ID, Id[ID]](classOf[Id[ID]], name, ExpressionType.PARAMETER)
  
  def variable[ID](name: String): QId[ID] = QId[ID, Id[ID]](classOf[Id[ID]], name, ExpressionType.VARIABLE)
  
}