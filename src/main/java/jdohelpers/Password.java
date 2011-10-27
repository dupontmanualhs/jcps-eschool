package jdohelpers;

public class Password {
	private String value;
	
	public void set(String value) {
		this.value = value;
	}
	
	public boolean matches(String possPasswd) {
		return this.value != null && this.value.equals(possPasswd);
	}

}
