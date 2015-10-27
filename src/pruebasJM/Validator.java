package pruebasJM;

import com.sun.javafx.scene.traversal.Direction;

import utility.Dictionary;
import general.BoardState;
import general.Move;

public class Validator {
	
	public static boolean isValidMovement(Move movement, BoardState boardState, Dictionary dictionary) {
		
		int x = movement.getX(), y = movement.getY();
		String word = movement.getWord();
		char[][] spaces = boardState.getSpaces();
		
		if(!isWithinBounds(boardState, movement) || !boardHasEnoughLettersFor(boardState, movement)) {
			return false;
		}
		
		
		
		return false;
	}
	
	
	public static boolean isWithinBounds(BoardState b, Move m) {
		
		int offset = m.getWord().length();
		
		if (m.getDirection() == BoardState.Direction.DOWN) {
			return (m.getY() + offset) < BoardState.SIZE;
		} else {
			return (m.getX() + offset) < BoardState.SIZE;
		}
		
	}
	
	public static boolean boardHasEnoughLettersFor(BoardState b, Move m) {
		
		int x = m.getX(), y = m.getY();
		char[] word = m.getWord().toCharArray();
		char[][] spaces = b.getSpaces();
		int[] letters = new int[26];
		
		for (int i = 0 ; i < b.getRemainingLetters().length ; i++) {
			letters[i] = b.getRemainingLetters()[i]; // Must do a copy of values because, if not, the real one gets modified
		}
		
		if (m.getDirection() == BoardState.Direction.DOWN) {
			
			for (int index = 0 ; index < word.length ; index++) {
				
				if (spaces[y + index][x] == ' ') {
					int actualLetter = word[index] - 'A';
					if (letters[actualLetter] == 0) {
						return false;
					}
					letters[actualLetter]--;	
				}
			}	
		} else {
			for (int index = 0 ; index < word.length ; index++) {
				
				if (spaces[y][x + index] == ' ') {
					int actualLetter = word[index] - 'A';
					if (letters[actualLetter] == 0) {
						return false;
					}
					letters[actualLetter]--;	
				}
			}
			
		}
		
		
		
		return true;
	}

}
