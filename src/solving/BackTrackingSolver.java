package solving;

import java.util.HashSet;
import java.util.Set;

import general.BoardState;
import general.Move;
import general.BoardState.Direction;
import utility.Dictionary;
import utility.WordCondition;

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
		
		for(String word : dictionary.giveMeWords(new HashSet<WordCondition>())){
			for(int i=0 ; i<word.length(); i++){
				Move movement = new Move(word, 7 - i, 7, Direction.RIGHT);
				initial.doMove(movement);
				BoardState result = solve(initial);
				if(result.getScore() > best.getScore()) {
					best = new BoardState(result);
				}
				initial.undoMove(movement);
			}
		}
		print("\n===============\n");
		print("Optimal solution:\n" + best.toPrettyString());
		print("Score: " + best.getScore() + "\n");
		return best;
	}
	
	private BoardState solve(BoardState initial) {		
		print(initial.toPrettyString());
		Set<Move> movements = Helper.getPossibleMoves(initial, dictionary);
		if(movements.isEmpty()){
			if(initial.getScore() > best.getScore()){
				best = new BoardState(initial);
				print("NEW MAX SCORE: " + best.getScore() + "\n");
				try {
					Thread.sleep(2000);
				}
				catch(InterruptedException e) {}
			}
			return initial;
		}
		for(Move movement: movements){
			initial.doMove(movement);
			BoardState result = solve(initial);
			if(result.getScore() > best.getScore()){
				best = new BoardState(initial);
			}
			initial.undoMove(movement);
		}
		return initial;
	}

}
