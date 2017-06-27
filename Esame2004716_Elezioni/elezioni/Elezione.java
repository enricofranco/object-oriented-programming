package elezioni;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Elezione {
	private Map<String, Cittadino> elettori = new HashMap<>();
	private Map<String, Lista> liste = new HashMap<>();
	private long numeroVotanti = 0;

	public Elezione() {
		
	}
	
	public Cittadino aggiungiElettore(String nome, String cognome) {
		Cittadino elettore = new Elettore(nome, cognome);
		String key = cognome + " " + nome;
		elettori.put(key, elettore);
		return elettore;
	}
	
	public Collection<Cittadino> getElettori() {
		return elettori.values();
	}
	
	public Cittadino getElettore(String nome, String cognome) {
		return elettori.get(cognome + " " + nome);
	}
	
	public void registraLista(Lista lista) {
		lista.setElezione(this);
		liste.put(lista.getNome(), lista);
	}

    /**
     * Il cittadino votante esprime un voto per la lista ed 
     * un voto di preferenza per il candidato identificato
     * da nome e cognome
     * @throws TentatoDoppioVoto se il cittadino ha gi� votato
     * @throws TaglioNonPermesso se il candidato per cui si esprime
     * 							la preferenza non appartiene alla lista
     */	
	public void vota(Cittadino votante, String nomeLista, String nome, String cognome)
		throws TentatoDoppioVoto, TaglioNonPermesso {
		
		if(votante.haVotato())
			throw new TentatoDoppioVoto();
		votante.setVotato();
		Lista lista = liste.get(nomeLista);
		if(lista == null)
			throw new RuntimeException();
		Cittadino candidato = getElettore(nome, cognome);
		if(candidato == null || candidato.getLista() == null)
			throw new RuntimeException();
		if(! lista.equals(candidato.getLista()))
			throw new TaglioNonPermesso();
		candidato.addVoto();
		++numeroVotanti;
	}

	/**
	 * Il cittadino votante esprime un voto per la lista
	 * il voto di preferenza va automaticamente al capolista
	 * @throws TentatoDoppioVoto se il cittadino ha gi� votato
	 */	
	public void vota(Cittadino votante, String nomeLista)
		throws TentatoDoppioVoto {
		
		if(votante.haVotato())
			throw new TentatoDoppioVoto();
		votante.setVotato();
		Lista lista = liste.get(nomeLista);
		if(lista == null)
			throw new RuntimeException();
		lista.getCapolista().addVoto();
		++numeroVotanti;
	}
	
	public long getNumeroVotanti(){
		return numeroVotanti;
	}
	
	public Collection<Lista> getRisultatiListe(){
		return liste.values().stream()
				.sorted(Comparator.comparing(Lista::getNumeroVoti).reversed())
				.collect(Collectors.toList());
	}

	public Collection<Cittadino> getRisultatiCandidati(){
		return elettori.values().stream()
				.filter(Cittadino::isCandidato)
				.sorted(Comparator.comparing(Cittadino::getNumeroVoti).reversed())
				.collect(Collectors.toList());
	}
	
	
}
