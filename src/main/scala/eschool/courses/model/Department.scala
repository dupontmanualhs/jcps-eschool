package eschool.courses.model

import javax.jdo.annotations._
import bootstrap.liftweb.DataStore
import jdo.Id
import jdo.QId
import org.datanucleus.api.jdo.query._
import org.datanucleus.query.typesafe._

@PersistenceCapable
class Department extends Id[Long] {
  private[this] var _name: String = _

  def this(name: String) = {
    this()
    _name = name
  }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
}

object Department {
  def getOrCreate(name: String): Department = {
    val cand = QDepartment.candidate
    DataStore.pm.query[Department].filter(cand.name.eq(name)).executeOption() match {
      case Some(dept) => dept
      case None => {
        val dept = new Department(name)
        DataStore.pm.makePersistent(dept)
      }
    }
  }
}

trait QDepartment extends QId[Long, Department] {
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
}

object QDepartment {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QDepartment = {
    new PersistableExpressionImpl[Department](parent, name) with QId[Long, Department] with QDepartment
  }
  
  def apply(cls: Class[Department], name: String, exprType: ExpressionType): QDepartment = {
    new PersistableExpressionImpl[Department](cls, name, exprType) with QId[Long, Department] with QDepartment
  }
  
  private[this] lazy val jdoCandidate: QDepartment = candidate("this")
  
  def candidate(name: String): QDepartment = QDepartment(null, name, 5)
  
  def candidate(): QDepartment = jdoCandidate
  
  def parameter(name: String): QDepartment = QDepartment(classOf[Department], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QDepartment = QDepartment(classOf[Department], name, ExpressionType.VARIABLE)
}
