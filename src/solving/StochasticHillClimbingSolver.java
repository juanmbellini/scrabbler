package solving;

import java.util.Random;
import java.util.Set;

import general.BoardState;

/**
 * Class used to reach an approximate solution to the problem with a stochastic hill climbing strategy.
 */
public class StochasticHillClimbingSolver extends TimedSolver {
	private static final int OPTIMAL_T = 10;	//TODO TEST THIS! Find optimal value
	private Random r;
	private int T;
	
	public StochasticHillClimbingSolver(BoardState initial, boolean visual, long timeLimit, int T) {
		super(initial, visual, timeLimit);
		r = new Random();
		this.T = T;
	}
	
	public StochasticHillClimbingSolver(BoardState initial, boolean visual, long timeLimit) {
		this(initial, visual, timeLimit, OPTIMAL_T);
	}

	@Override
	protected BoardState solveWithTimeLimit() {
		solve(best, best.getScore());
		return best;
	}
	
	private void solve(BoardState current, int maxScore) {
		if(System.currentTimeMillis() >= endTime) {
			print("Time limit reached.");
			return;
		}
		print(current.toPrettyString());
		Set<BoardState> neighbors = computeNeighbors(current);
		if(!current.hasRemainingLetters() || neighbors.isEmpty()) {
			print("No more moves from here.");
			return;
		}
		for(BoardState b : neighbors) {
			double probability = 1/( 1+Math.exp( (current.getScore()-b.getScore()) )/T );
			if(r.nextDouble() <= probability) {
				if(b.getScore() > maxScore) {
					best = b;
					maxScore = b.getScore();
				}
				solve(b, maxScore);
			}
		}
	}
	
	private Set<BoardState> computeNeighbors(BoardState b) {
		return null;	//TODO
	}

	@Override
	protected BoardState solve(BoardState initial) {
		// TODO Auto-generated method stub
		return null;
	}
}
