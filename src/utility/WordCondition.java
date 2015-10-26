package utility;

/**
 * This class represents a condition to be evaluated in the dictionary. It contains a position in a word, and the letter
 * to evaluate in that position. For example, if you have in the dictionary the words "NOSE" and "BODY", and your condition
 * is words with an 'O' in the second position, both words will satisfy the condition
 * <p>The first position of a word has the 0 index</p>
 *
 */
public class WordCondition {
	
	private int position;
	private char letter;
	
	public WordCondition(int position, char letter) {
		this.position = position;
		this.letter = letter;
	}

	public int getPosition() {
		return position;
	}

	public char getLetter() {
		return letter;
	}
	

}
