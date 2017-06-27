package abbigliamento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Abbigliamento {
	private Map<String, Modello> modelli = new HashMap<>();
	private Map<String, Colore> colori = new HashMap<>();
	private Map<String, Materiale> materiali = new HashMap<>();
	private Map<String, Capo> capi = new HashMap<>();
	private Map<String, Collezione> collezioni = new HashMap<>();
	
	public void leggiFile(String fileName) {
		try(BufferedReader r = new BufferedReader(new FileReader(fileName))) {
			r.lines()
			.peek(System.out::println)
			.map(l -> l.split(";"))
			.forEach(line -> {
				String type = line[0].trim();
				if(type.equalsIgnoreCase("MOD")) {
					Modello m = new Modello(line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3]));
					modelli.put(line[1], m);
				} else if(type.equalsIgnoreCase("COL")) {
					Colore c = new Colore(line[1]);
					colori.put(line[1], c);
				} else if(type.equalsIgnoreCase("MAT")) {
					Materiale m = new Materiale(line[1], Double.parseDouble(line[2]));
					materiali.put(line[1], m);
				} else if(type.equalsIgnoreCase("CAP")) {
					Modello modello = getModello(line[2]);
					Materiale materiale = getMateriale(line[3]);
					Colore colore = getColore(line[4]);
					Capo capo = new Capo(modello, materiale, colore);
					capi.put(line[1], capo);
				} else if(type.equalsIgnoreCase("CLZ")) {
					Collezione collezione = new Collezione();
					for(int i = 2; i < line.length; ++i) {
						Capo capo = getCapo(line[i]);
						if(capo != null)
							collezione.add(capo);
					}
					collezioni.put(line[1], collezione);
				} else {
					System.err.println(line[1] + " Unrecognized type");
				}
			});
		} catch (FileNotFoundException e) {
			e.getMessage();
		} catch (IOException e) {
			e.getMessage();
		} catch(NumberFormatException e) {
			e.getMessage();
		} catch(ArrayIndexOutOfBoundsException e) {
			e.getMessage();
		}
	}

	public Modello getModello(String name) {
		return modelli.get(name);
	}

	public Colore getColore(String name) {
		return colori.get(name);
	}

	public Materiale getMateriale(String name) {
		return materiali.get(name);
	}

	public Capo getCapo(String name) {
		return capi.get(name);
	}

	public Collezione getCollezione(String name) {
		return collezioni.get(name);
	}

}
