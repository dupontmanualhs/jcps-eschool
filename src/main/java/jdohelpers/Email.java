package jdohelpers;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class Email {
	private String value;
	
	@SuppressWarnings("unused")
	private Email() {}
	
	public Email(String value) {
		this.value = value;
	}
	
	public void set(String value) {
		this.value = value;
	}
	
	public String get() {
		return this.value;
	}
	
/*	public class EmailConverter implements ObjectStringConverter<Email> {
		public Email toObject(String str) {
			return new Email(str);
		}
		
		public String toString(Email email) {
			return email.get();
		}
	}
*/
}
