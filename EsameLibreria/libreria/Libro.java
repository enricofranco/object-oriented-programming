package libreria;

public class Libro {
	private String titolo;
	private String autore;
	private int anno;
	private double prezzo;
	private Editore editore;
	private Libreria libreria;
	
	private int quantita;
	
	private int sogliaGuardia;
	private int quantitaRiordino;
	
	public Libro(String titolo, String autore, int anno, double prezzo, Editore editore, Libreria libreria) {
		this.titolo = titolo;
		this.autore = autore;
		this.anno = anno;
		this.prezzo = prezzo;
		this.editore = editore;
		this.libreria = libreria;
	}

	public String getTitolo(){
        return titolo;
    }
    
    public String getAutore(){
        return autore;
    }
    
    public int getAnno(){
        return anno;
    }

    public double getPrezzo(){
        return prezzo;
    }
    
    public Editore getEditore(){
        return editore;
    }
    
    public Libreria getLibreria() {
    	return libreria;
    }

    public void setQuantita(int q){
    	this.quantita = q;
    }
    
    public int getQuantita(){
        return quantita;	
    }

    public void registraVendita(int settimana, int mese) {
    	if(quantita == 0)
    		throw new RuntimeException();
    	quantita--;
    	libreria.registraVendita(settimana, mese, this);
    	if(quantita == sogliaGuardia)
    		libreria.effettuaOrdine(this, quantitaRiordino);
    }

    public void setParametri(int soglia, int quantitaRiordino) {
    	this.sogliaGuardia = soglia;
    	this.quantitaRiordino = quantitaRiordino;
    }
}
