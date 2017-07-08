package it.polito.po;

public class Quiz {
	final static public String[] questions = {
	"How does SVN resolve conflicts?",
	"What is specified in the middle section of a UML class?",
	"What is the main difference between verification and validation?"
	};
	final static public String[][] options = {
	{
		"Copy-Modify-Merge",
		"Lock-Modify-Unlock",
		"Check-out/Commit",
		"Check-out/Check-in",
		"Modify-Commit-Unlock"	},
	{
		"List of interfaces",
		"Name of the package",
		"List of static attributes",
		"Implementation details",
		"List of attributes"	},
	{
		"One is useful, the other is deprecated",
		"Both are carried out by testing teams",
		"One is white-box, the other is black-box",
		"One determines the quality, the other ensures that the system is useful",
		"There are no differences whatsoever"	}
	};
	
	/**
	 * Return the index of the right answer(s) for the given question 
	 */
	public static int[] answer(int question){
		// TODO: answer the question
		
		switch(question){
			case 0: return null; // replace with your answers
			case 1: return null; // replace with your answers
			case 2: return null; // replace with your answers
		}
		return null; // means: "No answer"
	}

	/**
	 * When executed will show the answers you selected
	 */
	public static void main(String[] args){
		for(int q=0; q<questions.length; ++q){
			System.out.println("Question: " + questions[q]);
			int[] a = answer(q);
			if(a==null || a.length==0){
				System.out.println("<undefined>");
				continue;
			}
			System.out.println("Answer" + (a.length>1?"s":"") + ":" );
			for(int i=0; i<a.length; ++i){
				System.out.println(a[i] + " - " + options[q][a[i]]);
			}
		}
	}
}

