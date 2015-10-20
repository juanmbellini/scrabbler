package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.FileProcessor;

public class FileProcessorTester {
	private static final String DICTIONARY_FILE = "testDictionary.txt";
	private static final String LETTERS_FILE = "testLetters.txt";
	
	
	@Test
	public void testProcessDictionaryFile() {
		try {			
			int validWords = writeTestDictionaryFile();
			Set<String> dictionary = FileProcessor.processDictionaryFile(DICTIONARY_FILE);
			System.out.println("Words: ");
			for(String word : dictionary) {
				System.out.println(word);
			}
			deleteFile(DICTIONARY_FILE);
			assertEquals(validWords, dictionary.size());
		} catch (IOException e) {
			fail("I/O error: " + e.getMessage());
		}
	}

	@Test
	public void testProcessLettersFile() {
		try {			
			writeTestLettersFile();
			int[] processedLetters = FileProcessor.processLettersFile(LETTERS_FILE);
			int sum = 0;
			assertEquals("Letters array is not 26 elements long", 26, processedLetters.length);
			System.out.println("Letters:");
			for(int i = 0; i < processedLetters.length; i++) {
				System.out.println(Character.toString((char) ('A'+i)) + ": " + processedLetters[i]);
				sum += processedLetters[i];
			}
			deleteFile(LETTERS_FILE);
			assertTrue("More than 80 letters were assigned", sum <= 80);
		} catch (IOException e) {
			fail("I/O error: " + e.getMessage());
		}
	}
	
	/**
	 * Writes a test dictionary file with some valid and some invalid words.
	 * 
	 * @return The number of valid words written.
	 * @throws IOException If an I/O error occurs.
	 */
	private int writeTestDictionaryFile() throws IOException {
		FileWriter w = new FileWriter(DICTIONARY_FILE);
		Set<String> validWords = new HashSet<>();
			validWords.add("auto");
			validWords.add("casa");
			validWords.add("tabla");
			validWords.add("pelo");
			validWords.add("libro");
			validWords.add("monitor");
			validWords.add("queso");
		Set<String> invalidWords = new HashSet<>();
			invalidWords.add("ñoño");
			invalidWords.add("multi palabra");
			invalidWords.add("éxito");
			invalidWords.add("3");
		for(String s : validWords) {
			w.write(s + '\n');
		}
		for(String s : invalidWords) {
			w.write(s + '\n');
		}
		w.close();
		return validWords.size();
	}
	
	/**
	 * Writes a test letters file with some valid and some invalid letters.
	 * 
	 * @return An {@code int[26]} containing how many of each letter was written.
	 * @throws IOException If an I/O error occurs.
	 */
	private int[] writeTestLettersFile() throws IOException {
		FileWriter w = new FileWriter(LETTERS_FILE);
		int[] result = {3, 1, 1, 4, 0, 0, 0, 0, 4, 3, 7, 1, 1, 1, 2, 4, 1, 1, 2, 1, 1, 2, 1, 1, 1, 60};
		
		for(int i = 0; i < result.length; i++) {
			for(int j = result[i]; j >= 0; j--) {
				w.write('A'+i);
			}
		}
		w.close();
		return result;
	}
	
	private void deleteFile(String path) {
		File f = new File(path);
		f.delete();
		return;
	}

}
