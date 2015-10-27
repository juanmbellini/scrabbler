package solving;

import general.BoardState;
import general.Move;
import java.util.Set;
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
        int count = 0;
        for(Move m : computeInitialMoves()) {
            initial.doMove(m);
            if(!solve(initial)) {
                break;  //Absolute maximum found, stop
            }
            initial.undoMove(m);
        }
        print("\n==============================\n");
        print("Optimal solution:");
        print("\n==============================\n");
        print(best.toPrettyString());
        print("Score: " + best.getScore() + "\n");
        return best;
    }
    
    /**
     * Solves the problem recursively.
     * 
     * @param current The current board state.
     * @return {@code true} If the solving should continue, {@code false} if an
     * absolute maximum has been found and the solving should stop.
     */
    private boolean solve(BoardState current) {
        print(current.toPrettyString());
        Set<Move> movements = Helper.getPossibleMoves(current, dictionary);
        if(movements.isEmpty()){
            if(current.getScore() > best.getScore()){
                best = new BoardState(current);
                print("NEW MAX SCORE: " + best.getScore() + "\n");
                if(!current.hasRemainingLetters()) {
                    return false;
                }
            }
            return true;
        }
        for(Move movement: movements) {
            current.doMove(movement);
            boolean result = solve(current);
            current.undoMove(movement);
            return result;
        }
        return true;
    }
}
