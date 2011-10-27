package eschool.courses.model;

import javax.jdo.annotations.*;

import org.joda.time.LocalDate;

import eschool.users.model.Teacher;

@PersistenceCapable
public class TeacherAssignment {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;
	private Teacher teacher;
	private Section section;
	private Term term;
	@Persistent
	private LocalDate start;
	@Persistent
	private LocalDate end;
	
	public TeacherAssignment() {}
	
	public TeacherAssignment(Teacher teacher, Section section, Term term, 
			LocalDate start, LocalDate end) {
		this.teacher = teacher;
		this.section = section;
		this.term = term;
		this.start = start;
		this.end = end;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
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
