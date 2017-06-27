package orari;

public class Fermata implements Comparable<Fermata> {
	private String stazione;
	private int ore, minuti;
	
	public Fermata(String stazione, int ore, int minuti) {
		this.stazione = stazione;
		this.ore = ore;
		this.minuti = minuti;
	}

	public String getStazione() {
		return stazione;
	}

	public int getOre() {
		return ore;
	}

	public int getMinuti() {
		return minuti;
	}

	@Override
	public int compareTo(Fermata other) {
		int o = ore - other.getOre();
		if(o == 0)
			return minuti - other.getMinuti();
		else
			return o;
	}

}
