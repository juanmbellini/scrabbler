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
		
		for(Move m : computeInitialMoves()) {
			if(System.currentTimeMillis() >= endTime) {
				print("Time limit reached.");
				break;
			}
			initial.doMove(m);
			solve(initial);
			initial.undoMove(m);
		}
		return best;
	}
	
	/**
	 * Override behavior to generate one move per starting word, with a random first position.
	 */
	@Override
	protected Set<Move> computeInitialMoves() {
		Collection<String> words = dictionary.giveMeWords(new HashSet<WordCondition>());
		Collection<String> possibleWords = new HashSet<String>();
		Set<Move> result = new HashSet<>();
		int[] letters = new int[26];
		
		for (String each : words) {
			letters = initial.getRemainingLetters().clone();
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
		while(hasMoreSolutions && it.hasNext()) {
			String word = it.next();
			int random =(int) r.nextDouble()*word.length();
			Move movement = new Move(word, 7 - random,  7, Direction.RIGHT);
			result.add(movement);
		}
		return result;
	}

	private void solve(BoardState current) {
		if(System.currentTimeMillis() >= endTime) {
			return;
		}
		print(current.toPrettyString());
		Set<BoardState> neighbors = computeNeighbors(current);
		if(!current.hasRemainingLetters() || neighbors.isEmpty()) {
			print("No more moves from here.");
			return;
		}
		BoardState backup = neighbors.iterator().next();	//In the rare case no probability works, go with the first
		for(BoardState b : neighbors) {
			double probability = 1/( 1+Math.exp( (current.getScore()-b.getScore()) )/T );
			if(r.nextDouble() <= probability) {
				if(b.getScore() > best.getScore()) {
					best = new BoardState(b);
					print("NEW MAX SCORE: " + best.getScore() + "\n");
				}
				solve(b);
			}
		}
		solve(backup);
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
