package managingProperties;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class PropertyManager {
	private Map<String, Building> buildings = new HashMap<>();
	private Set<String> owners = new TreeSet<>();
	private Map<String, String> ownerApt = new HashMap<>();
	private SortedMap<String, Integer> professions = new TreeMap<>();
	private Map<String, String> professionals = new HashMap<>();
	private List<Request> requests = new LinkedList<>();
	
	private boolean validApartmentNumber(int n) {
		return (n >= 1 && n <= 100);
	}
	
	public void addBuilding(String building, int n) throws PropertyException {
		if(buildings.containsKey(building))
			throw new PropertyException("Building already exists!");
		if(! validApartmentNumber(n))
			throw new PropertyException("Invalid apartments number!");
		buildings.put(building, new Building(building, n));
	}

	public void addOwner(String owner, String... apartments) throws PropertyException {
		if(owners.contains(owner))
			throw new PropertyException("Owner already exists!");
		owners.add(owner);
		for(String a : apartments) {
			String[] info = a.split(":");
			Building b = buildings.get(info[0]);
			if(b == null)
				throw new PropertyException("Building does not exist!");
			ownerApt.put(a, owner);
		}
	}

	/**
	 * Returns a map ( number of apartments => list of buildings )
	 * 
	 */
	public SortedMap<Integer, List<String>> getBuildings() {
		return buildings.values().stream()
				.sorted(Comparator.comparing(Building::getId))
				.collect(Collectors.groupingBy(Building::getNumApartments,
						TreeMap::new,
						Collectors.mapping(Building::getId, Collectors.toList())));
	}

	public void addProfessionals(String profession, String... professionals) throws PropertyException {
		if(professions.containsKey(profession))
			throw new PropertyException("Profession already exists!");
		for(String p : professionals) {
			if(this.professionals.containsKey(p))
				throw new PropertyException();
			this.professionals.put(p, profession);
		}
		this.professions.put(profession, professionals.length);
	}

	/**
	 * Returns a map ( profession => number of workers )
	 *
	 */
	public SortedMap<String, Integer> getProfessions() {
		return professions;
	}

	public int addRequest(String owner, String apartment, String profession) throws PropertyException {
		if(! owners.contains(owner) || 
				! ownerApt.containsKey(apartment) || 
				! ownerApt.get(apartment).equals(owner) ||
				! professions.containsKey(profession))
			throw new PropertyException();
		Request r = new Request(requests.size() + 1, owner, apartment, profession);
		requests.add(r);
		return requests.size();
	}

	public void assign(int requestN, String professional) throws PropertyException {
		Request r = requests.get(requestN - 1);
		String p = this.professionals.get(professional);
		if(r == null || ! r.getStatus().equalsIgnoreCase("pending") ||
				p == null || ! r.getProfession().equals(p))
			throw new PropertyException();
			
		r.setProfessional(professional);
	}

	public List<Integer> getAssignedRequests() {
		return requests.stream()
				.filter(r -> r.getStatus().equalsIgnoreCase("assigned"))
				.map(Request::getId)
				.collect(Collectors.toList());
	}

	public void charge(int requestN, int amount) throws PropertyException {
		Request r = requests.get(requestN - 1);
		if(r == null || ! r.getStatus().equalsIgnoreCase("assigned") ||
				amount < 0 || amount > 1000)
			throw new PropertyException();
		r.setCharge(amount);
	}

	/**
	 * Returns the list of request ids
	 * 
	 */
	public List<Integer> getCompletedRequests() {
		return requests.stream()
				.filter(r -> r.getStatus().equalsIgnoreCase("completed"))
				.map(Request::getId)
				.collect(Collectors.toList());
	}

	/**
	 * Returns a map ( owner => total expenses )
	 * 
	 */
	public SortedMap<String, Integer> getCharges() {
		return requests.stream()
				.filter(r -> r.getStatus().equalsIgnoreCase("completed") && r.getCharge() != 0)
				.collect(Collectors.groupingBy(Request::getOwner,
						TreeMap::new,
						Collectors.summingInt(Request::getCharge)));
	}

	/**
	 * Returns the map ( building => ( profession => total expenses) ). Both
	 * buildings and professions are sorted alphabetically
	 * 
	 */
	public SortedMap<String, Map<String, Integer>> getChargesOfBuildings() {
		return requests.stream()
				.filter(r -> r.getStatus().equalsIgnoreCase("completed") && r.getCharge() != 0)
				.collect(Collectors.groupingBy(Request::getBuilding,
						TreeMap::new,
						Collectors.groupingBy(Request::getProfession, 
								TreeMap::new,
								Collectors.summingInt(Request::getCharge))));
		}

}
