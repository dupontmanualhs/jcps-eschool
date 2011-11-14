package eschool.users.model

import scala.collection.JavaConverters._

import javax.jdo.annotations._

@PersistenceCapable
class Guardian extends Perspective {
  private[this] var _children: java.util.Set[Student] = _
  
  def children: Set[Student] = _children.asScala.toSet
  def children_=(theChildren: Set[Student]) { _children = theChildren.asJava }
}