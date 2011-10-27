package eschool.courses.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class AcademicYear {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	
	@Unique
	@Column(allowsNull="false")
	private String name;
	
	public AcademicYear() {}
	
	public AcademicYear(String name) {
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
