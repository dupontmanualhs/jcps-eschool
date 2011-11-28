package eschool.sites.model.jdo;

import javax.jdo.annotations.*;

import java.util.List;
import java.util.LinkedList;

@PersistenceCapable
public class Page {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private long id;
	private Site parentSite;
	private Page parentPage;
	private String ident;
	private String name;
	private String content;
	@Join
	private List<Page> children;

	public Page() {
	}

	public Page(String name) {
		setName(name);
	}

	public long getId() {
		return this.id;
	}

	public Site getParentSite() {
		return this.parentSite;
	}

	public void setParentSite(Site site) {
		this.parentSite = site;
	}

	public Page getParentPage() {
		return this.parentPage;
	}

	public void setParentPage(Page page) {
		this.parentPage = page;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Page> getChildren() {
		if (this.children == null) {
			return new LinkedList<Page>();
		} else {
			return this.children;
		}
	}

	public void setChildren(List<Page> children) {
		this.children = children;
		for (Page p : this.children) {
			p.setParentPage(this);
		}
	}
}
