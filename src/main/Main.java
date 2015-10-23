package main;

import general.BoardState;
import general.Solver;
import general.Validator;
import gui.StateVisualizer;

import java.util.HashSet;
import java.util.Set;

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
		
		int[] letters = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
		Solver solver = new Solver(dictionary);
		System.out.println(solver.backTracking(dictionary,letters));
		
	}

}
