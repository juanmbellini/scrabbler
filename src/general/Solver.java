package general;

import java.util.Set;

public class Solver {
	private Set<String> dictionary;
	private int[] letters;
	private char[][] bestAnswer;
	
	
	public Solver(int[] letters){
		this.letters=letters;
	}


	public Set<String> getDictionary() {
		return dictionary;
	}


	public int[] getLetters() {
		return letters;
	}


	public char[][] getBestAnswer() {
		return bestAnswer;
	}
}
