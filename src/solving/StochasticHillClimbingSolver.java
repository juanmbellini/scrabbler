package solving;

import general.BoardState;
import general.BoardState.Direction;
import general.Move;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import utility.Dictionary;
import utility.WordCondition;

/**
 * Class used to reach an approximate solution to the problem with a stochastic hill climbing strategy.
 */
public class StochasticHillClimbingSolver extends TimedSolver {
	private static final int OPTIMAL_T = 10;	//TODO TEST THIS! Find optimal value
	private Random r;
	private int T;
	
	public StochasticHillClimbingSolver(Dictionary dictionary, int[] startingLetters, boolean visual, int T , long timeLimit) {
		super(dictionary, startingLetters, visual, timeLimit);
		r = new Random();
		this.T = T;
	}

	public StochasticHillClimbingSolver(Dictionary dictionary, int[] startingLetters, boolean visual, long timeLimit) {
		this(dictionary, startingLetters, visual, OPTIMAL_T, timeLimit);
	}

	@Override
	protected BoardState solveWithTimeLimit() {
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
		Iterator<String> it = possibleWords.iterator();
		boolean hasMoreSolutions = true;
		while(hasMoreSolutions && it.hasNext()){
			String word = it.next();
			int random =(int) r.nextDouble()*word.length();
			Move movement = new Move(word, 7 - random,  7, Direction.RIGHT);
			initial.doMove(movement);
			hasMoreSolutions = solve(initial,best.getScore());
			initial.undoMove(movement);
		}
		return best;
	}
	
	private boolean solve(BoardState current, int maxScore) {
		if(System.currentTimeMillis() >= endTime) {
			print("Time limit reached.");
			return false;
		}
		print(current.toPrettyString());
		Set<BoardState> neighbors = computeNeighbors(current);
		if(!current.hasRemainingLetters() || neighbors.isEmpty()) {
			print("No more moves from here.");
			return true;
		}
		for(BoardState b : neighbors) {
			double probability = 1/( 1+Math.exp( (current.getScore()-b.getScore()) )/T );
			if(r.nextDouble() <= probability) {
				if(b.getScore() > maxScore) {
					best = new BoardState(b);
					maxScore = best.getScore();
				}
				return solve(b, maxScore);
			}
		}
		return true;
	}
	
	private Set<BoardState> computeNeighbors(BoardState b) {
		Set<BoardState> result = new HashSet<BoardState>();
		for(Move movement: Helper.getPossibleMoves(b, this.getDictionary())){
			BoardState clone = new BoardState(b);
			clone.doMove(movement);
			result.add(clone);
		}
		return result;
	}
}
