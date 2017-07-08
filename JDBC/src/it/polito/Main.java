package it.polito;

import it.polito.db.DB;
import it.polito.finestre.FinestraPrincipale;

public class Main {

	public static void main(String[] args) {
		// apro una nuova connessione con il DB
		DB db = new DB();
		if (!db.OpenConnection()) {
			System.err.println("Errore di Connessione al DB. Impossibile Continuare");
			System.exit(-1);
		} else
			new FinestraPrincipale(db);
	}

}
