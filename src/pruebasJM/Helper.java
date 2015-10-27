package pruebasJM;

import general.BoardState;
import general.Move;
import general.Validator;
import general.BoardState.Direction;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.sun.scenario.effect.impl.state.BoxRenderState;

import pruebasJM.State.Directions;
import utility.Dictionary;
import utility.WordCondition;

public class Helper {
	
	
	
	public static Collection<Move> getPossibleMoves(BoardState boardState, Dictionary dictionary) {
		
		Collection<Move> result = new HashSet<Move>();	//TODO order by score?
		
		if(!boardState.hasRemainingLetters()) {
			return result;	//No moves, return empty result
		}
		
		char[][] spaces = boardState.getSpaces();
		int[] remainingLetters = boardState.getRemainingLetters();
		
		if (boardState.getScore() == 0) { //No words were placed
			Collection<String> startingWords = getPossibleStartingWords(boardState, dictionary);
			for(String word : startingWords){
				for(int i=0 ; i<word.length(); i++){
					result.add(new Move(word, 7 - i, 7, Direction.RIGHT));
				}
			}
			return result;
		}
		
		for (int y = 0; y < spaces.length; y++) {
			
			for (int x = 0; x < spaces[y].length; x++) {
				
				if(isValidRange(boardState, x, y, Direction.RIGHT)) {
					
					Collection<String> words = dictionary.giveMeWords(getWordConditions(boardState,x,y,1,0));
					
					for(String word : words) {
						Move move = new Move(word, x, y, Direction.RIGHT);
						if(Validator.isValidMovement(move, boardState)){
							result.add(move);
						}
					}
				}
				
				if(isValidRange(boardState, x, y, Direction.DOWN)){
					for(String word : dictionary.giveMeWords(getWordConditions(boardState,x,y,0,1))){
						Move move = new Move(word, x, y, Direction.DOWN);
						if(Validator.isValidMovement(move, boardState)){
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
	 * @param boardState The board to analyze the case in.
	 * @param x The starting column.
	 * @param y The starting row.
	 * @param dir The direction in which to advance.
	 * @return {@code true} If within the maximum length of allowed words there is at least
	 * one space and one letter (no letters => can't combine, no spaces => no room)
	 */
	
	private static boolean isValidRange(BoardState boardState, int i, int j, Direction dir) {
		
		boolean foundSpace = false, foundLetter = false;
		char[][] spaces = boardState.getSpaces();
		
		if (dir == Direction.DOWN) {
			boolean flag = false;		
			for (int index = 0 ; index < 7 && !flag && !(foundSpace && foundLetter); i++) {
				if (i < 0 || i >= BoardState.SIZE || j < 0 || j >= BoardState.SIZE) {
					flag = true;
				}
				foundSpace |= spaces[i + index][j] != ' ';
				foundLetter |= spaces[i + index][j] == ' ';
			}
		} else {
			boolean flag = false;
			
			for (int index = 0 ; index < 7 && !flag && !(foundSpace && foundLetter); i++) {
				if (i < 0 || i >= BoardState.SIZE || j < 0 || j >= BoardState.SIZE) {
					flag = true;
				}
				foundSpace |= spaces[i][j + index] != ' ';
				foundLetter |= spaces[i][j + index] == ' ';
			}
		}
		return foundSpace && foundLetter;		
	}
	
	
	public static Set<WordCondition> getWordConditions(BoardState boardsState, int x, int y, int dirX, int dirY) {
		
		Set<WordCondition> tokens = new HashSet<WordCondition>();
		char[][]spaces = boardsState.getSpaces();
		
		for(int index = 0; (index < 7) && (index * dirX +  x < BoardState.SIZE) && (index * dirY + y < BoardState.SIZE) ; index++) {
			
			if(spaces[y + index * dirY][x + index  * dirX] != ' '){
				
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
	
	private static Collection<String> getPossibleStartingWords(BoardState initial, Dictionary dictionary) {
		
		Collection<String> words = dictionary.giveMeWords(new HashSet<WordCondition>());
		Collection<String> possibleWords = new HashSet<String>();
		int[] letters = new int[26];
		
		for (String each : words) {
			
			for (int i = 0 ; i < initial.getRemainingLetters().length ; i++) {
				letters[i] = initial.getRemainingLetters()[i]; // Must do a copy of values because, if not, the real one gets modified
			}
			
			boolean flag = false;
			char[] actualWord = each.toCharArray();
			for (int i = 0 ; i < actualWord.length && !flag ; i++) {
				int actualLetter = actualWord[i] - '0';
				if (letters[actualLetter] == 0) {
					flag = true; // No more letters for this word, so it can't be a possible starting move
				} else {
					letters[actualLetter]--; // Gets off the used letter
				}
			}
			if (!flag) {
				possibleWords.add(each);
			}
		}
		
		return possibleWords;	
	}
	
	
}
