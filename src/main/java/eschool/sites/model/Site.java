package eschool.sites.model;

import javax.jdo.annotations.*;

import eschool.users.model.User;

@PersistenceCapable
@Uniques({@Unique(members={"owner", "name"}), @Unique(members={"owner", "ident"})})
public class Site {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	private User owner;
	private String name;
	private String ident;
	
	public Site() {}
	
	public Site(User owner, String name, String ident) {
		this.owner = owner;
		this.name = name;
		this.ident = ident;
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

	public long getId() {
		return id;
	}
}
