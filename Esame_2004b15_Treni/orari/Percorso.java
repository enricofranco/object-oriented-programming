package orari;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Percorso {
	private String code;
	private String category;
	private boolean extraOrdinary = false;
	private Set<Fermata> fermate = new HashSet<>();
	private Set<Treno> treni = new HashSet<>();
	
	public Percorso(String code, String category) {
		this.code = code;
		this.category = category;
	}

	public String getCodice() {
		return code;
	}

	public String getCategoria() {
		return category;
	}

	public boolean isStraordinario() {
		return extraOrdinary;
	}

	public void setStraordinario(boolean b) {
		extraOrdinary = b;
	}

	public Fermata aggiungiFermata(String stazione, int ore, int minuti) {
		Fermata f = new Fermata(stazione, ore, minuti);
		fermate.add(f);
		return f;
	}

	public List<Fermata> getFermate() {
		return fermate.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	public void addTreno(Treno t) {
		treni.add(t);
	}
	
	public Collection<Treno> getTreni() {
		return treni.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	public Fermata ultimaFermata() {
		List<Fermata> f = getFermate();
		int size = f.size();
		return f.get(size - 1);
	}

	public int ritardoMassimo() {
		return treni.stream().mapToInt(Treno::ritardoFinale)
				.max()
				.orElse(-1);
	}

	public int ritardoMedio() {
		return (int)treni.stream()
				.mapToInt(Treno::ritardoFinale)
				.average().orElse(-1);
	}

}
