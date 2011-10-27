package eschool.courses.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Course {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;

	private String name;
	@Unique
	private String masterNumber;
	private Department department;
	
	public Course() {}
	
	public Course(String name, String masterNumber, Department department) {
		this.name = name;
		this.masterNumber = masterNumber;
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMasterNumber() {
		return masterNumber;
	}

	public void setMasterNumber(String masterNumber) {
		this.masterNumber = masterNumber;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public long getId() {
		return id;
	}
}
