package eschool.courses.model.jdo;

import java.sql.Date;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Term {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	private String name;
	private AcademicYear year;
	@Persistent
	private Date start;
	@Persistent
	private Date end;
	
	@SuppressWarnings("unused")
	private Term() { }
	
	public Term(String name, AcademicYear year, Date start, Date end) {
		setName(name);
		setYear(year);
		setStart(start);
		setEnd(end);
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
	
	public AcademicYear getYear() {
		return year;
	}
	
	public void setYear(AcademicYear year) {
		this.year = year;
	}
	
	public Date getStart() {
		return start;
	}
	
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	
	public void setEnd(Date end) {
		this.end = end;
	}
}

