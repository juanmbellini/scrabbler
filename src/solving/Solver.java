package solving;

import general.BoardState;

/**
 * General class used to modularize different tactics of solving the same problem.  Each solver is given an initial board
 * state and can solve the problem of finding the optimal board state with its own strategy.
 */
public abstract class Solver {
	protected BoardState best;
	
	/**
	 * Creates a new solver with an initial board state from which to start solving.
	 * 
	 * @param initial The starting board state.
	 */
	public Solver(BoardState initial) {
		best = initial;
	}
	
	/**
	 * Finds the board state that maximizes the points for the game.
	 * 
	 * @return The board state that maximizes points.
	 */
	public abstract BoardState solve();

}
