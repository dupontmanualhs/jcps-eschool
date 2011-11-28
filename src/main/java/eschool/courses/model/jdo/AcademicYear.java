package eschool.courses.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
public class AcademicYear {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	
	@Unique
	@Column(allowsNull="false")
	private String name;
	
	@SuppressWarnings("unused")
	private AcademicYear() { }
	
	public AcademicYear(String name) {
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
