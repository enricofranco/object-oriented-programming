package libreria;

public class Ordine {
	private int numero;
	private Libro libro;
	private int quantita;
	private boolean consegnato = false;

    public Ordine(int numero, Libro libro, int quantita) {
		this.numero = numero;
		this.libro = libro;
		this.quantita = quantita;
	}
    
    public void setConsegnato() {
    	consegnato = true;
    	int currentQty = libro.getQuantita();
    	libro.setQuantita(currentQty + quantita);
    }

	public Editore getEditore(){
        return libro.getEditore();
    }
    
    public Libro getLibro(){
        return libro;
    }
    
    public int getQuantita(){
        return quantita;
    }

    public boolean isConsegnato(){
        return consegnato;
    }

    public int getNumero(){
        return numero;
    }
}
