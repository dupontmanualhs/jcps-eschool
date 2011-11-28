package eschool.courses.model.jdo;

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
	@Element(types={Term.class})
	@Join
	private Set<Term> terms;
	@Element(types={Period.class})
	@Join
	private Set<Period> periods;
	private Room room;
	
	private Section() { }
	
	public Section(Course course, String sectionId, Set<Term> terms, Set<Period> periods, Room room) {
		setCourse(course);
		setSectionId(sectionId);
		setTerms(terms);
		setPeriods(periods);
		setRoom(room);
	}
	
	public long getId() {
		return id;
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
}