package eschool.courses.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Department {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;

	private String name;
	
	public Department() {}
	
	public Department(String name) {
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
