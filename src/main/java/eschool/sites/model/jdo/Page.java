package eschool.sites.model.jdo;

import scala.Option;
import scala.Some;
import scala.Either;
import scala.xml.NodeSeq;

import scala.collection.immutable.List;
import scala.collection.JavaConversions;

import javax.jdo.annotations.*;

import eschool.utils.Helpers;

@PersistenceCapable
public class Page {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private long id;
  private Site parentSite;
  private Page parentPage;
  private String ident;
  private String name;
  private String content;
  @Join
  private java.util.List<Page> children;
  
  private Page() { }
  
  public Page(String name) {
    setName(name);
  }

  public long getId() {
	  return this.id;
  }

  Option<Site> getParentSite() {
	  if (this.parentSite == null) {
		  return Option.empty();
	  }else {
		  return new Some<Site>(this.parentSite);
	  }
  }
  protected void setParentSite(Option<Site> maybeSite) {
	  this.parentSite = maybeSite.isDefined() ? maybeSite.get() : null;
  }
  protected void setParentSite(Site site) {
	  this.parentSite = site;
  }
  
  Option<Page> getParentPage() {
	  if (this.parentPage ==  null) {
		  return Option.empty();
	  } else {
		  return new Some<Page>(this.parentPage);
	  }
  }
  protected void setParentPage(Option<Page> maybePage) {
	  this.parentPage = maybePage.isDefined() ? maybePage.get() : null;
  }
  protected void setParentPage(Page page) {
	  this.parentPage = page;
  }
  
  public Either<Site, Page> getParent() {
    if (getParentSite().isDefined()) {
    	return new Left(getParentSite().get());
    } else if (getParentPage().isDefined()) {
    	return new Right(getParentPage().get());
    } else {
    	throw new Exception("Page with id %s does not have a parent!".format(id));
    }
  }
  
  public String getIdent() {
	  return this.ident;
  }
  public void setIdent(String ident) {
	  this.ident = ident;
  }

  public String getName() {
	  return this.name;
  }
  public void setName(String name) {
	  this.name = name;
  }
  
  public NodeSeq getContent() {
	  Helpers.string2nodeSeq(this.content);
  }
  public void setContent(NodeSeq html) {
	  this.content = html.toString();
  }
  public void setContent(String htmlString) {
	  setContent(Helpers.string2nodeSeq(htmlString));
  }
    
  
  public List<Page> getChildren() {
	  if (this.children == null) {
		  return new List<Page>();
	  } else {
		  return JavaConversions.asScalaIterator<Page>(this.children).toList();
	  }
  }
  public void setChildren(List<Page> children) {
	  this.children = JavaConversions.asJavaIterator<Page>(children);
	  for (Page p : this.children) {
		  p.setParentPage(this);
	  }
  }
  
  private java.util.List<String> getPath(java.util.List<String> suffix) {
	  Either<Site, Page> parent = getParent();
	  if (parent.isLeft()) {
		  Site site = parent.left().get();
		  suffix.addFirst(getIdent());
		  suffix.addFirst(site.getIdent());
		  suffix.addFirst(site.getOwner().getUsername());
		  return suffix;
	  } else {
		  Page page = parent.right().get();
		  suffix.addFirst(getIdent);
		  return page.getPath(suffix);
	  }
  }
  
  public List<String> getPath() {
	  return JavaConversions.asScalaIterator(getPath(new java.util.LinkedList<String>())).toList();
  }
}
