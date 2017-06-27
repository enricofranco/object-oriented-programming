package elezioni;

import java.util.Collection;
import java.util.LinkedList;


public class Lista {
	private String nome, motto;
	private Collection<Cittadino> candidati = new LinkedList<>();
	private Cittadino capolista;
	private long numeroVoti = 0;
	private Elezione elezione;
	
	public Lista(String nome, String motto) {
		this.nome = nome;
		this.motto = motto;
	}
	
	public void setElezione(Elezione elezione) {
		this.elezione = elezione;
	}
	
	public String getNome() {
		return nome;
	}

	public String getMotto() {
		return motto;
	}
	
	public void assegnaCapolista(Cittadino capolista)
			throws CandidatoNonValido {
		if(capolista.isCandidato() || capolista.isCapolista())
			throw new CandidatoNonValido();
		this.capolista = capolista;
		capolista.setCapolista();
		capolista.setLista(this);
	}

	public void aggiungiCandidato(Cittadino candidato)
			throws CandidatoNonValido {
		if(candidato.isCandidato() || candidato.isCapolista())
			throw new CandidatoNonValido();
		this.candidati.add(candidato);
		candidato.setLista(this);
	}

	public Cittadino getCapolista() {
		return capolista;
	}

	/**
	 * Restuisce la collezione dei candidati
	 * (NON include il capolista)
	 */
	public Collection<Cittadino> getCandidati() {
		return candidati;
	}
	
	public void addVoto() {
		++numeroVoti;
	}
	
	public long getNumeroVoti() {
		return numeroVoti;
	}

	public double getPercentualeVoti() {
		return ((double)numeroVoti)/((double)elezione.getNumeroVotanti());
	}
}
