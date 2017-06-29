package restaurantChain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Chain {

	private Map<String, Restaurant> restaurants = new HashMap<>();
	
	public void addRestaurant(String name, int tables) throws InvalidName {
		Restaurant r = new Restaurant(name, tables);
		if(restaurants.containsKey(name))
			throw new InvalidName();
		restaurants.put(name, r);
	}

	public Restaurant getRestaurant(String name) throws InvalidName {
		Restaurant r = restaurants.get(name);
		if(r == null)
			throw new InvalidName();
		return r;
	}

	public List<Restaurant> sortByIncome() {
		return restaurants.values().stream()
				.sorted(Comparator.comparing(Restaurant::getIncome).reversed())
				.collect(Collectors.toList());
	}

	public List<Restaurant> sortByRefused() {
		return restaurants.values().stream()
				.sorted(Comparator.comparing(Restaurant::getRefused))
				.collect(Collectors.toList());
	}

	public List<Restaurant> sortByUnusedTables() {
		return restaurants.values().stream()
				.sorted(Comparator.comparing(Restaurant::getUnusedTables))
				.collect(Collectors.toList());
	}
}
