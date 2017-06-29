package forum;

@SuppressWarnings("serial")
public class DuplicateNickname extends Exception {
	public DuplicateNickname() {
	}

	public DuplicateNickname(String msg) {
		super(msg);
	}
}
