package libreria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Libreria {
	private Map<String, Editore> editori = new HashMap<>();
	private Set<Libro> libri = new HashSet<>();
	private Map<Integer, LinkedList<Libro>> venditeSettimanali = new HashMap<>();
	private Map<Integer, LinkedList<Libro>> venditeMensili = new HashMap<>();
	
	private List<Ordine> ordini = new LinkedList<>();
	
    public Editore creaEditore(String nome, int tempoConsegna, String email){
        Editore e = new Editore(nome, tempoConsegna, email);
        editori.put(nome, e);
        return e;
    }

    public Editore getEditore(String nome){
        return editori.get(nome);
    }

    public Collection<Editore> getEditori(){
        return editori.values().stream().sorted().collect(Collectors.toList());
    }

    public Libro creaLibro(String titolo, String autore, int anno, double prezzo, String nomeEditore)
    			throws EditoreInesistente {
        Editore e = editori.get(nomeEditore);
        if(e == null)
        	throw new EditoreInesistente();
        Libro l = new Libro(titolo, autore, anno, prezzo, e, this);
        libri.add(l);
        return l;
    }
    
    public Libro getLibro(String autore, String titolo){
    	if(autore == null && titolo == null)
    		throw new RuntimeException();
    	else if(autore == null)
    		return libri.stream().filter(l -> l.getTitolo().equals(titolo)).findAny().orElse(null);
    	else if(titolo == null)
    		return libri.stream().filter(l -> l.getAutore().equals(autore)).findAny().orElse(null);
    	else
    		return libri.stream().filter(l -> l.getAutore().equals(autore) 
    				&& l.getTitolo().equals(titolo)).findAny().orElse(null);
    }
    
    public Collection<Libro> getClassificaSettimana(final int settimana){
    	Collection<Libro> libri = venditeSettimanali.get(settimana);
    	if(libri == null)
    		return null;
        return libri.stream()
        		.collect(Collectors.groupingBy(Function.identity(),
        				Collectors.counting()))
        		.entrySet().stream()
        		.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        		.map(Entry::getKey)
        		.collect(Collectors.toList());
    }
    
    public Collection<Libro> getClassificaMese(final int mese){
    	Collection<Libro> libri = venditeMensili.get(mese);
    	if(libri == null)
    		return null;
        return libri.stream()
        		.collect(Collectors.groupingBy(Function.identity(),
        				Collectors.counting()))
        		.entrySet().stream()
        		.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
        		.map(Entry::getKey)
        		.collect(Collectors.toList());
    }
    
    public Collection<Ordine> getOrdini(){
        return ordini;
    }
    
    public void ordineRicevuto(int numOrdine){
    	Ordine o = ordini.get(numOrdine - 1);
    	if(o == null)
    		throw new RuntimeException();
    	o.setConsegnato();
    }
    
    public void leggi(String file) throws IOException {
    	try(BufferedReader r = new BufferedReader(new FileReader(file))) {
    		r.lines()
    		.map(l -> l.split(";"))
    		.forEach(line -> {
    			try {
					if(line[0].trim().equalsIgnoreCase("L")) {
						Libro l = creaLibro(line[1], line[2], Integer.parseInt(line[3]), Double.parseDouble(line[4]), line[5]);
						l.setQuantita(Integer.parseInt(line[6]));
					} else if(line[0].trim().equalsIgnoreCase("E")) {
						creaEditore(line[1], Integer.parseInt(line[2]), line[3]);
					} else {
						System.err.println("Unrecognized line type");
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					System.err.println("Out of bound exception");
				} catch(NumberFormatException e) {
					System.err.println("Number format exception");
				} catch (EditoreInesistente e) {
					System.err.println("Editore inesistente");
				}
    			
    		});
    		
    	}
    }
    
    public void effettuaOrdine(Libro libro, int quantita) {
    	Ordine o = new Ordine(ordini.size() + 1, libro, quantita);
    	ordini.add(o);
    }

	public void registraVendita(int settimana, int mese, Libro libro) {
		LinkedList<Libro> setLibri = venditeSettimanali.get(settimana);
		if(setLibri == null)
			setLibri = new LinkedList<>();
		setLibri.add(libro);
		venditeSettimanali.put(settimana, setLibri);

		setLibri = venditeMensili.get(mese);
		if(setLibri == null)
			setLibri = new LinkedList<>();
		setLibri.add(libro);
		venditeMensili.put(mese, setLibri);
	}
}
