package travels;

import java.util.HashSet;
import java.util.Set;

public class Proposal {
	private String name;
	private Destination destination;
	private Set<String> users = new HashSet<>();
	private Set<Operator> operators = new HashSet<>();
	
	public Proposal(String name, Destination destination) {
		this.name = name;
		this.destination = destination;
	}
	
	public String getName() {
		return name;
	}

	public Destination getDestination() {
		return destination;
	}
	
	public void addUser(String user) {
		users.add(user);
	}
	
	public boolean hasUser(String user) {
		return users.contains(user);
	}
	
	public int getNUsers() {
		return users.size();
	}
	
	public void addOperator(Operator o) {
		operators.add(o);
	}
}
