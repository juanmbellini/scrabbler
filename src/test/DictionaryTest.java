package test;

import java.io.IOException;
import java.util.Set;

import io.FileProcessor;

public class DictionaryTest {

	public static void main(String[] args) {
		try {			
			Set<String> dictionary = FileProcessor.processDictionaryFile("words.txt");
			System.out.println("Words: ");
			for(String word : dictionary) {
				System.out.println(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}