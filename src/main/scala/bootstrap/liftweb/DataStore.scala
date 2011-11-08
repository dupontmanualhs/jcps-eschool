package bootstrap.liftweb

import scala.collection.JavaConversions._
import javax.jdo.JDOHelper
import org.datanucleus.api.jdo.JDOPersistenceManager
import org.datanucleus.query.typesafe.{BooleanExpression, OrderExpression, TypesafeQuery}
import net.liftweb.common._
import net.liftweb.http.{LiftSession, S}
import javax.jdo.Transaction
import org.datanucleus.api.jdo.JDOPersistenceManagerFactory
import javax.jdo.PersistenceManagerFactory
import org.datanucleus.api.jdo.query.JDOTypesafeQuery
import javax.jdo.PersistenceManager
import javax.jdo.spi.PersistenceCapable
import net.liftweb.http.TransientRequestVar

object DataStore {
  val pmf: JDOPersistenceManagerFactory = 
		  JDOHelper.getPersistenceManagerFactory("props/datastore.props").asInstanceOf[JDOPersistenceManagerFactory]
  val pmOutsideRequest = ScalaPersistenceManager.create(pmf)
  object pmVar extends TransientRequestVar[ScalaPersistenceManager]({
    	println("****creating pm at beginning of request")
    	ScalaPersistenceManager.create(pmf)}) {
    registerGlobalCleanupFunc(ignore => this.get.commitTransactionAndClose())
  }
  
  def pm: ScalaPersistenceManager = {
    S.request match {
      case Full(req) => pmVar.get
      case _ => {
        println("****getting singleton pm")
        pmOutsideRequest
      }
    }
  }
}

class ScalaPersistenceManager(val jpm: JDOPersistenceManager) {
  def beginTransaction() {
    jpm.currentTransaction.begin()
  }
  
  def commitTransaction() {
    try {
      jpm.currentTransaction.commit()
    } finally {
      if (jpm.currentTransaction.isActive) {
        jpm.currentTransaction.rollback()
      }
    }
  }

  def commitTransactionAndClose() {
    try {
      println("****committing transaction")
      jpm.currentTransaction.commit()
    } finally {
      if (jpm.currentTransaction.isActive) {
        jpm.currentTransaction.rollback()
      }
      println("****closing transaction")
      jpm.close()
    }
  }
  
  def close() {
    jpm.close()
  }
  
  def makePersistent[T](dataObj: T): T = { // TODO: can this be PersistenceCapable
    jpm.makePersistent[T](dataObj)
  }
  
  def makePersistentAll[T](dataObjs: Iterable[T]): Iterable[T] = {
    jpm.makePersistentAll[T](dataObjs)
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
  
  def orderBy(orderExpr: OrderExpression[_]*): ScalaQuery[T] = {
    ScalaQuery[T](query.orderBy(orderExpr: _*))
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