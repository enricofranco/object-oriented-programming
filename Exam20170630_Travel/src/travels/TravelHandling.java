package travels;
import java.util.*;
import java.util.stream.Collectors;

public class TravelHandling {
	private Set<String> customers = new HashSet<>();
	private Map<String, Destination> destinations = new HashMap<>();
	private Map<String, Operator> operators = new HashMap<>();
	private Map<String, Proposal> proposals = new HashMap<>();
	private List<Quote> quotes = new LinkedList<>();
//R1
	public void addCustomers(String... userNames) {
		for(String u : userNames)
			customers.add(u);
	}
	
	public void addDestination(String destinationName, String... operatorNames) throws TravelException {
		if(destinations.containsKey(destinationName))
			throw new TravelException("Destination " + destinationName + " already exists!");
		Destination d = new Destination(destinationName);
		for(String o : operatorNames) {
			Operator op = operators.get(o);
			if(op == null) {
				op = new Operator(o);
				operators.put(o, op);
			}
			d.addOperator(op);
			op.addDestination(d);
		}
		destinations.put(destinationName, d);
	}

	public List<String> getDestinations(String operatorName) {
		if(operators.containsKey(operatorName))
			return operators.get(operatorName).getDestinations();
		else
			return null;
	}
//R2
	public void addProposal(String name, String destinationName) throws TravelException {
		Destination d = destinations.get(destinationName);
		if(d == null || proposals.containsKey(name))
			throw new TravelException("Proposal " + name + " not valid!");
		proposals.put(name, new Proposal(name, d));
	}
	
	public List<String> setUsers(String proposalName, String... userNames) {
		Proposal p = proposals.get(proposalName);
		List<String> unvalidUsers = new LinkedList<>();
		if(p == null)
			return null;
		for(String u : userNames)
			if(! customers.contains(u))
				unvalidUsers.add(u);
			else
				p.addUser(u);
		return unvalidUsers.stream().sorted()
				.collect(Collectors.toList());
	}

	public SortedSet<String> getUsersOLD(String proposalName) {
        return null;
	}
	

//R3
	public List<String> setOperators(String proposalName, String... operatorNames) {
        Proposal p = proposals.get(proposalName);
        if(p == null)
        	return null;
        Destination d = p.getDestination();
		List<String> unvalidOperators = new LinkedList<String>();
		for(String o : operatorNames) {
			Operator op = operators.get(o);
			if(op == null || ! op.hasDestination(d))
				unvalidOperators.add(o);
			else {
				op.addProposal(p);
				p.addOperator(op);
			}
		}
		return unvalidOperators.stream().sorted()
				.collect(Collectors.toList());
	}
	
	public SortedSet<String> getOperatorsOLD(String proposalName) {
        return null;
	}
	
	public void addQuote(String proposalName, String operatorName, int amount) throws TravelException {
        Proposal p = proposals.get(proposalName);
        Operator o = operators.get(operatorName);
        if(p == null || o == null)
        	throw new TravelException();
        if(! o.hasProposal(p))
        	throw new TravelException("Operator/Proposal unvalid");
        Quote q = new Quote(p, o, amount);
        quotes.add(q);
        
	}
	
	public List<Quote> getQuotes(String proposalName) {
        return quotes.stream()
        		.filter(q -> q.getProposalName().equalsIgnoreCase(proposalName))
        		.sorted(Comparator.comparing(Quote::getOperatorName))
        		.collect(Collectors.toList());
	}

//R4
	private Quote getProposalOperator(Proposal p, Operator o) {
		for(Quote q : quotes)
			if(q.getOperatorName().equals(o.getName()) &&
					q.getProposalName().equals(p.getName()))
				return q;
		return null;
	}
	
	public void makeChoice(String proposalName, String userName, String operatorName) throws TravelException {
        Proposal p = proposals.get(proposalName);
        Operator o = operators.get(operatorName);
        if(p == null || o == null || ! customers.contains(userName))
        	return;
        Quote q = getProposalOperator(p, o);
        if(! p.hasUser(userName) || q == null)
        	throw new TravelException("Unvalid choice!");
        q.addUser(userName);
	}
	

	public Quote getWinningQuote(String proposalName) {
        return quotes.stream()
        		.filter(q -> q.getProposalName().equals(proposalName))
        		.sorted((q1,q2) -> {
        			if(q1.getNChoices() == q2.getNChoices())
        				return q1.getAmount() - q2.getAmount();
        			else
        				return q2.getNChoices() - q1.getNChoices();
        		})
        		.findFirst()
        		.orElse(null);
	}

	
//R5
	public SortedMap<String, Integer> totalAmountOfQuotesPerDestination() {
        return quotes.stream()
        		.sorted(Comparator.comparing(Quote::getDestination))
        		.collect(Collectors.groupingBy(Quote::getDestination,
        				TreeMap::new,
        				Collectors.summingInt(Quote::getAmount)));
	}
	
	public SortedMap<Integer, List<String>> operatorsPerNumberOfQuotes() {
		SortedMap<Integer, List<Operator>> m = operators.values().stream()
				.filter(o -> o.getNumQuotes() > 0)
				.sorted(Comparator.comparing(Operator::getName))
				.collect(Collectors.groupingBy(Operator::getNumQuotes,
						TreeMap::new,
						Collectors.toList()));
		
		SortedMap<Integer, List<String>> m2 = new TreeMap<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer int1, Integer int2) {
				return int2.compareTo(int1);
			}
		});		
		for(Integer i : m.keySet()) {
			List<Operator> operators = m.get(i);
			Set<String> names = new TreeSet<>();
			for(Operator o : operators) 
				names.add(o.getName());
			m2.put(i, names.stream().collect(Collectors.toList()));
		}
		
		return m2;
	}
	

	public SortedMap<String, Long> numberOfUsersPerDestination() {
		SortedMap<String, Long> m = new TreeMap<>();
		proposals.values().stream()
				.filter(p -> p.getNUsers() > 0)
				.forEach(p -> m.put(p.getDestination().getName(), 
						Long.valueOf(p.getNUsers())));
		return m;
	}
}


/*

	public void setWinningQuote (String proposalName)  {
		Proposal p = proposals.get(proposalName);
		List<Quote> list = p.getQuotes();
		if (list.size() > 0) {
			Optional<Quote> quote = list.stream()
				.max(comparing(Quote::getNUsers,naturalOrder())
						.thenComparing(Quote::getAmount, reverseOrder()));
			p.setWinningQuote(quote.get());
		}
	}	

	public List<Quote> getUserQuotesOLD (String userName) {
		User user = users.get(userName);
		return user.getQuotes();
	}
	
*/