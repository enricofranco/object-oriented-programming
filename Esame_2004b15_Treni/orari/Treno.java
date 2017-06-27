package orari;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Treno implements Comparable<Treno> {
	private Percorso percorso;
	private int giorno, mese, anno;
	private List<Passaggio> passaggi = new LinkedList<>();

	public Treno(Percorso percorso, int giorno, int mese, int anno) {
		this.percorso = percorso;
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
	}

	public Percorso getPercorso() {
		return percorso;
	}

	public int getGiorno() {
		return giorno;
	}

	public int getMese() {
		return mese;
	}

	public int getAnno() {
		return anno;
	}
	
	public List<Passaggio> getPassaggi() {
		return passaggi;
	}

	public Passaggio registraPassaggio(String stazione, int ora, int minuti) throws StazioneNonValida {
		Fermata f = percorso.getFermate().stream()
				.filter(s -> s.getStazione().equals(stazione))
				.findFirst().orElse(null);
		if(f == null)
			throw new StazioneNonValida();
		Passaggio p = new Passaggio(f, ora, minuti);
		passaggi.add(p);
		return p;
	}

	public boolean arrivato() {
		Fermata ultimaFermata = percorso.ultimaFermata();
		return passaggi.stream()
				.map(Passaggio::getStazione)
				.filter(e -> e.equals(ultimaFermata.getStazione()))
				.collect(Collectors.toList())
				.contains(ultimaFermata.getStazione());
	}

	public int ritardoMassimo() {
		Passaggio maxRitardo = passaggi.stream()
				.collect(Collectors.maxBy(Comparator.comparingInt(Passaggio::ritardo))).orElse(null);
		if(maxRitardo == null)
			throw new RuntimeException();
		return maxRitardo.ritardo();
	}

	public int ritardoFinale() {
		Fermata ultimaFermata = percorso.ultimaFermata();
		Passaggio p = passaggi.stream().filter(e -> e.getStazione().equals(ultimaFermata.getStazione())).findFirst().orElse(null);
		if(p == null)
			throw new RuntimeException();
		return p.ritardo();
	}

	@Override
	public int compareTo(Treno other) {
		int a = other.getAnno() - anno;
		if(a == 0) {
			int m = other.getMese() - mese;
			if(m == 0)
				return other.getGiorno() - giorno;
			else
				return m;
		} else
			return a;
	}

}
