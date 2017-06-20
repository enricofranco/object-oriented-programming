
public class Main {

	public static void main(String[] args) {
		Book book = new Book();
		int gs = book.add("Giovanni", "Squillero", "squillero@polito.it");
		book.add("Gabriele", "Squillero", "");
		book.add("Elena", "Squillero", "");

		System.out.println(book.find("Squillero", "Elena"));
		book.delete(gs);
		System.out.println(book.find("Squillero", "Elena"));
		book.delete(0);
		System.out.println(book.find("Squillero", "Elena"));

		int c1 = book.add("Groucho", "Marx", "");
		int c2 = book.add("Groucho", "Marx", "");

		System.out.println(book.getContact(c1).equals(book.getContact(c2)));
	}

}
