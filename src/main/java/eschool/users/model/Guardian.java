package eschool.users.model;

import java.util.Set;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Guardian {
	private Set<Student> children;
	
	public Guardian() {}
}
