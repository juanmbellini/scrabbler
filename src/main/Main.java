package main;

import java.util.HashSet;
import java.util.Set;

import general.BoardState;
import general.BoardState.Direction;
import general.Move;
import general.Validator;
import gui.StateVisualizer;
import solving.Helper;

public class Main {

	public static void main(String[] args) {
		Set<String> dictionary = new HashSet<>();
		dictionary.add("AUTO");
		dictionary.add("CASA");
		dictionary.add("TABLA");
		dictionary.add("PELO");
		dictionary.add("LIBRO");
		dictionary.add("MONITOR");
		dictionary.add("QUESO");
		Validator.setDictionary(dictionary);
		StateVisualizer v = new StateVisualizer();
		
		BoardState b = new BoardState(new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3});
		Move m1 = new Move("CASA", 5, 0, Direction.DOWN);
		System.out.println("Moving: " + m1);
		b.doMove(m1);
		v.print(b.toPrettyString());
		Set<Move> moves = Helper.getPossibleMoves(b, dictionary);
		Move m2 = new Move("TABLA", 4, 3, Direction.RIGHT);
		assert moves.contains(m2);
		b.doMove(m2);
		
		for(int i = 0; i < 100; i++) {
			v.print(b.toPrettyString());
		}
	}

}
