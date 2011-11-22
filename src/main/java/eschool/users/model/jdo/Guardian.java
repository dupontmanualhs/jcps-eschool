package eschool.users.model.jdo;

import java.util.Set;
import javax.jdo.annotations.*;

@PersistenceCapable
public class Guardian extends Perspective {
  private Set<Student> children;
  
  public Set<Student> getChildren() {
	  return this.children;
  }
  
  public void setChildren(Set<Student> children) {
	  this.children = children;
  }
}
