package travels;

import java.util.LinkedList;
import java.util.List;

public class Destination {
	private String destination;
	private List<Operator> operators = new LinkedList<>();
	
	public Destination(String destination) {
		this.destination = destination;
	}
	
	public void addOperator(Operator o) {
		operators.add(o);
	}
	
	public String getName() {
		return destination;
	}
}
