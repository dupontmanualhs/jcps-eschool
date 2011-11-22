package eschool.users.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
@Inheritance(strategy=InheritanceStrategy.SUBCLASS_TABLE)
public class Perspective {
  @PrimaryKey
  @Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
  private long id;
  private User user;
  
  protected Perspective() { }
  
  protected Perspective(User user) {
    this();
    this.user = user;
  }
  
  public long getId() { return id; }
  
  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
}
