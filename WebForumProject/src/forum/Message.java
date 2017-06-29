package forum;

public class Message {
	private String title;
	private String body;
	private User user;
	private Topic topic;
	private long timestamp;
	
	public Message(String title, String body, User user, Topic topic) {
		this.title = title;
		this.body = body;
		this.user = user;
		this.topic = topic;
		this.timestamp = System.currentTimeMillis();
		
		this.user.addMessage();
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public User getUser() {
		return user;
	}

	public Topic getTopic() {
		return topic;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
