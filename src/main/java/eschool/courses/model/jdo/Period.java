package eschool.courses.model.jdo;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Period {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)
	private long id;
	private String name;
	private int order;
	
	private Period() {}
	
	public Period(String name, int order) {
		setName(name);
		setOrder(order);
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
}
