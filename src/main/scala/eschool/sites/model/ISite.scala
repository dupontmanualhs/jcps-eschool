package eschool.sites.model

import scala.collection.JavaConversions._
import javax.jdo.annotations._
import eschool.users.model.jdo.User
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

@PersistenceCapable
@Uniques({"owner", "name"}, {"owner", "ident"})
public class Site {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _owner: User = _
  private[this] var _name: String = _
  private[this] var _ident: String = _
  @Join
  private[this] var _children: java.util.List[Page] = _
  
  def this(owner: User, name: String, ident: String) = {
    this()
    owner_=(owner)
    name_=(name)
    ident_=(ident)
  }

  def id: Long = _id

  def owner: User = _owner
  def owner_=(theOwner: User) { _owner = theOwner }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def ident: String = _ident
  def ident_=(theIdent: String) { _ident = theIdent }
  
  def children: List[Page] = {
    if (_children == null) List[Page]()
    else _children.asScala.toList
  }
  def children_=(theChildren: List[Page]) {
    theChildren foreach ((page: Page) => {
      page.parentSite = this
    })
    _children = theChildren.asJava
  } 
}

trait QSite extends PersistableExpression[Site] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _owner: ObjectExpression[User] = new ObjectExpressionImpl[User](this, "_owner")
  def owner: ObjectExpression[User] = _owner
  
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _ident: StringExpression = new StringExpressionImpl(this, "_ident")
  def ident: StringExpression = _ident
  
  private[this] lazy val _children: ListExpression[java.util.List[Page], Page] =
      new ListExpressionImpl[java.util.List[Page], Page](this, "_children")
  def children: ListExpression[java.util.List[Page], Page] = _children
  
}

object QSite {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QSite = {
    new PersistableExpressionImpl[Site](parent, name) with QSite
  }
  
  def apply(cls: Class[Site], name: String, exprType: ExpressionType): QSite = {
    new PersistableExpressionImpl[Site](cls, name, exprType) with QSite
  }
  
  private[this] lazy val jdoCandidate: QSite = candidate("this")
  
  def candidate(name: String): QSite = QSite(null, name, 5)
  
  def candidate(): QSite = jdoCandidate
  
  def parameter(name: String): QSite = QSite(classOf[Site], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QSite = QSite(classOf[Site], name, ExpressionType.VARIABLE)
}