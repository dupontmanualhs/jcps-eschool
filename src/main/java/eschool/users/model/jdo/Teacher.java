package eschool.users.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Teacher extends Perspective {
	@Unique
	private String personId;
	@Unique
	private String stateId;

	@SuppressWarnings("unused")
	private Teacher() {
	}

	public Teacher(User user, String personId, String stateId) {
		super(user);
		setPersonId(personId);
		setStateId(stateId);
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
