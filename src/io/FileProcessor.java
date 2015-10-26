package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import general.BoardState;

/**
 * Class created to parse words and dictionary files for the program to function.
 *
 */
public class FileProcessor {
	
	/**
	 * Parses the dictionary file, which contains one word per line.
	 * 
	 * @param path The path to the file.
	 * @return A set of valid words to be considered valid for the game, or {@code null}
	 * on error.
	 * @throws IOException If the read stream can't be closed.
	 */
	public static Set<String> processDictionaryFile(String path) throws IOException {
		BufferedReader r = null;
		Set<String> result = new HashSet<>();
        try {
            r = new BufferedReader(new FileReader(path));
            String line;
            while((line = r.readLine()) != null) {
                line = line.toUpperCase();
                int len = line.length();
                if(isASCII(line) && len >= 2 && len <= 7) {		//Ignore invalid words
                	result.add(line.toUpperCase());                	
                }
            }
        }
        catch(IOException e) {
        	result = null;
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
	 * has available at the start of the game, or {@code null} on error.
	 * @throws IOException If the read stream can't be closed.
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
        catch(IOException e) {
        	result = null;
        }
        finally {
            if(r != null) r.close();
        }
        return result;
	}
	
	/**
	 * Writes the pretty version of the specified board state to a file with the specified path.
	 * 
	 * @param board The board to write.
	 * @param path The path of the output file.
	 * @return {@code true} If the write was completed successfully, {@code false} on error.
	 * @throws IOException If the write stream can't be closed.
	 */
	public static boolean writeOutputFile(BoardState board, String path) throws IOException {
		boolean success = true;
		FileWriter w = null;
		try {
			w = new FileWriter(path);
			for(String row : board.toPrettyString().split("\n")) {
				w.write(row);
				w.write(System.getProperty("line.separator"));	//Windows newline != linux newline
			}
		}
		catch(IOException e) {
			success = false;
		}
		finally {
			if(w != null) w.close();			
		}
		return success;
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
