package eschool.courses.model.jdo;

import java.sql.Date;

import javax.jdo.annotations.*;

import eschool.users.model.jdo.Student;

@PersistenceCapable
public class StudentEnrollment {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	private Student student;
	private Section section;
	private Term term;
	@Persistent
	private Date start;
	@Persistent
	private Date end;
	
	@SuppressWarnings("unused")
	private StudentEnrollment() { }
	
	public StudentEnrollment(Student student, Section section, Term term, Date start, Date end) {
		setStudent(student);
		setSection(section);
		setTerm(term);
		setStart(start);
		setEnd(end);
	}
	
	public long getId() {
		return id;
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