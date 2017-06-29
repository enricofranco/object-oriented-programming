package forum;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Topic {
	private String name;
	private String subject;
	private User user;
	private List<Message> messages = new LinkedList<>();

	public Topic(String name, String subject, User user) {
		this.name = name;
		this.subject = subject;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public String getSubject() {
		return subject;
	}

	public User getUser() {
		return user;
	}

	public Message submitMessage(User user, String title, String body) {
		Message m = new Message(title, body, user, this);
		messages.add(m);
		return m;
	}

	public Collection<Message> getMessagges() {
		return messages;
	}

	public long numMessages() {
		return messages.size();
	}
}
