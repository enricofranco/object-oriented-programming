package travels;

import java.util.HashSet;
import java.util.Set;

public class Quote {
	private Proposal proposal;
	private Operator operator;
	private int quote;
	private Set<String> users = new HashSet<>();
	private int numChoices;

	public Quote(Proposal proposal, Operator operator, int quote) {
		this.proposal = proposal;
		this.operator = operator;
		this.quote = quote;
		
		operator.addQuote();
	}
	
	public void addUser(String u) {
		this.users.add(u);
		++numChoices;
	}

	public int getAmount() {
		return quote;
	}

	public String getProposalName() {
		return proposal.getName();
	}
	
	public String getDestination() {
		return proposal.getDestination().getName();
	}

	public String getOperatorName() {
		return operator.getName();
	}

	public int getNChoices() {
		return numChoices;
	}

}
