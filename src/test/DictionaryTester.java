package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.Dictionary;

public class DictionaryTester {
	
	private static Dictionary dictionary;
	
	@BeforeClass
	public static void setUp() {
		
		dictionary = new Dictionary();
		dictionary.addWord("Hola");
		dictionary.addWord("Hora");
		dictionary.addWord("Horario");
		dictionary.addWord("Helado");
		dictionary.addWord("Arbol");
		dictionary.addWord("Aviones");
		
	}
	
	@Test
	public void firstHasWordTest() {
		assertTrue(dictionary.hasWord("Hola"));
	}
	
	@Test
	public void secondHasWordTest() {
		assertTrue(dictionary.hasWord("Horario"));
	}
	
	@Test
	public void thirdHasWordTest() {
		assertFalse(dictionary.hasWord("Arboles"));
	}
	
	@Test
	public void fourthHasWordTest() {
		assertFalse(dictionary.hasWord("Avion"));
	}
	
	
	

}
