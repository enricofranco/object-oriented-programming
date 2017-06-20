
public class Group {
	private String name;
	private Contact[] contacts = new Contact[100];
	int numContacts = 0;

	public Group(String name) {
		this.name = name;
	}
	
	public int addContact(Contact contact) {
		contacts[numContacts] = contact;
		return numContacts++;
	}
	
	public void searchDeleteContact(Contact c) {
		for(int i=0; i<numContacts; ++i) {
			if (c.equals(contacts[i])) {
				contacts[i] = contacts[numContacts - 1];
				contacts[numContacts - 1] = null; // makes the garbage collector happy
				--numContacts;
				return;
			}
		}
	}
}
