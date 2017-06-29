package ticketing;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import ticketing.Ticket.State;

public class IssueManager {
	private Map<String, User> users = new HashMap<>();
	private Map<String, Component> components = new HashMap<>();
	private List<Ticket> tickets = new LinkedList<>();

	/**
	 * Eumeration of valid user classes
	 */
	public static enum UserClass {
		/** user able to report an issue and create a corresponding ticket **/
		Reporter,
		/** user that can be assigned to handle a ticket **/
		Maintainer
	}

	/**
	 * Creates a new user
	 * 
	 * @param username
	 *            name of the user
	 * @param classes
	 *            user classes
	 * @throws TicketException
	 *             if the username has already been created or if no user class
	 *             has been specified
	 */
	public void createUser(String username, UserClass... classes) throws TicketException {
		if(classes.length == 0 || users.containsKey(username))
			throw new TicketException();
		Set<UserClass> userClasses = new HashSet<>();
		for(UserClass u : classes)
			userClasses.add(u);
		User u = new User(username, userClasses);
		users.put(username, u);
	}

	/**
	 * Creates a new user
	 * 
	 * @param username
	 *            name of the user
	 * @param classes
	 *            user classes
	 * @throws TicketException
	 *             if the username has already been created or if no user class
	 *             has been specified
	 */
	public void createUser(String username, Set<UserClass> classes) throws TicketException {
		if(classes.size() == 0 || users.containsKey(username))
			throw new TicketException();
		User u = new User(username, classes);
		users.put(username, u);
	}

	/**
	 * Retrieves the user classes for a given user
	 * 
	 * @param username
	 *            name of the user
	 * @return the set of user classes the user belongs to
	 */
	public Set<UserClass> getUserClasses(String username) {
		User u = users.get(username);
		if(u == null)
			throw new RuntimeException();
		return u.getUserClasses();
	}

	/**
	 * Creates a new component
	 * 
	 * @param name
	 *            unique name of the new component
	 * @throws TicketException
	 *             if a component with the same name already exists
	 */
	public void defineComponent(String name) throws TicketException {
		String path = "/" + name;
		if(components.containsKey(path))
			throw new TicketException("Duplicate top-level component name");
		Component c = new Component(name);
		components.put(path, c);
	}

	/**
	 * Creates a new sub-component as a child of an existing parent component
	 * 
	 * @param name
	 *            unique name of the new component
	 * @param parentPath
	 *            path of the parent component
	 * @throws TicketException
	 *             if the the parent component does not exist or if a
	 *             sub-component of the same parent exists with the same name
	 */
	public void defineSubComponent(String name, String parentPath) throws TicketException {
		String path = parentPath + "/" + name;
		if(components.containsKey(path)) 
			throw new TicketException("Duplicate component name");
		Component parent = components.get(parentPath);
		if(parent == null)
			throw new TicketException("Parent does not exist");
		Component c = new Component(name, parent);
		components.put(path, c);
	}

	/**
	 * Retrieves the sub-components of an existing component
	 * 
	 * @param path
	 *            the path of the parent
	 * @return set of children sub-components
	 */
	public Set<String> getSubComponents(String path) {
		Component parent = components.get(path);
		return components.values().stream()
				.filter(e -> e.getParent() == parent)
				.map(e -> e.getName())
				.collect(Collectors.toSet());
	}

	/**
	 * Retrieves the parent component
	 * 
	 * @param path
	 *            the path of the parent
	 * @return name of the parent
	 */
	public String getParentComponent(String path) {
		Component c = components.get(path);
		if(c == null)
			return null;
		Component parent = c.getParent();
		if(parent == null)
			return null;
		else
			return parent.getPath();
	}

	/**
	 * Opens a new ticket to report an issue/malfunction
	 * 
	 * @param username
	 *            name of the reporting user
	 * @param componentPath
	 *            path of the component or sub-component
	 * @param description
	 *            description of the malfunction
	 * @param severity
	 *            severity level
	 * 
	 * @return unique id of the new ticket
	 * 
	 * @throws TicketException
	 *             if the user name is not valid, the path does not correspond
	 *             to a defined component, or the user does not belong to the
	 *             Reporter {@link IssueManager.UserClass}.
	 */
	public int openTicket(String username, String componentPath, String description, Ticket.Severity severity)
			throws TicketException {
		User u = users.get(username);
		if(u == null || ! u.getUserClasses().contains(UserClass.Reporter))
			throw new TicketException("Unvalid user");
		Component c = components.get(componentPath);
		if(c == null)
			throw new TicketException("Unvalid component path");
		Ticket t = new Ticket(tickets.size() + 1, u, c, description, severity);
		tickets.add(t);
		return tickets.size();
	}

