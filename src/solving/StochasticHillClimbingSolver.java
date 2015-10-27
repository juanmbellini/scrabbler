package solving;

import general.BoardState;
import general.Move;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import utility.Dictionary;

/**
 * Class used to reach an approximate solution to the problem with a stochastic hill climbing strategy.
 */
public class StochasticHillClimbingSolver extends TimedSolver {
	private static final int OPTIMAL_T = 5;	//TODO TEST THIS! Find optimal value
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
		Iterator<Move> movements = computeInitialMoves().iterator();
		boolean hasMovementsLeft = true;
			while(movements.hasNext() && hasMovementsLeft && System.currentTimeMillis() < endTime){
				Move m = movements.next();
				initial.doMove(m);
				hasMovementsLeft = solve(initial);
				initial.undoMove(m);
			}
			print("\n");
			print("\n");
			print("\n");
			print("Board with best score:");
			print(best.toPrettyString());
			print("Score:" + best.getScore());

		return best;
	}

	private boolean solve(BoardState current) {
		if(System.currentTimeMillis() >= endTime) {
			return false;
		}
		print(current.toPrettyString());
		if(!current.hasRemainingLetters()){
			print("Best solution found");
			return false;
		}
		Set<BoardState> neighbors = computeNeighbors(current);
		if(neighbors.isEmpty()) {
			print("No more moves from here.");
			return true;
		}
		BoardState backup = neighbors.iterator().next();	//In the rare case no probability works, go with the first
		for(BoardState b : neighbors) {
			double probability = 1/( 1+Math.exp( (current.getScore()-b.getScore()) )/T );
			if(r.nextDouble() <= probability) {
				if(b.getScore() > best.getScore()) {
					best = new BoardState(b);
					print("NEW MAX SCORE: " + best.getScore() + "\n");
				}
				return solve(b);
			}
		}
		return solve(backup);
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