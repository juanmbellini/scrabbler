package utility;

import java.util.HashMap;
import java.util.Map;

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
	 */
	public void addWord(String word) {
		char [] aux = word.toUpperCase().toCharArray();	
		addWord(aux, 0, root);
	}
	
	private void addWord(char[] word, int i, Map<Character, LetterNode> nodes) {
		if(i == word.length-1) {
			if(nodes.get('#') == null) {
				nodes.put('#', new LetterNode('#'));
			}
			return;
		}
		
		LetterNode next = nodes.get(word[i]);
		if(next == null) {
			next = new LetterNode(word[i]);
			nodes.put(word[i], next);
		}
		addWord(word, i+1, next.next);	
	}
	
	/**
	 * Evaluates if the specified word is contained in the dictionary
	 * 
	 * @param word The word to be evaluated
	 * @return <code>true</code> if the dictionary contained the word, or <code>false</code> if not
	 */
	public boolean hasWord(String word) {
		char [] aux = word.toUpperCase().toCharArray();
		return hasWord(aux, 0, root);
	}
	
	private boolean hasWord(char[] word, int i, Map<Character, LetterNode> nodes) {
		if(i == word.length-1) {
			return nodes.containsKey('#');
		}
		LetterNode aux = nodes.get(word[i]);
		if(aux == null) {
			return false;
		}
		return hasWord(word, i+1, aux.next);
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
