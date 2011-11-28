package eschool.users.model.jdo;

import javax.jdo.annotations.*;

import scala.Option;
import scala.None;
import scala.Some;

import jdohelpers.Email;
import jdohelpers.Gender;
import jdohelpers.Password;

@PersistenceCapable
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.INCREMENT)
	private long id;
	@Unique
	@Column(allowsNull = "false")
	private String username;
	@Column(allowsNull = "false")
	private String first;
	private String middle;
	@Column(allowsNull = "false")
	private String last;
	private String preferred;
	private Gender gender;
	@Embedded
	@Unique
	private Email email;
	@Embedded
	private Password password;

	private User() {
	}

	public User(String username, String first, Option<String> middle,
			String last, Option<String> preferred, Gender gender, String email,
			String password) {
		this.username = username;
		this.first = first;
		if (middle.isDefined()) {
			this.middle = middle.get();
		} else {
			this.middle = null;
		}
		this.last = last;
		setPreferred(preferred);
		this.gender = gender;
		this.email = new Email(email);
		this.password = new Password(password);
	}

	public long getId() {
		return id;
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

	public Option<String> getMiddle() {
		if (this.middle == null) {
			return Option.empty();
		} else {
			return new Some<String>(this.middle);
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

	public Option<String> getPreferred() {
		if (this.preferred == null) {
			return Option.empty();
		} else {
			return new Some<String>(this.preferred);
		}
	}

	public void setPreferred(Option<String> preferred) {
		if (preferred.isDefined()) {
			this.preferred = preferred.get();
		} else {
			this.preferred = null;
		}
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Option<String> getEmail() {
		if (this.email == null) {
			return Option.empty();
		} else {
			return new Some<String>(this.email.get());
		}
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public void setEmail(Option<String> email) {
		if (email.isDefined()) {
			this.email = new Email(email.get());
		} else {
			this.email = null;
		}
	}

	public void setEmail(String email) {
		setEmail(new Some<String>(email));
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

	public void setPassword(String password) {
		setPassword(new Password(password));
	}

	public String displayName() {
		return (getPreferred().isDefined() ? getPreferred().get() : getFirst())
				+ " " + getLast();
	}

	public String formalName() {
		return getLast() + ", " + getFirst()
				+ (getMiddle().isDefined() ? " " + getMiddle().get() : "");
	}
}