package solving;

import general.BoardState;
import gui.StateVisualizer;

/**
 * General class used to modularize different tactics of solving the same problem.  Each solver is given an initial board
 * state and can solve the problem of finding the optimal board state with its own strategy.
 */
public abstract class Solver {
	protected BoardState best;
	protected StateVisualizer visualizer;
	
	/**
	 * Creates a new solver with an initial board state from which to start solving.
	 * 
	 * @param initial The starting board state.
	 */
	public Solver(BoardState initial, boolean visual) {
		best = initial;
		if(visual) {
			visualizer = new StateVisualizer();
		}
	}
	
	/**
	 * Finds the board state that maximizes the points for the game.
	 * 
	 * @return The board state that maximizes points.
	 */
	public final BoardState solve() {
		print("Initial board:\n" + best.toPrettyString());
		return solve(best);
	}
	
	protected abstract BoardState solve(BoardState initial);
	
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

}
