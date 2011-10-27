package eschool.users.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Teacher extends Perspective {
	@Unique
	private String personId;
	@Unique
	private String stateId;

	public Teacher() {}
	
	public Teacher(User user, String personId, String stateId) {
		super(user);
		this.personId = personId;
		this.stateId = stateId;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
}
