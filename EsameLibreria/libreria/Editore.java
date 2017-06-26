package libreria;

public class Editore implements Comparable<Editore> {
	private String nome;
	private int tempoConsegna;
	private String email;
	
	public Editore(String nome, int tempoConsegna, String email) {
		this.nome = nome;
		this.tempoConsegna = tempoConsegna;
		this.email = email;
	}

	public String getNome(){
        return nome;
    }
    
    public int getTempoConsegna(){
        return tempoConsegna;
    }
    
    public String getEmail(){
        return email;
    }

	@Override
	public String toString() {
		return "Editore: " + nome + ", " + tempoConsegna + ", " + email;
	}

	@Override
	public int compareTo(Editore other) {
		return this.getNome().compareTo(other.getNome());
	}
}
