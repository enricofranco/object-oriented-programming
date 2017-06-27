package orari;

import java.util.Collection;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;

public class Orari {
	private Map<String, Percorso> percorsi = new HashMap<>();

	public Percorso creaPercorso(String codice, String categoria) {
		if(! categoria.equalsIgnoreCase("Intercity")
				&& ! categoria.equalsIgnoreCase("Eurostar")
				&& ! categoria.equalsIgnoreCase("Interregionale")
				&& ! categoria.equalsIgnoreCase("Regionale"))
			throw new RuntimeException();
		Percorso p = new Percorso(codice, categoria);
		percorsi.put(codice, p);
		return p;
	}

	public Collection<Percorso> getPercorsi() {
		return percorsi.values();
	}

	public Percorso getPercorso(String codice) {
		return percorsi.get(codice);
	}

	public Treno nuovoTreno(String codice, int giorno, int mese, int anno) throws PercorsoNonValido {
		Percorso p = getPercorso(codice);
		if(p == null)
			throw new PercorsoNonValido();
		Treno t = new Treno(p, giorno, mese, anno);
		p.addTreno(t);
		return t;
	}

//	public List getTreni() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
