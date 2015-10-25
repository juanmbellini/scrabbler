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
		
		int[] letters = new int[] {4, 1, 2, 3, 1, 0, 0, 0, 0, 2, 3, 3, 3, 3, 0, 3, 0, 3, 3, 3, 0, 0, 0, 0, 0, 0};
		/*try {
			FileProcessor.writeOutputFile(new BoardState(letters), "OLA KE ASE.txt");
		} catch (IOException e) {
			System.out.println("I/O error.");
			e.printStackTrace();
		}
		System.exit(0);*/
		
		Solver solver = new Solver(dictionary);
		System.out.println(solver.backTracking(dictionary,letters));
		
	}

}
