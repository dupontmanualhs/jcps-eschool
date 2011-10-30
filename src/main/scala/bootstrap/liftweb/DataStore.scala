package bootstrap.liftweb
import javax.jdo.JDOHelper
import net.liftweb.http.RequestVar
import org.datanucleus.api.jdo.JDOPersistenceManager
import org.datanucleus.query.typesafe.{BooleanExpression, TypesafeQuery}
import net.liftweb.common._
import net.liftweb.http.LiftSession
import javax.jdo.Transaction
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory
import javax.jdo.PersistenceManagerFactory
import org.datanucleus.api.jdo.query.JDOTypesafeQuery
import javax.jdo.PersistenceManager

object DataStore {
  val pmf: JDOPersistenceManagerFactory = 
		  JDOHelper.getPersistenceManagerFactory("props/datastore.props").asInstanceOf[JDOPersistenceManagerFactory]
  object pmVar extends RequestVar[ScalaPersistenceManager](ScalaPersistenceManager.create(pmf)) {
    registerCleanupFunc(ignore => this.get.commitTransactionAndClose())
  }
  
  def pm: ScalaPersistenceManager = {
    pmVar.get
  }
}

class ScalaPersistenceManager(val jpm: JDOPersistenceManager) {
  def beginTransaction() {
    jpm.currentTransaction.begin()
  }

  def commitTransactionAndClose() {
    try {
      jpm.currentTransaction.commit()
    } finally {
      if (jpm.currentTransaction.isActive) {
        jpm.currentTransaction.rollback()
      }
      jpm.close()
    }
  }
  
  def close() {
    jpm.close()
  }
  
  def makePersistent(dataObj: Object) = { // TODO: can this be PersistenceCapable
    jpm.makePersistent(dataObj)
  }
  
  def query[T: ClassManifest](): ScalaQuery[T] = ScalaQuery[T](jpm)
}

object ScalaPersistenceManager {
  def create(pmf: JDOPersistenceManagerFactory): ScalaPersistenceManager = {
    val spm = new ScalaPersistenceManager(pmf.getPersistenceManager().asInstanceOf[JDOPersistenceManager])
    spm.beginTransaction()
    spm
  }
}

class ScalaQuery[T](val query: TypesafeQuery[T]) {   
  def executeOption(): Option[T] = {
    executeList() match {
      case List(obj) => Some(obj)
      case _ => None
    }
  }
  
  def executeList(): List[T] = {
    import scala.collection.JavaConverters._
    query.executeList().asScala.toList
  }
  
  def filter(expr: BooleanExpression): ScalaQuery[T] = {
    ScalaQuery[T](query.filter(expr))
  }
}

object ScalaQuery {
  def apply[T: ClassManifest](jpm: JDOPersistenceManager): ScalaQuery[T] = {
    new ScalaQuery[T](jpm.newTypesafeQuery[T](classManifest[T].erasure))
  }
  
  def apply[T](query: TypesafeQuery[T]): ScalaQuery[T] = {
    new ScalaQuery[T](query)
  }
}