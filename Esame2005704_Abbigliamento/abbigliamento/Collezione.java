package abbigliamento;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Collezione {
	Collection<Capo> capi = new LinkedList<>();

	public void add(Capo capo) {
		capi.add(capo);
	}

	public Collection<Capo> trova(Colore colore) {
		return capi.stream()
				.filter(c -> c.getColore().equals(colore))
				.collect(Collectors.toList());
	}

	public Collection<Capo> trova(Materiale materiale) {
		return capi.stream()
				.filter(c -> c.getMateriale().equals(materiale))
				.collect(Collectors.toList());
	}

	public Collection<Capo> trova(Modello modello) {
		return capi.stream()
				.filter(c -> c.getModello().equals(modello))
				.collect(Collectors.toList());
	}

}
