package pruebasJM;

import general.BoardState;
import general.Move;

import java.util.Collection;

import solving.Helper;
import solving.Solver;
import utility.Dictionary;

public class BackTrackingSolver extends Solver {
	
	public BackTrackingSolver(Dictionary dictionary, int[] startingLetters, boolean visual) {
		super(dictionary, startingLetters, visual);
	}

	@Override
	public BoardState solve() {
		
		print("Initial board:\n");
		//Collection<String> words = dictionary.giveMeWords(new HashSet<WordCondition>());
		//Collection<String> possibleWords = getPossibleStartingWords(words);
		
		solve(initial);
		return best;
		
	}
	
	
	private boolean solve(BoardState initial) {
		
		print(initial.toPrettyString());
		Collection<Move> movements = Helper.getPossibleMoves(initial, dictionary);
		if (movements.isEmpty()) {
			if (initial.getScore() > best.getScore()) {
				best = new BoardState(initial);
				print("NEW MAX SCORE: " + best.getScore() + "\n");
				//if (best.getScore() == maxPossibleScore) {
					//return true; --> Devuelve que encontró la mejor solucion posible (la que tiene un puntaje utilizando todas las letras
				//}
			}
		}
		for (Move each : movements) {
			initial.doMove(each);
			if (solve(initial)) {
				return true;
			}
			initial.undoMove(each);
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
