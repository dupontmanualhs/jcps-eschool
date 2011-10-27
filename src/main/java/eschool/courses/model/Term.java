package eschool.courses.model;

import javax.jdo.annotations.*;

import org.joda.time.LocalDate;



@PersistenceCapable
public class Term {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;
	
	private String name;
	
	private AcademicYear year;
	@Persistent
	private LocalDate start;
	@Persistent
	private LocalDate end;
	
	public Term() {}
	
	public Term(String name, AcademicYear year, LocalDate start, LocalDate end) {
		this.name = name;
		this.year = year;
		this.start = start;
		this.end = end;
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

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public long getId() {
		return id;
	}
}
