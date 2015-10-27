package solving;

import general.BoardState;
import general.BoardState.Direction;
import general.Move;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import utility.Dictionary;
import utility.WordCondition;

/**
 * Class used to find an exact solution to the problem with a backtracking strategy. 
 */
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
				int actualLetter = actualWord[i] - 'A';
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
		
		
		for(String word : possibleWords){
			for(int i=0 ; i<word.length(); i++){
				Move movement = new Move(word, 7 - i, 7, Direction.RIGHT);
				initial.doMove(movement);
				solve(initial);
				initial.undoMove(movement);
			}
		}
		print("\n==============================\n");
		print("Optimal solution:");
		print("\n==============================\n");
		print(best.toPrettyString());
		print("Score: " + best.getScore() + "\n");
		return best;
	}
	
	private void solve(BoardState initial) {		
		print(initial.toPrettyString());
		Set<Move> movements = Helper.getPossibleMoves(initial, dictionary);
		if(movements.isEmpty()){
			if(initial.getScore() > best.getScore()){
				best = new BoardState(initial);
				print("NEW MAX SCORE: " + best.getScore() + "\n");
				try {
					Thread.sleep(2000);
				}
				catch(InterruptedException e) {}
			}
			return;
		}
		for(Move movement: movements){
			initial.doMove(movement);
			solve(initial);
			initial.undoMove(movement);
		}
		return;
	}

}
