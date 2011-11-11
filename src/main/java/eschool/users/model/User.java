package eschool.users.model;

import javax.jdo.annotations.*;

import jdohelpers.*;

@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	@Unique
	@Column(allowsNull="false")
	private String username;
	private String first;
	private String middle;
	private String last;
	private String preferred;
	private Gender gender;
	@Embedded
	@Unique
	private Email email;
	@Embedded
	private Password password;
	
	public User() {}
	
	public User(String username, String first, String middle, String last,
			String preferred, Gender gender, String email, String password) {
		this.username = username;
		this.first = first;
		this.middle = middle;
		this.last = last;
		this.preferred = preferred;
		this.gender = gender;
		this.email = new Email(email);
		this.password = new Password(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getMiddle() {
		if (this.middle == null) {
			return "";
		} else {
			return middle;
		}
	}

	public void setMiddle(String middle) {
		this.middle = middle;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getPreferred() {
		return preferred;
	}

	public void setPreferred(String preferred) {
		this.preferred = preferred;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}
	
	public Password getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public long getId() {
		return id;
	}
	
	public String displayName() {
		return getFirst() + " " + getLast();
	}

	public String formalName() {
		return getLast() + ", " + getFirst() + " " + getMiddle();
	}
}