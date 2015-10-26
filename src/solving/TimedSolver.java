package solving;

import general.BoardState;

/**
 * Class used to modularize different tactics of solving the same problem, but with a time limit.  Each solver is given an
 * initial board state and returns the best solution found in the given time limit.
 */
public abstract class TimedSolver extends Solver {
	protected long timeLimit, endTime;
	
	public TimedSolver(BoardState initial, boolean visual, long timeLimit) {
		super(initial, visual);
		this.timeLimit = timeLimit;
	}
	
	@Override
	protected BoardState solve(BoardState initial) {
		endTime = System.currentTimeMillis() + timeLimit;
		return solveWithTimeLimit();
	}

	protected abstract BoardState solveWithTimeLimit();
}
