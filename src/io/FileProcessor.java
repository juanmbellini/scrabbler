package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FileProcessor {
	
	/**
	 * Parses the dictionary file, which contains one word per line.
	 * 
	 * @param path The path to the file.
	 * @return A set of words to be considered valid for the game.
	 * @throws IOException If a read error occurs.
	 */
	public static Set<String> processDictionaryFile(String path) throws IOException {
		BufferedReader r = null;
		Set<String> result = new HashSet<>();
        try {
            r = new BufferedReader(new FileReader(path));
            String line;
            while((line = r.readLine()) != null) {
                result.add(line.toUpperCase());
            }
        }
        finally {
            if(r != null) r.close();
        }
        return result;
	}
	
	/**
	 * Processes the letters file, which contains all the letters available
	 * in one line.
	 * 
	 * @param path The path to the file.
	 * @return An int[] array of 26 elements containing how many of each letter
	 * the player has available at the start of the game.
	 * @throws IOException If a read error occurs.
	 */
	public static int[] processLettersFile(String path) throws IOException {
		BufferedReader r = null;
		int[] result = new int[26];
        try {
            r = new BufferedReader(new FileReader(path));
            String line = r.readLine();	//Just read 1 line
            for(char c : line.toCharArray()) {
            	c = Character.toUpperCase(c);
            	result[c-'A']++;
            }
        }
        finally {
            if(r != null) r.close();
        }
        return result;
	}
}
