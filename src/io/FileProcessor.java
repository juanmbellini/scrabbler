package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class created to parse words and dictionary files for the program to function.
 *
 */
public class FileProcessor {
	
	/**
	 * Parses the dictionary file, which contains one word per line.
	 * 
	 * @param path The path to the file.
	 * @return A set of valid words to be considered valid for the game.
	 * @throws IOException If a read error occurs.
	 */
	public static Set<String> processDictionaryFile(String path) throws IOException {
		BufferedReader r = null;
		Set<String> result = new HashSet<>();
        try {
            r = new BufferedReader(new FileReader(path));
            String line;
            while((line = r.readLine()) != null) {
                line = line.toUpperCase();
                if(isASCII(line)) {		//Ignore invalid words
                	result.add(line.toUpperCase());                	
                }
            }
        }
        finally {
        	if(r != null) r.close();
        }
        return result;
	}
	
	/**
	 * Processes the letters file, which contains all the letters available in
	 * one line.
	 * 
	 * @param path The path to the file.
	 * @return An {@code int[26]} containing how many of each letter the player
	 * has available at the start of the game.
	 * @throws IOException If a read error occurs.
	 */
	public static int[] processLettersFile(String path) throws IOException {
		BufferedReader r = null;
		int[] result = new int[26];
		int letterCount = 0;
        try {
            r = new BufferedReader(new FileReader(path));
            String line = r.readLine().toUpperCase();	//Read just 1 line of the file
            for(char c : line.toCharArray()) {
            	if(letterCount == 80) break;//Read only up to 80 letters
            	letterCount++;
            	if(c >= 'A' && c <= 'Z') {	//Ignore invalid letters
            		result[c-'A']++;
            	}
            }
        }
        finally {
            if(r != null) r.close();
        }
        return result;
	}
	
	/**
	 * Checks whether the specified String is formed of only standard ASCII
	 * characters ('A' to 'Z'). 
	 * 
	 * @param s The string to validate.
	 * @return {@code true} If every character in {@code s} is within 'A' and 'Z'.
	 */
	private static boolean isASCII(String s) {
		for(char c : s.toCharArray()) {
			if(c < 'A' || c > 'Z') return false;
		}
		return true;
	}
}
