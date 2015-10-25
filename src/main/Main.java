package main;

import java.util.HashSet;
import java.util.Set;

import general.Solver;
import general.Validator;

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
