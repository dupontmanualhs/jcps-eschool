package eschool.sites.model.jdo;

import scala.collection.immutable.List;
import scala.collection.JavaConversions;

import javax.jdo.annotations.*;

import eschool.users.model.jdo.User;

@PersistenceCapable
@Uniques({ @Unique(members = { "owner", "name" }),
		@Unique(members = { "owner", "ident" }) })
public class Site {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private long id;
	private User owner;
	private String name;
	private String ident;
	@Join
	private java.util.List<Page> children;

	@SuppressWarnings("unused")
	private Site() {
	}

	public Site(User owner, String name, String ident) {
		setOwner(owner);
		setName(name);
		setIdent(ident);
	}

	public long getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public List<Page> getChildren() {
		return JavaConversions.collectionAsScalaIterable(this.children)
				.toList();
	}

	public void setChildren(List<Page> children) {
		this.children = JavaConversions.seqAsJavaList(children);
	}
}
