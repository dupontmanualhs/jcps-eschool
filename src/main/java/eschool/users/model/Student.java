package eschool.users.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Student extends Perspective {
	@Unique
	private String stateId;
	@Unique
	private String studentNumber;
	private int grade;
	private String teamName;
	
	public Student() {}
	
	public Student(User user, String stateId, String studentNumber, int grade, String teamName) {
		super(user);
		this.stateId = stateId;
		this.studentNumber = studentNumber;
		this.grade = grade;
		this.teamName = teamName;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
