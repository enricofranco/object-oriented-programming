package travels;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Operator {
	private String name;
	private List<Destination> destinations = new LinkedList<>();
	private Set<Proposal> proposals = new HashSet<>();
	private int numQuotes = 0;
	
	public Operator(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addDestination(Destination d) {
		destinations.add(d);
	}
	
	public boolean hasDestination(Destination d) {
		return destinations.contains(d);
	}
	
	public void addProposal(Proposal p) {
		proposals.add(p);
	}
	
	public boolean hasProposal(Proposal p) {
		return proposals.contains(p);
	}	
	
	public void addQuote() {
		++numQuotes;
	}
	
	public int getNumQuotes() {
		return numQuotes;
	}

	public List<String> getDestinations() {
		return destinations.stream()
				.map(Destination::getName)
				.sorted()
				.collect(Collectors.toList());
	}
}
