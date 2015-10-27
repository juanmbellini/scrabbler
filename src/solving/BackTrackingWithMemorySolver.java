/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package solving;

import general.BoardState;
import general.Move;
import java.util.HashSet;
import java.util.Set;
import utility.Dictionary;

/**
 * Class used to find an exact solution to the problem via backtracking with
 * memory. By sacrificing memory performance, the algorithm knows which states
 * have been visited and avoids repeating them, greatly increasing time
 * performance when compared to ordinary backtracking.
 */
public class BackTrackingWithMemorySolver extends Solver {
    Set<String> visitedStates;
    
    public BackTrackingWithMemorySolver(Dictionary dictionary, int[] startingLetters, boolean visual) {
        super(dictionary, startingLetters, visual);
        visitedStates = new HashSet<>();
    }
    
    @Override
    public BoardState solve() {
        print("Initial board:\n" + best.toPrettyString());
        int count = 0;
        for(Move m : computeInitialMoves()) {
            visitedStates.clear();
            initial.doMove(m);
            visitedStates.add(initial.toString());
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
                print("\nNEW MAX SCORE: " + best.getScore() + "\n");
                if(!current.hasRemainingLetters()) {
                    return false;
                }
            }
            return true;
        }
        else {
            for(Move movement: movements) {
                current.doMove(movement);
                if(!visitedStates.contains(current.toString())) {
                    visitedStates.add(current.toString());
                    solve(current);
                }
                else {
                    print("\nAvoiding visited state.\n");
                }
                current.undoMove(movement);
            }
            return true;
        }
    }
}