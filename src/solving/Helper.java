package solving;

import general.BoardState;
import general.BoardState.Direction;
import general.Move;
import general.PossibleWordsIterator;
import general.Validator;

import java.util.HashSet;
import java.util.Set;

import utility.Dictionary;
import utility.WordCondition;

/**
 * Helper class for making decisions when solving the problem.
 *
 */
public class Helper {

	public static Set<Move> getPossibleMoves(BoardState b, Dictionary dictionary) {
		Set<Move> result = new HashSet<>();	//TODO order by score?
		if(!b.hasRemainingLetters()) {
			return result;	//No moves, return empty result
		}
		char[][] spaces = b.getSpaces();
		int[] remainingLetters = b.getRemainingLetters();
		for (int y = 0; y < spaces.length; y++) {
			for (int x = 0; x < spaces[y].length; x++) {
				if(isValidRange(b, x, y, Direction.RIGHT)){
					for(String word : dictionary.giveMeWords(getWordConditions(b,x,y,1,0))){
						Move move = new Move(word, x, y, Direction.RIGHT);
						if(Validator.isValidMovement(move, b)){
							result.add(move);
						}
					}
				}
				if(isValidRange(b, x, y, Direction.DOWN)){
					for(String word : dictionary.giveMeWords(getWordConditions(b,x,y,0,1))){
						Move move = new Move(word, x, y, Direction.DOWN);
						if(Validator.isValidMovement(move, b)){
							result.add(move);
						}
					}
				}				
			}
		}
		return result;
	}
	
	/**
	 * Checks if any word could be placed in the specified starting position and direction. 
	 * 
	 * @param b The board to analyze the case in.
	 * @param x The starting column.
	 * @param y The starting row.
	 * @param dir The direction in which to advance.
	 * @return {@code true} If within the maximum length of allowed words there is at least
	 * one space and one letter (no letters => can't combine, no spaces => no room)
	 */
	private static boolean isValidRange(BoardState b, int x, int y, Direction dir) {
		boolean foundSpace = false, foundLetter = false;
		int deltaX = dir == Direction.RIGHT ? 1 : 0,
				deltaY = dir == Direction.DOWN ? 1 : 0;
		char[][] spaces = b.getSpaces();
		for(int i = 0; i < 7; i++) {
			if(x < 0 || x >= BoardState.SIZE || y < 0 || y >= BoardState.SIZE) break;
			if(spaces[y][x] == ' ')  {
				foundSpace = true;
			}
			else {
				foundLetter = true;
			}
			x += deltaX;
			y += deltaY;
		}
		return foundSpace && foundLetter;
	}

	public static Set<WordCondition> getWordConditions(BoardState b,int x, int y, int dirX,int dirY){
		Set<WordCondition> tokens = new HashSet<WordCondition>();
		char[][]spaces = b.getSpaces();
		for(int index = 0; index<7 && index * dirX + x<b.SIZE && index * dirY + y <b.SIZE;index++){
			if(spaces[y + index * dirY][x + index  * dirX]!=' '){
				if(dirX == 0){
					tokens.add(new WordCondition(index,spaces[y + index * dirY][x]));
				}
				else{
					tokens.add(new WordCondition(index, spaces[y][x + index * dirX]));
				}
			}
		}
		return tokens;
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
