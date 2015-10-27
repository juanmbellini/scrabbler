package solving;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import general.BoardState;
import general.BoardState.Direction;
import general.Move;
import gui.StateVisualizer;
import utility.Dictionary;
import utility.WordCondition;

/**
 * General class used to modularize different tactics of solving the same problem.  Each solver is given an initial board
 * state and can solve the problem of finding the optimal board state with its own strategy.
 */
public abstract class Solver {
	protected Dictionary dictionary;
	protected BoardState best, initial;
	protected StateVisualizer visualizer;
	
        /**
         * Creates a new solver with an initial board state and dictionary with
         * which to start solving.
         * 
         * @param dictionary The set of valid words.
         * @param startingLetters The letters assigned to the player initially.
         * @param visual Whether to run on visual mode or not.
         */
	public Solver(Dictionary dictionary, int[] startingLetters, boolean visual) {
		this.dictionary = dictionary;
		initial  = new BoardState(startingLetters);
		best = new BoardState(initial);
		if(visual) {
			visualizer = new StateVisualizer();
		}
	}
	
	/**
	 * Finds the board state that maximizes the points for the game.
	 * 
	 * @return The board state that maximizes points.
	 */
	public abstract BoardState solve();
	
	/**
	 * Computes the initial moves possible on this board. By default any word in any
	 * direction is a valid first move, since there are no words on the board.
	 * 
	 * @return A set of valid first moves from which the solving can start.
	 */
	protected Set<Move> computeInitialMoves() {
		Collection<String> words = dictionary.giveMeWords(new HashSet<WordCondition>());
		Collection<String> possibleWords = new HashSet<String>();
		Set<Move> result = new HashSet<Move>();
		
		int[] letters = new int[26];
		
		for (String word : words) {			
			letters = initial.getRemainingLetters().clone();
			
			boolean flag = false;
			char[] wordArry = word.toCharArray();
			for (int i = 0 ; i < wordArry.length && !flag ; i++) {
				int index = wordArry[i] - 'A';
				if (letters[index] == 0) {
					flag = true; // No more letters for this word, so it can't be a possible starting move
				} else {
					letters[index]--; // Gets rid of the used letter
				}
			}
			if (!flag) {
				possibleWords.add(word);
			}
		}
		
		//No need to validate moves; the board is empty so any word goes.
		for(String word : possibleWords){
			for(int i=0 ; i<word.length(); i++){
				Move m = new Move(word, 7 - i, 7, Direction.RIGHT);
				result.add(m);
			}
		}
		return result;
	}
	
	/**
	 * Shows the specified message on the solver's visualizer, if enabled.
	 * 
	 * @param message The message to show.
	 */
	protected void print(String message) {
		if(visualizer != null) {
			visualizer.print(message);
		}
	}
	public Dictionary getDictionary() {
		return dictionary;
	}
}