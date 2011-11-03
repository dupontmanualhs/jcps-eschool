package eschool.sites.model;

import javax.jdo.annotations.*;

import scala.xml.NodeSeq;

import jdohelpers.HtmlString;

@PersistenceCapable
public class Page {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	private Site parentSite;
	private Page parentPage;
	private String ident;
	private String name;
	private HtmlString content;
	
	public Page() {}
	
	public Page(String name, NodeSeq content) {
		this.name = name;
		this.setContent(content);
	}

	public Site getParentSite() {
		return parentSite;
	}

	public void setParentSite(Site parentSite) {
		this.parentSite = parentSite;
	}

	public Page getParentPage() {
		return parentPage;
	}

	public void setParentPage(Page parentPage) {
		this.parentPage = parentPage;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NodeSeq getContent() {
		return content.get();
	}

	public void setContent(String content) {
		this.content.set(content);
	}
	
	public void setContent(NodeSeq content) {
		this.content.set(content);
	}

	public long getId() {
		return id;
	}
}
