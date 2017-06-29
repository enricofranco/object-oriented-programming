package ticketing;

public class Component {
	private String name;
	private Component parent = null;
	
	public Component(String name) {
		this.name = name;
	}
	
	public Component(String name, Component parent) {
		this.parent = parent;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Component getParent() {
		return parent;
	}

	public String getPath() {
		return (parent == null ? "" : parent.getPath()) + "/" + name;
	}
	
}
