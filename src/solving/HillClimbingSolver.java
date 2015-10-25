package solving;

import java.util.Random;
import java.util.Set;

import general.BoardState;


public class HillClimbingSolver extends TimedSolver {
	private Random r;
	private int T;
	
	public HillClimbingSolver(BoardState initial, long timeLimit, int T) {
		super(initial, timeLimit);
		r = new Random();
		this.T = T;
	}

	@Override
	protected BoardState solveWithTimeLimit() {
		solve(best, best.getScore());
		return best;
	}
	
	private void solve(BoardState current, int maxScore) {
		if(System.currentTimeMillis() >= endTime) {
			return;
		}
		Set<BoardState> neighbors = computeNeighbors(current);
		if(!current.hasRemainingLetters() || neighbors.isEmpty()) {
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

	
}
