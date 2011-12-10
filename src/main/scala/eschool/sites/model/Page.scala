package eschool.sites.model

import javax.jdo.annotations._
import scala.xml.{NodeSeq, XML}
import scala.collection.JavaConverters._
import net.liftweb.common._
import bootstrap.liftweb.DataStore
import eschool.utils.Helpers.string2nodeSeq
import org.datanucleus.query.typesafe._
import org.datanucleus.api.jdo.query._

@PersistenceCapable
@Uniques(Array(
  new Unique(members=Array("parentSite", "ident")), 
  new Unique(members=Array("parentPage", "ident"))))
class Page {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private[this] var _id: Long = _
  private[this] var _parentSite: Site = _
  private[this] var _parentPage: Page = _
  private[this] var _ident: String = _
  private[this] var _name: String = _
  private[this] var _content: String = _
  @Join
  private[this] var _children: java.util.List[Page] = _
  
  def this(name: String) = {
    this()
    _name = name
  }

  def id: Long = _id

  protected[model] def parentSite: Option[Site] = if (_parentSite == null) Empty else Full(_parentSite)
  protected[model] def parentSite_=(maybeSite: Option[Site]) { 
    if (maybeSite.isEmpty) _parentSite = null
    else _parentSite = maybeSite.get 
  }
  protected[model] def parentSite_=(site: Site) { _parentSite = site }
  
  protected[model] def parentPage: Option[Page] = if (_parentPage == null) Empty else Full(_parentPage)
  protected[model] def parentPage_=(maybePage: Option[Page]) { 
    if (maybePage.isEmpty) _parentPage = null
    else _parentPage = maybePage.get
  }
  protected[model] def parentPage_=(page: Page) { _parentPage = page }
  
  def parent: Either[Site, Page] = {
    if (parentSite.isDefined) Left(parentSite.get)
    else if (parentPage.isDefined) Right(parentPage.get)
    else throw new Exception("Page with id %s does not have a parent!".format(id))
  }
  
  def ident: String = _ident
  def ident_=(theIdent: String) { _ident = theIdent }
  
  def name: String = _name
  def name_=(theName: String) { _name = theName }
  
  def content: NodeSeq = string2nodeSeq(_content)
  def content_=(html: NodeSeq) { _content = html.toString }
  def content_=(htmlString: String) { content_=(string2nodeSeq(htmlString)) }
  
  def children: List[Page] = {
    if (_children == null) List[Page]()
    else _children.asScala.toList
  }
  def children_=(theChildren: List[Page]) {
    theChildren foreach ((page: Page) => {
      page.parentPage = this
    })
    _children = theChildren.asJava
  }
  
  def path(suffix: List[String] = Nil): List[String] = {
    parent match {
      case Left(site) => site.owner.username :: site.ident :: ident :: suffix
      case Right(page) => page.path(ident :: suffix)
    }
  }  
}

object Page {
  /**
   * Produces the path to page (including the username and site-ident)
   */

  def fromSiteAndPath(site: Site, path: List[String]): Box[Page] = path match {
    case Nil => Failure("How did I get here without a page path?")
    case topPageIdent :: restOfPath => {
      def followPath(current: Option[Page], path: List[String]): Box[Page] = current match {
        case Some(page) => path match {
          case Nil => Full(page)
          case next :: rest => followPath(page.children.find(_.ident == next), rest)
        }
        case _ => Failure("There is no page with the given path.")
      }
      val topPage: Option[Page] = site.children.find(_.ident == topPageIdent)
      followPath(topPage, restOfPath)
    }
  }

  private def errorIfSome(badPage: Option[Page], problem: String): Box[String] = {
    badPage match {
      case Some(page) => Full("There is already a page with that %s.".format(problem))
      case None => Empty
    }
  }

  /**
   * validates a possible identifier for a page to make sure a page
   *   with the given identifier doesn't already exist as a child of
   *   the given Site
   * returns Nil if okay, and an appropriate error, if not
   */
  def uniqueIdent(parent: Either[Site, Page], possIdent: String): Box[String] = {
    println(parent.toString + " " + possIdent)
    val cand = QPage.candidate
    parent match {
      case Left(parentSite: Site) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentSite.eq(parentSite)).filter(cand.ident.eq(possIdent)).executeOption()
        errorIfSome(possPage, "identifier")
      }
      case Right(parentPage: Page) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentPage.eq(parentPage)).filter(cand.ident.eq(possIdent)).executeOption()
        errorIfSome(possPage, "identifier")
      }
    }
  }

  /**
   * validates a possible name for a page to make sure a page
   *   with the given name doesn't already exist as a child of
   *   the given Site
   * returns Nil if okay, and an appropriate error, if not
   */
  def uniqueName(parent: Either[Site, Page], possName: String): Box[String] = {
    val cand = QPage.candidate
    parent match {
      case Left(parentSite: Site) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentSite.eq(parentSite)).filter(cand.name.eq(possName)).executeOption()
        errorIfSome(possPage, "name")
      }
      case Right(parentPage: Page) => {
        val possPage: Option[Page] = DataStore.pm.query[Page].filter(cand.parentPage.eq(parentPage)).filter(cand.name.eq(possName)).executeOption()
        errorIfSome(possPage, "name")
      }
    }
  }
}

trait QPage extends PersistableExpression[Page] {
  private[this] lazy val _id: NumericExpression[Long] = new NumericExpressionImpl[Long](this, "_id")
  def id: NumericExpression[Long] = _id

  private[this] lazy val _parentSite: ObjectExpression[Site] = new ObjectExpressionImpl[Site](this, "_parentSite")
  def parentSite: ObjectExpression[Site] = _parentSite
  
  private[this] lazy val _parentPage: ObjectExpression[Page] = new ObjectExpressionImpl[Page](this, "_parentPage")
  def parentPage: ObjectExpression[Page] = _parentPage
  
  private[this] lazy val _ident: StringExpression = new StringExpressionImpl(this, "_ident")
  def ident: StringExpression = _ident
  
  private[this] lazy val _name: StringExpression = new StringExpressionImpl(this, "_name")
  def name: StringExpression = _name
  
  private[this] lazy val _content: StringExpression = new StringExpressionImpl(this, "_content")
  def content: StringExpression = _content

  private[this] lazy val _children: ListExpression[java.util.List[Page], Page] =
      new ListExpressionImpl[java.util.List[Page], Page](this, "_children")
  def children: ListExpression[java.util.List[Page], Page] = _children
}

object QPage {
  def apply(parent: PersistableExpression[_], name: String, depth: Int): QPage = {
    new PersistableExpressionImpl[Page](parent, name) with QPage
  }
  
  def apply(cls: Class[Page], name: String, exprType: ExpressionType): QPage = {
    new PersistableExpressionImpl[Page](cls, name, exprType) with QPage
  }
  
  private[this] lazy val jdoCandidate: QPage = candidate("this")
  
  def candidate(name: String): QPage = QPage(null, name, 5)
  
  def candidate(): QPage = jdoCandidate
  
  def parameter(name: String): QPage = QPage(classOf[Page], name, ExpressionType.PARAMETER)
  
  def variable(name: String): QPage = QPage(classOf[Page], name, ExpressionType.VARIABLE)
}