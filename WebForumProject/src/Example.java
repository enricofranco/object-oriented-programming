import forum.DuplicateNickname;
import forum.*;

public class Example {

	public static void main(String[] args) throws DuplicateNickname {
		// Create a web forum
		Forum forum = new Forum("http://www.polito.it/Java/Forum/");

		User rms = forum.registerUser("rms", "Richard", "Stallman", "rms@gnu.org", "foobar");
		User jg = forum.registerUser("jg", "James", "Gosling", "jg@sun.com", "thePassWord");

		Topic java = forum.createTopic("Syntax", "Aspects of Java syntax", jg);
		forum.createTopic("GNU", "Gnu's Not Unix", rms);

		java.submitMessage(jg, "Welcome", "Welcome to all participants!");

		System.out.println("There are " + java.numMessages() + " submitted to topic " + java.getName());

		System.out.println("Average messages per forum " + forum.averageMessages());
	}

}