	/**
	 * Returns a ticket object given its id
	 * 
	 * @param ticketId
	 *            id of the tickets
	 * @return the corresponding ticket object
	 */
	public Ticket getTicket(int ticketId) {
		Ticket t;
		try {
			t = tickets.get(ticketId - 1);
		} catch (IndexOutOfBoundsException e) {
			t = null;
		}
		return t;
	}

	/**
	 * Returns all the existing tickets sorted by severity
	 * 
	 * @return list of ticket objects
	 */
	public List<Ticket> getAllTickets() {
		return tickets.stream()
				.sorted(Comparator.comparing(Ticket::getSeverity).thenComparing(Ticket::getId))
				.collect(Collectors.toList());
	}

	/**
	 * Assign a maintainer to an open ticket
	 * 
	 * @param ticketId
	 *            id of the ticket
	 * @param username
	 *            name of the maintainer
	 * @throws TicketException
	 *             if the ticket is in state <i>Closed</i>, the ticket id or the
	 *             username are not valid, or the user does not belong to the
	 *             <i>Maintainer</i> user class
	 */
	public void assingTicket(int ticketId, String username) throws TicketException {
		User u = users.get(username);
		if(u == null || ! u.getUserClasses().contains(UserClass.Maintainer))
			throw new TicketException("Unvalid user");
		Ticket t = getTicket(ticketId);
		if(t == null || t.getState() == State.Closed)
			throw new TicketException("Unvalid ticket");
		t.assignMaintainer(u);
	}

	/**
	 * Closes a ticket
	 * 
	 * @param ticketId
	 *            id of the ticket
	 * @param description
	 *            description of how the issue was handled and solved
	 * @throws TicketException
	 *             if the ticket is not in state <i>Assigned</i>
	 */
	public void closeTicket(int ticketId, String description) throws TicketException {
		Ticket t = getTicket(ticketId);
		if(t == null || t.getState() != State.Assigned)
			throw new TicketException("Unvalid ticket");
		t.closeTicket(description);
	}

	/**
	 * returns a sorted map (keys sorted in natural order) with the number of
	 * tickets per Severity, considering only the tickets with the specific
	 * state.
	 * 
	 * @param state
	 *            state of the tickets to be counted, all tickets are counted if
	 *            <i>null</i>
	 * @return a map with the severity and the corresponding count
	 */
	public SortedMap<Ticket.Severity, Long> countBySeverityOfState(Ticket.State state) {
		if(state == null)
			return tickets.stream()
					.collect(Collectors.groupingBy(Ticket::getSeverity,
							TreeMap::new,
							Collectors.counting()));
//					.entrySet().stream()
//					.sorted()
//					.collect(Collectors.toMap(Ticket::getSeverity, Collectors.counting(), 
//							mergeFunction);
		else
			return tickets.stream()
					.filter(e -> e.getState() == state)
					.collect(Collectors.groupingBy(Ticket::getSeverity,
							TreeMap::new,
							Collectors.counting()));
//		return null;
	}

	/**
	 * Find the top maintainers in terms of closed tickets.
	 * 
	 * The elements are strings formatted as <code>"username:###"</code> where
	 * <code>username</code> is the user name and <code>###</code> is the number
	 * of closed tickets. The list is sorter by descending number of closed
	 * tickets and then by username.
	 * 
	 * @return A list of strings with the top maintainers.
	 */
	public List<String> topMaintainers() {
		return tickets.stream()
				.filter(t -> t.getState() == State.Closed)
				.collect(Collectors.groupingBy(Ticket::getMantainer,
						Collectors.counting()))
				.entrySet().stream()
				.sorted((e1, e2) -> { 
					if(Long.valueOf(e2.getValue()).equals(e1.getValue()))
						return e1.getKey().compareTo(e2.getKey());
					else
						return Long.valueOf(e2.getValue()).compareTo(e1.getValue());
					})
				.map(e -> e.getKey() + ":" + String.format("%3d", e.getValue()))
				.collect(Collectors.toList());
				
	}

}
