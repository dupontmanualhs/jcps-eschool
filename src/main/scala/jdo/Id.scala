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

trait QId[T] extends PersistableExpressionImpl[Id[T]] with PersistableExpression[Id[T]] {
  lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  
  def id: NumericExpression[Long] = _id
  
}

object QId {
  def apply[ID](parent: PersistableExpression[_ <: Id[ID]], name: String, depth: Int): QId[ID] = {
    new PersistableExpressionImpl[Id[ID]](parent, name) with QId[ID]
  }
  
  def apply[PC, ID](cls: (PC <: Class[QId[ID]]), name: String, exprType: ExpressionType): Q = {
    new PersistableExpressionImpl[Q](cls, name, exprType) with QId[I]
  }
  
  def jdoCandidate[T]: QId[T] = candidate("this")
  
  def candidate[T](name: String): QId[T] = QId[T](null, name, 5)
  
  def candidate[T](): QId[T] = jdoCandidate[T]
  
  def parameter[T](name: String): QId[T] = QId[T](classOf[QId[T]], name, ExpressionType.PARAMETER)
  
  def variable[T](name: String): QId[T] = QId[T](classOf[QId[T]], name, ExpressionType.VARIABLE)
  
}