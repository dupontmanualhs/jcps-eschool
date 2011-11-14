package eschool.courses.model.java;

import javax.jdo.annotations.*;

import jdo.java.Id;

@PersistenceCapable
public class AcademicYear extends Id<Long> {
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
}
