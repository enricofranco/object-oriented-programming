package ticketing;

/**
 * Class representing the ticket linked to an issue or malfunction.
 * 
 * The ticket is characterized by a severity and a state.
 */
public class Ticket {

	private int id;
	private User reporter;
	private Component component;
	private String description;
	private Severity severity;
	private State status;
	private User mantainer;
	
	public Ticket(int id, User user, Component component, String description, Severity severity) {
		this.id = id;
		this.reporter = user;
		this.component = component;
		this.description = description;
		this.severity = severity;
		this.status = State.Open;
	}

	/**
	 * Enumeration of possible severity levels for the tickets.
	 * 
	 * Note: the natural order corresponds to the order of declaration
	 */
	public enum Severity {
		Blocking, Critical, Major, Minor, Cosmetic
	};

	/**
	 * Enumeration of the possible valid states for a ticket
	 */
	public static enum State {
		Open, Assigned, Closed
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Severity getSeverity() {
		return severity;
	}

	public String getAuthor() {
		return reporter.getUsername();
	}

	public String getComponent() {
		return component.getPath();
	}

	public State getState() {
		return status;
	}
	
	public String getMantainer() {
		return mantainer.getUsername();
	}

	public String getSolutionDescription() throws TicketException {
		if(status != State.Closed)
			throw new TicketException();
		return description;
	}

	public void assignMaintainer(User u) {
		mantainer = u;
		status = State.Assigned;
	}

	public void closeTicket(String description) {
		this.description = description;
		status = State.Closed;
	}
}
