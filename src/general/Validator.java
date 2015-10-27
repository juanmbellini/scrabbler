package general;

import general.BoardState.Direction;
import utility.Dictionary;

public class Validator {
	
	//private static Set<String> dictionary;
	private static Dictionary dictionary;
	
	public static void setDictionary(Dictionary dictionary) {
		Validator.dictionary = dictionary;
	}
	
	public static boolean wordExists(String word){
		if(word.length() <= 1 || word.length() >= 8){
			return false;
		}
		return dictionary.hasWord(word);
	}
	
	/*revisa si poner una palabra ahi es valido o no*/
	public static boolean isValidMovement(Move move, BoardState boardState){
		
		int x = move.getX(), y = move.getY();
		String word = move.getWord();
		char[][] spaces = boardState.getSpaces();
		
		if(dictionary == null || x < 0 || y < 0){	//si la estoy copiando desde afuera
			return false;
		}
		
		if(!isWithinBounds(boardState, move) || !boardHasEnoughLettersFor(boardState, move)) {
			return false;
		}
		
		int overlappingSpaces = 0;
		boolean matches = false;
		
		if(move.getDirection() == Direction.DOWN) {
			
			for(int i=y; i < y + word.length(); i++){   		//se fija si 
				
				if(boardState.isOccupied(x-1, i) || boardState.isOccupied(x+1, i)){ //no forma palabras nuevas y si lo 
					
					matches = true;
					StringBuffer wordaux= new StringBuffer();	//hace las checkea y se fija que exista
					wordaux.append(spaces[i][x]);
					
					for(int j = x - 1; j > 0 && spaces[i][j]!=' '; j--){
						wordaux.append(spaces[i][j]);
					}
					
					wordaux.reverse();
					
					for(int j = x + 1; j < BoardState.SIZE && spaces[i][j]!=' '; j++){
						wordaux.append(spaces[i][j]);
					}
					
					if(!dictionary.hasWord(wordaux.toString())){
						return false;
					}
				}
				
				if(boardState.isOccupied(x, i)){
					matches = true;
					overlappingSpaces++;
				}
			}
			
			if( (y > 0 && spaces[y-1][x] != ' ') || ( y + move.getWord().length() < BoardState.SIZE && spaces[y+move.getWord().length()][x] != ' ') ) {
				
				StringBuffer wordaux = new StringBuffer();
				for(int j = y - 1; j > 0 && spaces[j][x]!=' '; j--){
					wordaux.append(spaces[j][x]);
				}
				
				wordaux.reverse();
				wordaux.append(move.getWord());
				
				for(int j = y + move.getWord().length(); j<BoardState.SIZE && spaces[j][x]!=' '; j++){
					wordaux.append(spaces[j][x]);
				}
				
				if(wordaux.length()>7){
					return false;
				}
				
				if(!dictionary.hasWord(wordaux.toString())){
					return false;
				}
			}
			
			if(overlappingSpaces == word.length()){
				return false;
			}
		} else {
			
			for(int i = x; i < x + word.length(); i++) {
				
				if (boardState.isOccupied(i, y - 1) || boardState.isOccupied(i, y + 1)) {
					
					matches = true;
					StringBuffer wordaux = new StringBuffer();
					wordaux.append(spaces[y][i]);
					for(int j = y - 1; j > 0 && spaces[j][i] != ' '; j--){
						wordaux.append(spaces[j][i]);
					}
					wordaux.reverse();
					for(int j = y + 1; j < BoardState.SIZE && spaces[j][i]!=' '; j++){
						wordaux.append(spaces[j][i]);
					}
					if(!dictionary.hasWord(wordaux.toString())){
						return false;
					}
				}
				if(boardState.isOccupied(i, y)){
					matches = true;
					overlappingSpaces++;
				}
			}
			if(overlappingSpaces == word.length()){
				return false;
			}
			if((x > 0 && spaces[y][x-1] != ' ') || (x + move.getWord().length() < BoardState.SIZE && spaces[y][x+move.getWord().length()] != ' ')){
				
				StringBuffer wordaux = new StringBuffer();
				for(int j = x - 1; j > 0 && spaces[y][j] != ' '; j--){
					wordaux.append(spaces[y][j]);
				}
				wordaux.reverse();
				wordaux.append(move.getWord());
				for(int j = x + move.getWord().length(); j < BoardState.SIZE && spaces[y][j] != ' '; j++){
					wordaux.append(spaces[y][j]);
				}
				if(wordaux.length() > 7){
					return false;
				}
				if(!dictionary.hasWord(wordaux.toString())){
					return false;
				}
			}
		}
		if(matches == false){
			return false;
		}
		return true;		
	}

	public static boolean hasWord(String word, int[] letters) {
		//TODO se podria mejorar el codigo y ser mas eficiente, ya ponerlas en el tablero

		int i;
		for(i = 0; i < word.length() && letters[word.charAt(i)-'A'] >= 1; i++) {
			letters[(int)(word.charAt(i)-'A')]--;
		}
		if(i == word.length()){
			for(i=0; i < word.length() ; i++){
				letters[(word.charAt(i)-'A')]++;
			}
			return true;
		}
		for(; i >= 0 ; i--){
			letters[(word.charAt(i)-'A')]++;
		}
		return false;
	}
	
	/**
	 * Checks that the specified move will not go out of bounds when
	 * played on the specified board.
	 * 
	 * @param b The board on which the move would be applied.
	 * @param m The move to analyze.
	 * @return {@code true} If the start and end coordinates of the
	 * specified move are all within the board's bounds.
	 */
	public static boolean isWithinBounds(BoardState b, Move m) {
		int deltaX = m.getDirection() == Direction.RIGHT ? 1 : 0,
				deltaY = m.getDirection() == Direction.DOWN ? 1 : 0;
		int endX = m.getX()+(m.getWord().length()-1)*deltaX,
				endY = m.getY()+(m.getWord().length()-1)*deltaY;
		return m.getX() >= 0 && endX < BoardState.SIZE
				&& m.getY() >= 0 && endY < BoardState.SIZE;
	}
	
	/**
	 * Checks that the specified board has enough letters to make
	 * the specified move.
	 * 
	 * @param b The board state that would perform the move.
	 * @param m The move to analyze. ASSUME THAT IT WON'T GO OUT OF BOUNDS.
	 * @return {@code true} If the board has enough letters for the move.
	 */
	public static boolean boardHasEnoughLettersFor(BoardState b, Move m) {
		int deltaX = m.getDirection() == Direction.RIGHT ? 1 : 0,
				deltaY = m.getDirection() == Direction.DOWN ? 1 : 0;
		int[] remainingLetters = b.getRemainingLetters().clone();	//TODO optimize? Start with how many remaining letters the board has
		char[][] spaces = b.getSpaces();
		for(int x = m.getX(), y = m.getY(), i = 0; i < m.getWord().length(); i++) {	//Add any letters available in the move slots
			if(spaces[y][x] != ' ') {
				remainingLetters[spaces[y][x]-'A']++;
			}
			x += deltaX;
			y += deltaY;
		}
		for(char c : m.getWord().toCharArray()) {
			remainingLetters[c-'A']--;
		}
		//All letter indices should have at least 0 remaining letters.
		for(int i : remainingLetters) {
			if(i < 0) {
				return false;
			}
		}
		return true;
	}
}
