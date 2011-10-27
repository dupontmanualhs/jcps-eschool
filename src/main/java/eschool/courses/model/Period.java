package eschool.courses.model;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Period {
	@PrimaryKey
	@Persistent(valueStrategy=IdGeneratorStrategy.INCREMENT)	
	private long id;
	
	private String name;
	private int order;
	
	public Period() {}
	
	public Period(String name, int order) {
		this.name = name;
		this.order = order;
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

	public long getId() {
		return id;
	}
}
