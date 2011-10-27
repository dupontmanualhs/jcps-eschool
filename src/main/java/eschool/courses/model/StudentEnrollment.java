package eschool.courses.model;

import javax.jdo.annotations.*;

import org.joda.time.LocalDate;

import eschool.users.model.Student;

@PersistenceCapable
public class StudentEnrollment {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;
	private Student student;
	private Section section;
	private Term term;
	@Persistent
	private LocalDate start;
	@Persistent
	private LocalDate end;
	
	public StudentEnrollment() {}
	
	public StudentEnrollment(Student student, Section section, Term term,
			LocalDate start, LocalDate end) {
		this.student = student;
		this.section = section;
		this.term = term;
		this.start = start;
		this.end = end;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
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
