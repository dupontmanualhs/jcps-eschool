package eschool.courses.model

import javax.jdo.annotations._
import jdo.Id
import bootstrap.liftweb.DataStore
import jdo.QId
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

@PersistenceCapable
class Room extends Id[Long] {
  @Unique
  private[this] var _name: String = _
  
  def this(name: String) = {
    this()
    _name = name
  }

  def name: String = _name
  def name_=(theName: String) { _name = theName }
}

object Room {
  def getOrCreate(name: String): Room = {
    val cand = QRoom.candidate
    DataStore.pm.query[Room].filter(cand.name.eq(name)).executeOption() match {
      case Some(room) => room
      case None => {
        val room = new Room(name)
        DataStore.pm.makePersistent(room)
      }
    }
  }
}

trait QRoom extends QId[Long, Room] {
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
}

object QRoom {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QRoom = {
    new PersistableExpressionImpl[Room](parent, name) with QId[Long, Room] with QRoom
  }
  
  def apply(cls: Class[Room], name: String, exprType: ExpressionType): QRoom = {
    new PersistableExpressionImpl[Room](cls, name, exprType) with QId[Long, Room] with QRoom
  }
  
  private[this] lazy val jdoCandidate: QRoom = candidate("this")
  
  def candidate(name: String): QRoom = QRoom(null, name, 5)
  
  def candidate: QRoom = jdoCandidate
  
  def parameter(name: String): QRoom = QRoom(classOf[Room], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QRoom = QRoom(classOf[Room], name, ExpressionType.VARIABLE)
}
