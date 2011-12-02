package jdohelpers;

import javax.jdo.annotations.*;

@PersistenceCapable(detachable="true")
public class Password {
	private String value;
	
	public Password(String value) {
		this.value = value;
	}
	
	public void set(String value) {
		this.value = value;
	}
	
	public boolean matches(String possPasswd) {
		return this.value != null && this.value.equals(possPasswd);
	}
}
