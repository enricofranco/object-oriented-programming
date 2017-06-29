package managingProperties;

public class Building {
	private String id;
	private int numApartments;
	
	public Building(String id, int numApartments) {
		this.id = id;
		this.numApartments = numApartments;
	}
	
	public String getId() {
		return id;
	}

	public int getNumApartments() {
		return numApartments;
	}

}
