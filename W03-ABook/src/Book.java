
public class Book {
	private Contact[] contacts = new Contact[1000];
	int numContacts = 0;

	private Group[] groups = new Group[1000];
	int numGroups = 0;
	
	public int add(String name, String surname, String email) {
		Contact n = new Contact(name, surname, email);
		contacts[numContacts] = n;
		return numContacts++;
	}
	
	public int addGroup(String nameGroup) {
		Group g = new Group(nameGroup);
		groups[numGroups] = g;
		return numGroups++;
	}

	public void delete(int id) {
		for(Group g : groups) {
			// Delete contact in the group
			g.searchDeleteContact(this.contacts[id]);
		}
		
		if (id >= numContacts) {
			System.err.println("Yeuch");
		} else {
			contacts[id] = contacts[numContacts - 1];
			contacts[numContacts - 1] = null; // makes the garbage collector happy
			--numContacts;
		}
	}

	public Contact getContact(int id) {
		return contacts[id];
	}

	public int find(String surname) {
		for (int i = 0; i < numContacts; ++i) {
			if (contacts[i].getSurname().equals(surname)) {
				return i;
			}
		}
		return -1;
	}

	public int find(String surname, String name) {
		for (int i = 0; i < numContacts; ++i) {
			if (contacts[i].getSurname().equals(surname) && contacts[i].getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
}
