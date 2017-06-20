
public class Contact {
	private String name;
	private String surname;
	private String email;

	public Contact() {
		// ???
	}

	public Contact(String name, String surname, String email) {
		this.setName(name);
		this.setSurname(surname);
		this.setEmail(email);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean equals(Contact c) {
		return this.name.equals(c.name) && this.surname.equals(c.surname) && this.email.equals(c.email);
	}
}
