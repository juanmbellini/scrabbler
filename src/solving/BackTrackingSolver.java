package solving;

import general.BoardState;
import utility.Dictionary;

/**
 * Class used to find an exact solution to the problem with a backtracking strategy. 
 */
public class BackTrackingSolver extends Solver {

	public BackTrackingSolver(Dictionary dictionary, int[] startingLetters, boolean visual) {
		super(dictionary, startingLetters, visual);
	}

	@Override
	public BoardState solve() {
		print("Initial board:\n" + best.toPrettyString());
		for(Move m : initialMoves) {	//TODO do initial moves here
			best.doMove(m);
			solve(best);
		}
		print("\n===============\n");
		print("Optimal solution:\n" + best.toPrettyString());
		print("Score: " + best.getScore());
		return best;
	}
	
	private BoardState solve(BoardState initial) {
		print(initial.toPrettyString());
		if(initial.getScore() > best.getScore()) {
			best = new BoardState(initial);
		}
		//TODO solve
		return null;
	}

}
