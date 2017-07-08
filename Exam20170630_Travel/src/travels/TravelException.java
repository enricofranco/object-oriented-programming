package travels;

@SuppressWarnings("serial")
public class TravelException extends Exception {
    public TravelException() {
    }
	public TravelException(String reason) {
		super(reason);
	}
}
