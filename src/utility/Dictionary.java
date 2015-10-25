package utility;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a dictionary using a trie as a data structure
 *
 */
public class Dictionary {
	
	//TODO mejorar la eficiencia de la lista de nodos, por ahi podemos implementar una que use busqueda binaria
	
	List<LetterNode> root;
	
	public Dictionary() {
		
		root = new ArrayList<LetterNode>();
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
	
	private void addWord(char[] word, int i, List<LetterNode> nodes) {
		
		if (i == word.length) {
			
			nodes.add(new LetterNode('#'));
			return;
		}
		
		LetterNode aux = new LetterNode(word[i]);
		int index = nodes.indexOf(aux);
		
		if (index == -1) { 			
			nodes.add(aux); // This means that the letter wasn't added in the current node son, so it's added
		} else {		
			aux = nodes.get(index); // Gets the real node
		}
		
		addWord(word, ++i, aux.next);
			
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
	
	private boolean hasWord(char[] word, int i, List<LetterNode> nodes) {
		
		if(i == word.length) {			
			return nodes.contains(new LetterNode('#'));
		}
		
		LetterNode aux = new LetterNode(word[i]);
		int index = nodes.indexOf(aux);
		
		if (index == -1) {
			return false; // The current letter wasn't found, so the specific word can't be in the dictionary
		}
		aux = nodes.get(index); // Gets the real node
		return hasWord(word, ++i, aux.next);
	}
	
	
	
	
	private static class LetterNode {
		
		char elem;
		List<LetterNode> next;
		
		public LetterNode(char c) {
			
			elem = c;
			next = (c == '#') ? null : new ArrayList<LetterNode>(); // Avoids creating a list in a finishing word node
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
