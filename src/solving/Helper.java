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
				if(isPossible(b, x, y, 1, 0)){
					for(String word : dictionary.giveMeWords(getWordConditions(b,x,y,1,0))){
						result.add(new Move(word, x, y, Direction.RIGHT));
					}
				}
				if(isPossible(b, x, y, 0, 1)){
					for(String word : dictionary.giveMeWords(getWordConditions(b,x,y,0,1))){
						result.add(new Move(word, x, y, Direction.DOWN));
					}
				}				
			}
		}
		for(Move move:result){
			if(!Validator.isValidMovement(move, b)){
				result.remove(move);
			}
		}
		return result;
	}
	
	private static boolean isPossible(BoardState b, int x, int y, int dirX, int dirY) {
		boolean flagSpace=true,flagLetter = true;
		for(int index = 0; index<7 && index * dirX + x<BoardState.SIZE && index * dirY + y <BoardState.SIZE && (flagLetter || flagSpace);index++){
			if(b.getSpaces()[y + index * dirY][x + index * dirX]==' '){
				flagSpace = false;
			}else{
				flagLetter = false;
			}
		}
		return !(flagSpace || flagLetter);
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
