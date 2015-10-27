package main;

import general.BoardState;
import general.Validator;
import io.FileProcessor;

import java.io.IOException;
import java.util.Set;

import solving.BackTrackingSolver;
import solving.Solver;
import solving.StochasticHillClimbingSolver;
import utility.Dictionary;

public class Main {
	
	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println("Not enough arguments. Aborting.");
			System.exit(1);
		}
		String dictPath = args[0],
			lettersPath = args[1],
			outPath = args[2];
			boolean visual = false;
		long maxTime = -1;
		for(int i = 3; i < args.length; i++) {	//Handle optional parameters
			if(args[i].equals("-visual")) {
				visual = true;
			}
			else if(args[i].startsWith("-maxtime")) {
				try {
					maxTime = Long.parseLong(args[i+1])*1000;
					i++;	//Skip next parameter, it's the max time parameter which we just read
				}
				catch(NumberFormatException e) {
					System.out.println("Invalid max time format. Aborting.");
					System.exit(1);
				}
				catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("No max time specified. Ignoring time limit.");
					maxTime = -1;
				}
			}
			else {
				System.out.println("Skipping invalid parameter " + args[i]);
			}
		}
		Set<String> words = null;
		int[] letters = null;
		try {
			words = FileProcessor.processDictionaryFile(dictPath);
			if(words == null) {
				System.out.println("Error reading dictionary file. Aborting.");
				System.exit(-1);
			}
			letters = FileProcessor.processLettersFile(lettersPath);
			if(letters == null) {
				System.out.println("Error reading letters file. Aborting.");
				System.exit(-1);
			}
		} catch (IOException e) {
			System.out.println("I/O error reading input files: " + e.getMessage() + "\nAborting.");
			System.exit(1);
		}
				
		Solver solver = null;
		Dictionary dict = new Dictionary();
		for(String word : words) {
			dict.addWord(word);
		}
		Validator.setDictionary(dict);
		if(maxTime > 0) {
			solver = new StochasticHillClimbingSolver(dict, letters, visual, maxTime);
		}
		else {
			solver = new BackTrackingSolver(dict, letters, visual);
		}
		BoardState solution = solver.solve();
		try {
			FileProcessor.writeOutputFile(solution, outPath);
		} catch (IOException e) {
			System.out.println("I/O error writing output file: " + e.getMessage());
			System.out.println("I can show you the solution though! Here it is:");
			System.out.println(solution.toPrettyString());
			System.exit(1);
		}
	}
}
