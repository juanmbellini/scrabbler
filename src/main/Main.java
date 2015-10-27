package main;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import general.BoardState;
import general.Validator;
import io.FileProcessor;
import solving.BackTrackingSolver;
import solving.Solver;
import solving.StochasticHillClimbingSolver;

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
			}
		}
		//TODO validate parameters?
		
		Set<String> dictionary = null;
		int[] letters = null;
		try {
			dictionary = FileProcessor.processDictionaryFile(dictPath);
			if(dictionary == null) {
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
		if(maxTime > 0) {
			solver = new StochasticHillClimbingSolver(new BoardState(letters), visual, maxTime);
		}
		else {
			solver = new BackTrackingSolver(new BoardState(letters), visual);
		}
		//BoardState solution = solver.solve(); TODO uncomment
		general.Solver s = new general.Solver(new utility.Dictionary());
		Set<String> uglyDict = new HashSet<String>();
		for(String s2 : dictionary) {
			s.getDictionary().addWord(s2);
			uglyDict.add(s2);
		}
		Validator.setDictionary(uglyDict);
		BoardState solution = s.backTracking(s.getDictionary(), letters);
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
