package eschool.courses.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Room {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	@Unique
	private String name;
	
	@SuppressWarnings("unused")
	private Room() { }
	
	public Room(String name) {
		setName(name);
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
