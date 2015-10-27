package pruebasJM;

import java.util.Collection;
import java.util.HashSet;

import general.BoardState;
import solving.Solver;
import utility.Dictionary;
import utility.WordCondition;

public class BackTrackingSolver extends Solver {
	
	public BackTrackingSolver(Dictionary dictionary, int[] startingLetters, boolean visual) {
		super(dictionary, startingLetters, visual);
	}

	@Override
	public BoardState solve() {
		
		print("Initial board:\n" + best.toPrettyString());
		Collection<String> words = dictionary.giveMeWords(new HashSet<WordCondition>());
		Collection<String> possibleWords = new HashSet<String>();
		
		int[] letters = new int[26];
		
		for (String each : words) {
			
			for (int i = 0 ; i < initial.getRemainingLetters().length ; i++) {
				letters[i] = initial.getRemainingLetters()[i]; // Must do a copy of values because, if not, the real one gets modified
			}
			
			boolean flag = false;
			char[] actualWord = each.toCharArray();
			for (int i = 0 ; i < actualWord.length && !flag ; i++) {
				int actualLetter = actualWord[i] - '0';
				if (letters[actualLetter] == 0) {
					flag = true; // No more letters for this word, so it can't be a possible starting move
				} else {
					letters[actualLetter]--; // Gets off the used letter
				}
			}
			if (!flag) {
				possibleWords.add(each);
			}
		}
		
		
		
		
		return null;
	}
	
	

}
