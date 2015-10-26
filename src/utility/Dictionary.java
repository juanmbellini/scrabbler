package utility;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class represents a dictionary using a try as a data structure
 *
 */
public class Dictionary {
	Map<Character, LetterNode> root;
	
	public Dictionary() {
		root = new HashMap<Character, LetterNode>();
	}
	
	/**
	 * Evaluates if the dictionary is empty
	 * 
	 * @return <code>true</code> if the dictionary is empty, or <code>false</code> if not
	 */
	public boolean isEmpty() {
		return root.isEmpty();
	}
	
	
	/**
	 * Adds the specified word to the dictionary
	 * 
	 * @param word The word to be added
	 * @throw IllegalArgumentException When trying to add a word with more than 7 letters
	 */
	public void addWord(String word) {
		
		if (word.length() > 7) {
			throw new IllegalArgumentException("Words must have a mixumum length of 7 letters");
		}
		
		char [] aux = word.toUpperCase().toCharArray();	
		addWord(aux, 0, root);
	}
	
	private void addWord(char[] word, int i, Map<Character, LetterNode> nodes) {
		
		if (i == word.length) {
			if(nodes.get('#') == null) { // This means that the word was already added
				nodes.put('#', new LetterNode('#'));
			}
			return;
		}
		
		LetterNode next = nodes.get(word[i]);
		if(next == null) {
			next = new LetterNode(word[i]);
			nodes.put(word[i], next);
		}
		addWord(word, i + 1, next.next);	
	}
	
	/**
	 * Evaluates if the specified word is contained in the dictionary
	 * 
	 * @param word The word to be evaluated
	 * @return <code>true</code> if the dictionary contained the word, or <code>false</code> if not
	 */
	public boolean hasWord(String word) {
		
		if(word.length() > 7) {
			return false;
		}
		
		char [] aux = word.toUpperCase().toCharArray();
		return hasWord(aux, 0, root);
	}
	
	private boolean hasWord(char[] word, int i, Map<Character, LetterNode> nodes) {
		
		if(i == word.length) {
			return nodes.containsKey('#');
		}
		
		LetterNode aux = nodes.get(word[i]);
		if(aux == null) {
			return false;
		}
		return hasWord(word, i + 1, aux.next);
	}
	
	
	
	/**
	 * Returns a set of words that satisfies a letter position condition. For example, if your dictionary has the words
	 * "HELADO", "HORA", "HORARIO", "HOLA", "ARBOL", "AVION", and you want words that has in their first position an 'H',
	 * and in the fourth position an 'A', this method returns a set with the words("HORA", "HORARIO", "HELADO", "HOLA")
	 * 
	 * @param wordConditions The conditions that the words must satisfy
	 * @return A Set of words that satisfy the conditions
	 */
	public Set<String> giveMeWords(Collection<WordCondition> wordConditions) {
		
		ConditionsQueue queue = new ConditionsQueue();
		
		for (WordCondition each : wordConditions) {
			queue.enqueueCondition(each); // Exception is thrown in case there are two conditions for the same position
		}
		
		Set<String> result = new HashSet<String>();
		giveMeWords(queue, result, 0, new char[7], root); // Seven is the maximum word length
		return result;
	}
	
	
	
	private void giveMeWords(ConditionsQueue queue, Set<String> results, int currentPosition, 
			char[] word, Map<Character, LetterNode> nodes) {
		
		WordCondition currentCondition = queue.peekCondition();
		
		if (currentCondition == null || currentPosition != currentCondition.getPosition()) {
			
			// If currentCondition is null, there are no more conditions to be satisfied, so I must continue traveling through the trie searching
			// for words. If current condition is not null, but there is no condition for the current position, I also have to travel through
			// the trie, after I get a condition for the current position 
			
			for (Entry<Character, LetterNode> each : nodes.entrySet()) {	
				
				char auxLetter = each.getKey();	
				if (auxLetter != '#') {
					// The possible words continue, so I must check if the next letters of the possible words
					// satisfies the conditions
					word[currentPosition] = auxLetter;
					giveMeWords(queue, results, currentPosition + 1, word, each.getValue().next);
				} else {		
					// If a found an existing word, I must add it to the set even though it doesn't satisfy. It can be used
					// in a possible move
					String addingWord = String.valueOf(word, 0, currentPosition);
					results.add(addingWord);

				}
			}
			return;	
		}
		// If a get here, it means that there was a condition for the current position
			
		LetterNode aux = nodes.get(currentCondition.getLetter());
		if (aux == null) {
			return; // There is no word that satisfies the current letter condition
		}
		word[currentPosition] = aux.elem;
		currentCondition = queue.dequeueCondition();
		giveMeWords(queue, results, currentPosition + 1, word, aux.next);
		queue.returnConditionToTheQueue(currentCondition);
		return;
	
		
			
		
	}
	
	
	
	
	
	private static class LetterNode {
		char elem;
		Map<Character, LetterNode> next;
		
		public LetterNode(char c) {
			elem = c;
			next = (c == '#') ? null : new HashMap<>(); // Avoids creating a list in a finishing word node
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			
			if (obj == null) {
				return false;
			}
			
			if (obj.getClass() != this.getClass()) {
				return false;
			}
			
			LetterNode other = (LetterNode) obj;
			return (other.elem == this.elem); // Equality of nodes is defined by its element
		}
	}
}
