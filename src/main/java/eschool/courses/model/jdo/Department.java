package eschool.courses.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Department {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	private String name;
	
	@SuppressWarnings("unused")
	private Department() { }
	
	public Department(String name) {
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
