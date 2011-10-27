package eschool.courses.model;

import java.util.Set;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Section {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;
	private Course course;
	@Unique
	private String sectionId;
	private Set<Term> terms;
	private Set<Period> periods;
	private Room room;
	
	public Section() {}
	
	public Section(Course course, String sectionId, Set<Term> terms, Set<Period> periods, Room room) {
		this.course = course;
		this.sectionId = sectionId;
		this.terms = terms;
		this.periods = periods;
		this.room = room;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public Set<Term> getTerms() {
		return terms;
	}

	public void setTerms(Set<Term> terms) {
		this.terms = terms;
	}

	public Set<Period> getPeriods() {
		return periods;
	}

	public void setPeriods(Set<Period> periods) {
		this.periods = periods;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public long getId() {
		return id;
	}
}
