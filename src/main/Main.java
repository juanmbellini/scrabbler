package main;

import java.util.HashSet;
import java.util.Set;

import general.Board;
import general.Board.Direction;
import general.Validator;
import gui.StateVisualizer;

public class Main {

	public static void main(String[] args) {
		Set<String> dictionary = new HashSet<>();
			dictionary.add("auto");
			dictionary.add("casa");
			dictionary.add("tabla");
			dictionary.add("pelo");
			dictionary.add("libro");
			dictionary.add("monitor");
			dictionary.add("queso");
		Validator.setDictionary(dictionary);
		StateVisualizer v = new StateVisualizer();
		v.initialize();
		
		Board b = new Board();
		b.placeWord("CASA", 5, 0, Direction.DOWN);
		b.placeWord("TABLA", 4, 3, Direction.RIGHT);
		v.print(b.toPrettyString());
	}

}
