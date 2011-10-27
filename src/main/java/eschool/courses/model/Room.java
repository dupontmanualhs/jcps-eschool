package eschool.courses.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Room {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;
	@Unique
	private String name;

	public Room() {}
	
	public Room(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
}
