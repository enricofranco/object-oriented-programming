package restaurantChain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Restaurant {
	
	private String name;
	private int tablesAvailable;
	private int refusedCustomer = 0;
	private double totalPayed = 0.0;
	private Map<String, Double> menus = new HashMap<>();
	private Map<String, Integer> groups = new HashMap<>();
	private Map<String, LinkedList<String>> orderGroups = new HashMap<String, LinkedList<String>>();
	private List<String> payedGroups = new LinkedList<>();
	
	public Restaurant(String name, int tablesAvailable) {
		this.name = name;
		this.tablesAvailable = tablesAvailable;
	}

	public String getName() {
		return name;
	}

	public void addMenu(String name, double price) throws InvalidName {
		if(menus.containsKey(name))
			throw new InvalidName();
		menus.put(name, price);
	}

	public int reserve(String name, int persons) throws InvalidName {
		if(groups.containsKey(name))
			throw new InvalidName();
		if(tablesAvailable*4 < persons) {
			System.err.println("Prenotation rejected!");
			refusedCustomer += persons;
			return 0;
		}
		groups.put(name, persons);
		int tablesBooked = (int) Math.ceil((double)persons/4);
		tablesAvailable -= tablesBooked;
		return tablesBooked;
	}

	public int getRefused() {
		return refusedCustomer;
	}

	public int getUnusedTables() {
		return tablesAvailable;
	}

	public boolean order(String name, String... menu) throws InvalidName {
		if(! groups.containsKey(name))
			throw new InvalidName();
		if(menu.length < groups.get(name)) {
			System.err.println("Order not accepted");
			return false;
		}
		LinkedList<String> menuList = new LinkedList<>();
		for(String m : menu) {
			if(! menus.containsKey(m))
				throw new InvalidName();
			menuList.add(m);
		}
		orderGroups.put(name, menuList);
		return true;
	}

	public List<String> getUnordered() {
		return groups.keySet().stream()
				.filter(g -> orderGroups.get(g) != null)
				.sorted()
				.collect(Collectors.toList());
	}

	public double pay(String name) throws InvalidName {
		if(! groups.containsKey(name))
			throw new InvalidName();
		if(! orderGroups.containsKey(name))
			throw new RuntimeException();
		double amount = orderGroups.get(name).stream()
				.mapToDouble(m -> menus.get(m))
				.sum();
		totalPayed += amount;
		return amount;
	}

	public List<String> getUnpaid() {
		return groups.keySet().stream()
				.filter(g -> ! payedGroups.contains(g))
				.sorted()
				.collect(Collectors.toList());
	}

	public double getIncome() {
		return totalPayed;
	}

}
