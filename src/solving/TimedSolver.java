package solving;

import general.BoardState;
import utility.Dictionary;

/**
 * Class used to modularize different tactics of solving the same problem, but with a time limit.  Each solver is given an
 * initial board state and returns the best solution found in the given time limit.
 */
public abstract class TimedSolver extends Solver {
	protected long timeLimit, endTime;
	
	public TimedSolver(Dictionary dictionary, int[] startingLetters, boolean visual) {
		super(dictionary, startingLetters, visual);
		this.timeLimit = timeLimit;
	}

	@Override
	public BoardState solve() {
		endTime = System.currentTimeMillis() + timeLimit;
		return solveWithTimeLimit();
	}

	protected abstract BoardState solveWithTimeLimit();
}
