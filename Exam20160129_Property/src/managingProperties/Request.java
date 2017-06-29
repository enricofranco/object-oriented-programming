package managingProperties;

public class Request {
	private int id;
	private String owner;
	private String apartment;
	private String profession;
	private String status;
	private String professional;
	private int charge;
	
	public Request(int id, String owner, String apartment, String profession) {
		this.id = id;
		this.owner = owner;
		this.apartment = apartment;
		this.profession = profession;
		this.status = "pending";
	}

	public int getId() {
		return id;
	}
	
	public String getBuilding() {
		return apartment.split(":")[0];
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getStatus() {
		return status;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
		this.status = "assigned";
	}

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
		this.status = "completed";
	}
	
}
