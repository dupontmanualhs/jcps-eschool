package eschool.users.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
class Student extends Perspective {
  @Unique
  private String stateId;
  @Unique
  private String studentNumber;
  private int grade;
  private String teamName;
  
  private Student() { }
  
  public Student(User user, String stateId, String studentNumber, int grade, String teamName) {
    super();
    setUser(user);
    setStateId(stateId);
    setStudentNumber(studentNumber);
    setGrade(grade);
    setTeamName(teamName); 
  }
  
  public String getStateId() { return stateId; }
  public void setStateId(String stateId) { this.stateId = stateId; }
  
  def studentNumber: String = _studentNumber
  def studentNumber_=(theStudentNumber: String) { _studentNumber = theStudentNumber }
  
  def grade: Int = _grade
  def grade_=(theGrade: Int) { _grade = theGrade }
  
  def teamName: String = _teamName
  def teamName_=(theTeamName: String) { _teamName = theTeamName }
}

