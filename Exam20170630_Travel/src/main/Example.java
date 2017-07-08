package main;
import java.util.*;

import travels.*;

public class Example {
	public static void printQuote(Quote q) {
		System.out.println(String.format("winning quote: proposal %s, operator %s, amount %d, n. of choices %d",	
			q.getProposalName(), q.getOperatorName(), q.getAmount(), q.getNChoices()));
	}
	
public static void main(String[] args) throws TravelException {
	TravelHandling ph = new TravelHandling();	
//R1
	ph.addCustomers("john","mary","ann","bob","ted","lucy");
	ph.addDestination("london", "op2","op1","op3");
	ph.addDestination("berlin", "op1","op2","op4","op5");
	ph.addDestination("madrid", "op1","op4","op5");
	ph.addDestination("rome", "op10","op3","op5");
	try{ph.addDestination("berlin");}
	catch(TravelException ex) {System.out.println(ex.getMessage());}
	System.out.println(ph.getDestinations("op1")); 	//[berlin, london, madrid]
//R2
	ph.addProposal("pro1", "london");
	ph.addProposal("pro2", "berlin");
	ph.addProposal("pro3", "madrid");
	ph.addProposal("pro4", "rome");
	try{ph.addProposal("pro5","prague");}
	catch(TravelException ex) {System.out.println(ex.getMessage());}
	List<String> list = ph.setUsers("pro1", "john","mary","ann","bob", "tom", "linda");
		System.out.println(list); //[linda, tom]
	list = ph.setUsers("pro2", "ann","bob");
		System.out.println(list); //[]
	list = ph.setUsers("pro3", "ann","ted","lucy");
		System.out.println(list); //[]
//R3	
	list = ph.setOperators("pro1", "op1","op2", "op3", "op20", "op4"); 
		System.out.println(list); //[op20, op4]
	list = ph.setOperators("pro3", "op5","op4", "op1"); 
		System.out.println(list); //[]
	ph.addQuote("pro1", "op3", 1000);
	ph.addQuote("pro1", "op1", 1200);
	ph.addQuote("pro1", "op2", 2000);
	try{ph.addQuote("pro1","op4",3000);}
	catch(TravelException ex) {System.out.println(ex.getMessage());}
	List<Quote> quotes = ph.getQuotes("pro1");
		System.out.println(quotes.get(0).getOperatorName()); //op1
//R4
	ph.makeChoice("pro1", "john", "op1"); ph.makeChoice("pro1", "ann", "op1");
	ph.makeChoice("pro1", "bob", "op2"); ph.makeChoice("pro1", "mary", "op3");
	try{ph.makeChoice("pro3","ted","op5");}
	catch(TravelException ex) {System.out.println(ex.getMessage());}	
	Quote q = ph.getWinningQuote("pro1");
	printQuote(q); //winning quote: proposal pro1, operator op1, amount 1200, n. of choices 2
	ph.addQuote("pro3", "op1", 1500);
	ph.addQuote("pro3", "op4", 1800);
	ph.makeChoice("pro3", "ann", "op1"); ph.makeChoice("pro3", "lucy", "op1");
	ph.makeChoice("pro3", "ted", "op4");
	q = ph.getWinningQuote("pro3");
	printQuote(q); //winning quote: proposal pro3, operator op1, amount 1500, n. of choices 2
//R5
	SortedMap<String, Integer> quotesPerDest = ph.totalAmountOfQuotesPerDestination();
	System.out.println("quotesPerDest " + quotesPerDest); //quotesPerDest {london=4200, madrid=3300}
	SortedMap<Integer, List<String>> oprPerNQuotes = ph.operatorsPerNumberOfQuotes();
	System.out.println("oprPerNQuotes " + oprPerNQuotes);  //oprPerNQuotes {2=[op1], 1=[op2, op3, op4]}
	SortedMap<String, Long> nUsersPerDest = ph.numberOfUsersPerDestination();
	System.out.println("nUsersPerDest " + nUsersPerDest);  //nUsersPerDest {berlin=2, london=4, madrid=3}
}

}
