package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import utility.Dictionary;
import utility.WordCondition;

public class DictionaryTester {
	
	private static Dictionary dictionary;
	
	@BeforeClass
	public static void setUp() {
		
		dictionary = new Dictionary();
		dictionary.addWord("Hola");
		dictionary.addWord("Hora");
		dictionary.addWord("Horas");
		dictionary.addWord("Horario");
		dictionary.addWord("Helado");
		dictionary.addWord("Arbol");
		dictionary.addWord("Avion");
		dictionary.addWord("Aviones");
		dictionary.addWord("Hoy");
		
		
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
	public void GiveMeWordsTest() {
		
		Set<WordCondition> set = new HashSet<WordCondition>();
		set.add(new WordCondition(0, 'H'));
		set.add(new WordCondition(3, 'A'));
		set.add(new WordCondition(4, 'D'));
		
		Set<String> result = dictionary.giveMeWords(set);
		Assert.assertTrue(result.contains("HOLA") && result.contains("HORA")
				&& !result.contains("HORARIO") && result.contains("HELADO")
				&& !result.contains("ARBOL") && !result.contains("HORAS"));
		
	}
	
	

}
