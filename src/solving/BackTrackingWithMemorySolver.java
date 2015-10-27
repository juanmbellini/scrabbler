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
 *
 * @author jlipumafinnemore
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
            System.out.println("Base movement #" + (++count));
            initial.doMove(m);
            visitedStates.add(initial.toString());
            solve(initial);
            initial.undoMove(m);
        }
        print("\n==============================\n");
        print("Optimal solution:");
        print("\n==============================\n");
        print(best.toPrettyString());
        print("Score: " + best.getScore() + "\n");
        return best;
    }
    
    private void solve(BoardState initial) {
        print(initial.toPrettyString());
        Set<Move> movements = Helper.getPossibleMoves(initial, dictionary);
        if(movements.isEmpty()){
            if(initial.getScore() > best.getScore()){
                best = new BoardState(initial);
                System.out.println("NEW MAX SCORE: " + best.getScore() + "\n");
                print("NEW MAX SCORE: " + best.getScore() + "\n");
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e) {}
            }
            return;
        }
        for(Move movement: movements){
            initial.doMove(movement);
            if(!visitedStates.contains(initial.toString())) {
                visitedStates.add(initial.toString());
                solve(initial);
            }
            else {
                print("Avoiding visited state.\n");
                /*try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e) {}*/
            }
            initial.undoMove(movement);
        }
    }
}