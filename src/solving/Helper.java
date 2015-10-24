package solving;

import java.util.HashSet;
import java.util.Set;

import general.BoardState;
import general.BoardState.Direction;
import general.Move;
import general.PossibleWordsIterator;
import general.Validator;

/**
 * Helper class for making decisions when solving the problem.
 *
 */
public class Helper {

	public static Set<Move> getPossibleMoves(BoardState b, Set<String> dictionary) {
		boolean flag = true;
		Set<Move> result = new HashSet<>();	//TODO order by score?
		for(int i=0; i<b.getRemainingLetters().length && flag;i++){
			if(b.getRemainingLetters()[i]!=0){
				flag=false;
			}
		}
		if(flag == true){
			return result;
		}
		char[][] spaces = b.getSpaces();
		int[] remainingLetters = b.getRemainingLetters();
		for (int y = 0; y < spaces.length; y++) {
			for (int x = 0; x < spaces[y].length; x++) {
				if(b.isOccupied(x, y)) {	//Word can potentially be formed from here
					if(b.isSurrounded(x, y)) {
						continue;
					}
					remainingLetters[spaces[y][x]-'A']++;	//This letter can be used (but only here)
					for(Move m : getMovesAt(x, y, b, new PossibleWordsIterator(dictionary.iterator(), remainingLetters))) {
						result.add(m);
					}
					remainingLetters[spaces[y][x]-'A']--;	//Now make this letter unusable again
				}
			}
		}
		return result;
	}
	
	/**
	 * Gets all the indices in which the given String can combine with the 
	 * specified character (part of another word)
	 * 
	 * @param s The string to insert.
	 * @param c The character to combine with.
	 * @return A set of ints containing all combination indices.
	 */
	public static Set<Integer> getCombinationIndeces(String s, char c) {
		Set<Integer> result = new HashSet<>();
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == c) {
				result.add(i);
			}
		}
		return result;
	}
	
	private static Set<Move> getMovesAt(int x, int y, BoardState b, PossibleWordsIterator w) {
		Set<Move> result = new HashSet<>();
		//In which direction can the new word go? Assume down, change if necessary
		//TODO verify direction choice
		Direction dir = Direction.DOWN;
		if(b.isOccupied(x, y-1) || b.isOccupied(x, y+1) || (y + 1 >= BoardState.SIZE) ) {
			dir = Direction.RIGHT;
		}
		while(w.hasNext()) {
			String word = w.next();
			for(int i : getCombinationIndeces(word, b.getSpaces()[y][x])) {
				Move potentialMove = new Move(
											//TODO verify offsets work
											word,
											(dir == Direction.RIGHT ? x-i : x),
											(dir == Direction.DOWN ? y-i : y),
											dir);
				if(Validator.isValidMovement(potentialMove, b)) {
					result.add(potentialMove);
				}
			}
		}
		return result;
	}

}
