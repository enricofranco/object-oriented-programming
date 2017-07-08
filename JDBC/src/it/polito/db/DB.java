package it.polito.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DB {
	private Connection conn; // Memorizza la connessione con il DB
	
	/**
	 * Costruttore
	 */
	public DB(){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch(Exception e) {
			System.err.println("Driver non disponibile: " + e);
		}
	}
	
	/**
	 * Metodo che apre la connessione con la base di dati.
	 * Ritorna true se la connessione viene aperta con successo, false altrimenti.
	 */
	public boolean OpenConnection(){
		try{
			conn = DriverManager.getConnection("jdbc:sqlite:basidati.db");
			return true;
			
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Questo metodo riceve in ingresso il codice di un cliente e 
	 * ritorna i dati di tale cliente sottoforma di stringa.
	 * Praticamente la stringa restituita e' una concatenazione dei nomi 
	 * degli attributi e dei suoi valori.
	 * Se ad esempio il cliente selezionato si chiama Paolo Garza, ha come indirizzo 
	 * "Via Inesistente 24, Torino"
	 * e come numero di cellulare 0110907022 allora il metodo deve restituire
	 * la stringa "Nome: Paolo\nCognome: Garza\nIndirizzo: Via Inesistente 24, Torino\nCell: 0110907022"
	 */
	public String ottieniDatiCliente(long codiceCliente) {		
		String dati;
		boolean clienteEsiste;

		try {
			String query = "SELECT Nome, Cognome, Indirizzo, Cell"
					+ " FROM CLIENTE"
					+ " WHERE CodiceCliente = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, codiceCliente);

			ResultSet rs = pstmt.executeQuery();
			clienteEsiste = rs.next();
			if (clienteEsiste)
			{
				dati = new String("Nome: " + rs.getString("Nome")
						+ "\nCognome: " + rs.getString("Cognome")
						+ "\nIndirizzo: " + rs.getString("Indirizzo")
						+ "\nCell: " + (rs.getString("Cell")));
			} else
				dati = new String("Cliente inesistente");

			rs.close();
			pstmt.close();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return dati;
	}
	
	/**
	 * Questo metodo ritorna una lista di stringhe. Ogni stringa contiene 
	 * il nome di un corso. La lista di stringhe restituite corrisponde 
	 * all'elenco di corsi cui e' iscritto il cliente con codice pari 
	 * a quello presente nel parametro codice_cliente di questo metodo
	 */
	public List<String> ottieniCorsiCliente(long codiceCliente) {
		List<String> list=new LinkedList<String>();

		try {
			String query = "SELECT NomeC"
					+ " FROM Corso"
					+ " WHERE CodCorso IN ("
					+ " SELECT CodiceCorso FROM Iscritto_corso WHERE CodiceCliente = ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, codiceCliente);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("NomeC"));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return list;
	}
	
	/**
	 * Il metodo ritorna una lista di stringhe con dentro il codice dei corsi
	 * per cui c'e' ancora almeno un posto libero e i rispettivi nomi. Ogni stringa 
	 * della lista restituita corrisponde ad un singolo corso e deve 
	 * essere formattata usando il formato codice_corso - nome_corso
	 */
	public List<String> ottieniCodiciCorsi() {
		List<String> list=new LinkedList<String>();

		try {
			String query = "SELECT CodCorso, NomeC"
					+ " FROM Corso"
					+ " WHERE PostiDisponibili > 0";
			PreparedStatement pstmt = conn.prepareStatement(query);

			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getString("CodCorso") + " - " + rs.getString("NomeC"));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return list;
	}
	
	/**
	 * Questo metodo riceve in ingresso (parametri di input) il codice di un corso
	 * e il codice di un cliente e iscrive il cliente al corso (ossia 
	 * aggiunge una nuova iscrizione nella base di dati). L'iscrizione 
	 * consiste nell'inserimento della tupla appropriata nella tabella iscritto_corso.
 	 * 
 	 * Una volta aggiunta l'iscrizione nella tabella iscritto_corso, il metodo 
 	 * viene aggiornare (decrementandolo di uno) il valore dei posti disponibili
 	 * per il corso indicato in codCorso (praticamente di deve eseguire un'istruzione 
 	 * SQL di aggiornamento che vada a decrementare di uno il valore del 
 	 * campo corso.postidisponibili per il corso per cui si e' appena effettuata 
 	 * l'iscrizione).
	 * 
	 * Il metodo ritorna true se l'iscrizione e' avvenuta correttamente, false altrimenti.
	 */
	public boolean aggiungiIscrizione(long codCorso,long codCliente){
		try {
			conn.setAutoCommit(false); // Garantisce atomicità transazione
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format((Calendar.getInstance().getTime()));
			
			PreparedStatement pstmt = conn.prepareStatement(
					"INSERT INTO Iscritto_Corso"
					+ " VALUES (?, ?, ?)");
			pstmt.setLong(1, codCliente);
			pstmt.setLong(2, codCorso);
			pstmt.setString(3, today);
			pstmt.execute();

			pstmt = conn.prepareStatement(
					"UPDATE corso"
					+ " SET postidisponibili = postidisponibili - 1"
					+ " WHERE codCorso = ?");
			pstmt.setLong(1, codCorso);
			pstmt.execute();
			
			conn.commit();
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	/**
	 * Questo metodo chiude la connessione con il DB
	 */
	public void CloseConnection(){
		try {
			conn.close();
		} catch (Exception e) {
			System.err.println("Errore nel chiudere la connessione con il DB!");
		}
	}
}
