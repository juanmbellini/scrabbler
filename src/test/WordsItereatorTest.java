package test;

import static org.junit.Assert.*;
import general.PossibleWordsIterator;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class WordsItereatorTest {
	
	private Set<String>	dictionary = new HashSet<String>();
	private int[] letters = {5,6,1,0,5,0,4,3,5,1,4,2,5,2,2,1,3,4,2,1,0,0,0,0,0,0};

	
	
	@Test
	public void test() {
		dictionary.add("CASA");
		dictionary.add("JORGE");
		dictionary.add("AAAAA");
		dictionary.add("AAAAAA");
		dictionary.add("MAMA");
		dictionary.add("MAAA");
		dictionary.add("SARASA");
		dictionary.add("SARASAA");
		dictionary.add("SARAKA");
		dictionary.add("SARAJA");
		PossibleWordsIterator iterator = new PossibleWordsIterator(dictionary.iterator(), letters);
		
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}

	}
	
	
	
}
