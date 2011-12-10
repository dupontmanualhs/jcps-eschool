package eschool.assignments.model

import javax.jdo.annotations._
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable(detachable="true")
class Source {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Unique
  @Column(allowsNull="false")
  private[this] var _name: String = _
  @Column(allowsNull="true")
  private[this] var _parent: Source = _
  
  def id: Long = _id
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def parent: Option[Source] = { if (_parent == null) None else Some(_parent) }
  def parent_=(theParent: Option[Source]) { _parent = theParent.getOrElse(null) }
}

trait QSource extends PersistableExpression[Source] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _parent: ObjectExpression[Source] = new ObjectExpressionImpl[Source](this, "_parent")
  def parent: ObjectExpression[Source] = _parent
}

object QSource {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QSource = {
    new PersistableExpressionImpl[Source](parent, name) with QSource
  }
  
  def apply(cls: Class[Source], name: String, exprType: ExpressionType): QSource = {
    new PersistableExpressionImpl[Source](cls, name, exprType) with QSource
  }
  
  private[this] lazy val jdoCandidate: QSource = candidate("this")
  
  def candidate(name: String): QSource = QSource(null, name, 5)
  
  def candidate(): QSource = jdoCandidate
  
  def parameter(name: String): QSource = QSource(classOf[Source], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QSource = QSource(classOf[Source], name, ExpressionType.VARIABLE)
}