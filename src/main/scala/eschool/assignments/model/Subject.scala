package eschool.assignments.model

import javax.jdo.annotations._
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

@PersistenceCapable(detachable="true")
class Subject {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  @Unique
  @Column(allowsNull="false")
  private[this] var _name: String = _
  @Column(allowsNull="true")
  private[this] var _parent: Subject = _
  
  def id: Long = _id
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def parent: Option[Subject] = { if (_parent == null) None else Some(_parent) }
  def parent_=(theParent: Option[Subject]) { _parent = theParent.getOrElse(null) }
}

trait QSubject extends PersistableExpression[Subject] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _parent: ObjectExpression[Subject] = new ObjectExpressionImpl[Subject](this, "_parent")
  def parent: ObjectExpression[Subject] = _parent
}

object QSubject {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QSubject = {
    new PersistableExpressionImpl[Subject](parent, name) with QSubject
  }
  
  def apply(cls: Class[Subject], name: String, exprType: ExpressionType): QSubject = {
    new PersistableExpressionImpl[Subject](cls, name, exprType) with QSubject
  }
  
  private[this] lazy val jdoCandidate: QSubject = candidate("this")
  
  def candidate(name: String): QSubject = QSubject(null, name, 5)
  
  def candidate(): QSubject = jdoCandidate
  
  def parameter(name: String): QSubject = QSubject(classOf[Subject], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QSubject = QSubject(classOf[Subject], name, ExpressionType.VARIABLE)
}