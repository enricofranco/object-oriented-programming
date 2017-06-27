package elezioni;

public class Elettore implements Cittadino {
	private String nome, cognome;
	private boolean votato = false;
	private boolean capolista = false;
	private long numeroVoti = 0;
	private Lista lista = null;

	public Elettore(String nome, String cognome) {
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public Lista getLista() {
		return lista;
	}
	
	@Override
	public String getNome() {
		return nome;
	}

	@Override
	public String getCognome() {
		return cognome;
	}

	@Override
	public boolean haVotato() {
		return votato;
	}

	@Override
	public boolean isCapolista() {
		return capolista;
	}

	@Override
	public boolean isCandidato() {
		return lista != null;
	}

	@Override
	public long getNumeroVoti() {
		return numeroVoti;
	}

	@Override
	public void setCapolista() {
		capolista = true;
	}
	
	@Override
	public void setLista(Lista l) {
		this.lista = l;
	}

	@Override
	public void setVotato() {
		votato = true;
	}

	@Override
	public void addVoto() {
		++numeroVoti;
		lista.addVoto();
	}

}
