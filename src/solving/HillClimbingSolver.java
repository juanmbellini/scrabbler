package solving;

import general.BoardState;


public class HillClimbingSolver extends TimedSolver {
	
	public HillClimbingSolver(BoardState initial, long timeLimit) {
		super(initial, timeLimit);
	}

	@Override
	protected BoardState solveWithTimeLimit() {
		if(System.currentTimeMillis() >= endTime) {
			return best;
		}
		//TODO solve it!
		return null;
	}

	
}
