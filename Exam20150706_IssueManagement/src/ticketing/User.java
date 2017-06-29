package ticketing;

import java.util.HashSet;
import java.util.Set;

import ticketing.IssueManager.UserClass;

public class User {
	private String username;
	private Set<UserClass> userClasses = new HashSet<>();

	public User(String username, Set<UserClass> userClasses) {
		this.username = username;
		this.userClasses = userClasses;
	}

	public String getUsername() {
		return username;
	}

	public Set<UserClass> getUserClasses() {
		return userClasses;
	}

}
