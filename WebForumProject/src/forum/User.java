package forum;

public class User implements Comparable<User> {
	private String nick;
	private String first;
	private String last;
	private String email;
	private String password;
	private long numMessages = 0;

	public User(String nick, String first, String last, String email, String password) {
		this.nick = nick;
		this.first = first;
		this.last = last;
		this.email = email;
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void addMessage() {
		++numMessages;
	}

	public long numSubmitted() {
		return numMessages;
	}

	public int compareTo(User u) {
		return Long.valueOf(u.numSubmitted()).compareTo(this.numSubmitted());
	}
}
