package forum;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Forum {
	private String url;
	private Map<String, User> users = new HashMap<>();
	private Set<Topic> topics = new HashSet<>();

	public Forum(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public User registerUser(String nick, String first, String last, String email, String password)
			throws DuplicateNickname {
		if(users.containsKey(nick))
			throw new DuplicateNickname();
		User u = new User(nick, first, last, email, password);
		users.put(nick, u);
		return u;
	}

	public User login(String nick, String password) {
		User u = users.get(nick);
		if(u == null || ! u.getPassword().equals(password))
			return null;
		else
			return u;
	}

	public Topic createTopic(String name, String subject, User author) {
		Topic t = new Topic(name, subject, author);
		topics.add(t);
		return t;
	}

	public Collection<Topic> listTopic() {
		return topics;
	}

	public List<User> rankUsers() {
		return users.values().stream()
				.sorted()
				.collect(Collectors.toList());
	}

	public double averageMessages() {
		return topics.stream()
				.collect(Collectors.averagingDouble(Topic::numMessages));
	}

}
