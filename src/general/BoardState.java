package general;

/**
 * Class designed to represent the board. It contains all slots with either spaces
 * or letters.  Can perform certain logic on specific slots to help decide whether
 * a move is valid or not. 
 */
public class BoardState {
	public static final int[] LETTER_POINTS = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; 
	public static enum Direction {DOWN, RIGHT};
	public static final int SIZE = 15;	//Square board
	private char[][] spaces;
	private int[] remainingLetters;
	int score;
	
	//TODO mencionar en informe que sacamos Comparable porque no era consistente con Equals

	/**
	 * Creates a new board and marks all its slots as empty.
	 */
	public BoardState(int[] startingLetters) {
		score = 0;
		spaces = new char[SIZE][SIZE];
		remainingLetters = startingLetters;
		clearBoard();
	}
	
	/**
	 * Creates a new board with the same state as the specified board. 
	 * 
	 * @param b The board whose state to replicate.
	 */
	public BoardState(BoardState b) {
		score = b.score;
		remainingLetters = b.remainingLetters.clone();
		spaces = b.spaces.clone();
	}
	
	/**
	 * Checks whether there is a letter in the specified slot.
	 * 
	 * @param x The column.
	 * @param y The row.
	 * @return {@code true} If the specified slot is valid and there is a letter
	 * in the specified slot.
	 */
	public boolean isOccupied(int x, int y) {
		return (x >= 0 && x < SIZE)
				&& (y >= 0 && y < SIZE)
				&& spaces[y][x] != ' ';
	}
	
	/**
	 * Calculates the score of this board in its current state as per the rules
	 * defined in the game.
	 * 
	 * @deprecated DO NOT USE. When words are added the score is automatically
	 * recalculated. Left only in case we decide it's a better idea to use.
	 * @return The sum of the points of all letters currently on this board.
	 */
	public int calculateScore() {
		int result = 0;
		for(char[] row : spaces) {
			for(char c : row) {
				if(c != ' ') {
					result += LETTER_POINTS[c-'A'];
				}
			}
		}
		return result;
	}
	
	/**
	 * Carries out the specified move, if valid, storing information about the current
	 * state (see {@link #prepareMove(Move)}), before updating it.
	 * <br />
	 * <b>NOTE:</b> To undo this move, call {@link #undoMove(Move)} with this same move.
	 * Performing other moves before undoing the specified one may cause inconsistency
	 * in the board.
	 * 
	 * @param m The move to perform.
	 * @return {@code true} If the move is valid and was performed, {@code false}
	 * otherwise.
	 */
	public boolean doMove(Move m) {
		if(!Validator.isValidMovement(m, this)) {
			return false;
		}
		prepareMove(m);
		placeWord(m.getWord().toCharArray(), m.getX(), m.getY(), m.getDirection());
		score += m.getScore();
		takeLetters(m.getAddedLetters());
		return true;
	}
	
	/**
	 * Undoes the specified move, restoring the previous board state.
	 * <br />
	 * <b>NOTE:</b> This method is intended to restore the board state directly after
	 * calling {@link #doMove(Move)} with the same Move. Undoing a move after a different
	 * move has been performed may cause inconsistency in the board. 
	 * 
	 * @param m The move to undo.
	 * @return {@code true} If the undoing was completed successfully, {@code false}
	 * otherwise.
	 */
	public boolean undoMove(Move m) {
		placeWord(m.getPreviousState(), m.getX(), m.getY(), m.getDirection());
		score -= m.getScore();
		putLetters(m.getAddedLetters());
		return true;
	}
	
	/**
	 * Places the specified word, character by character, in this board's spaces.
	 * 
	 * @param word The word to add.
	 * @param x The starting column.
	 * @param y The starting row.
	 * @param dir Down or right.
	 * @return {@code true} if the movement is valid and the board was updated,
	 * {@code false} otherwise.
	 */
	private void placeWord(char[] word, int x, int y, Direction dir) {
		int deltaX = dir == Direction.RIGHT ? 1 : 0,
				deltaY = dir == Direction.DOWN ? 1 : 0;
		for(char c : word) {
			spaces[y][x] = c;
			x += deltaX;
			y += deltaY;
		}
	}
	
	/**
	 * Removes the specified letters from the available letters array. Called when
	 * performing a move.
	 * 
	 * @param word The word whose letters to analyze and remove.
	 */
	private void takeLetters(char[] word) {
		for(char c : word) {
			if(c >= 'A' && c <= 'Z') {
				remainingLetters[c-'A']--;
			}
		}
	}
	
	/**
	 * Puts the specified letters from the available letters array. Called when
	 * undoing a move.
	 * 
	 * @param word The word whose letters to analyze and add.
	 */
	private void putLetters(char[] word) {
		for(char c : word) {
			if(c >= 'A' && c <= 'Z') {
				remainingLetters[c-'A']++;
			}
		}
	}
	
	/**
	 * Checks whether a specified slot is surrounded in all sides by another letter
	 * or by a border.
	 * 
	 * @param x The column.
	 * @param y The row.
	 * @return {@code true} if there is a letter or a border on all 4 directions
	 * of the specified slot.
	 */
	public boolean isSurrounded(int x, int y) {
		return (x == 0 || isOccupied(x-1, y))
				&& (x == SIZE-1 || isOccupied(x+1, y))
				&& (y == 0 || isOccupied(x, y-1))
				&& (y == SIZE-1 || isOccupied(x, y+1));
	}
	
	/**
	 * Checks whether the specified slot has at least one adjacent letter.
	 * 
	 * @param x The column.
	 * @param y The row.
	 * @return {@code true} If there is at least one letter adjacent to the
	 * specified slot.
	 */
	public boolean hasAdjacentLetters(int x, int y) {
		return (x > 0 && isOccupied(x-1, y))
				|| (x < SIZE-1 && isOccupied(x+1, y))
				|| (y > 0 && isOccupied(x, y-1))
				|| (y < SIZE-1 && isOccupied(x, y+1));
	}

	/**
	 * Fills the board with spaces, effectively marking each space as blank.
	 */
	private void clearBoard() {
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				spaces[row][col] = ' ';
			}
		}
	}
	
	/**
	 * Stores information about the current board state in order to be able
	 * to properly restore the board when the move is undone.
	 * 
	 * @param m The move to calculate the info for and store the info in.
	 */
	private void prepareMove(Move m) {
		int deltaX = m.getDirection() == Direction.RIGHT ? 1 : 0,
			deltaY = m.getDirection() == Direction.DOWN ? 1 : 0,
			x = m.getX(),
			y = m.getY(),
			score = 0;
		StringBuilder b = new StringBuilder(m.getWord().length()),
			addedLetters = new StringBuilder(b.length());
		for(int i = 0; i < b.length(); i++) {
			char c = spaces[y][x];
			b.append(c);						//Store board spaces before playing this move
			if(c == ' ') {
				score += LETTER_POINTS[c-'A'];	//Calculate score added by playing this move
				addedLetters.append(c);			//Store letters NOT already on the board
			}
			x += deltaX;
			y += deltaY;
		}
		m.setPreviousState(b.toString().toCharArray());
		m.setAddedLetters(b.toString().toCharArray());
		m.setScore(score);
	}
	
	public char[][] getSpaces() {
		return spaces;
	}

	public int[] getRemainingLetters() {
		return remainingLetters;
	}

	public int getScore() {
		return score;
	}

	/**
	 * Returns a textual representation of this board's current state. Used for
	 * hash coding and equality comparison.
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(SIZE*SIZE);
		for(char[] row : spaces) {
			for(char column : row) {
				result.append(column);
			}
		}
		return result.toString();
	}
	
	/**
	 * Returns a pretty-formatted String representation of this board's current
	 * state. Used for visualizer.
	 * @return A pretty form of this board's state.
	 */
	public String toPrettyString() {
		StringBuilder result = new StringBuilder();
		result.append("- - - - - - - - - - - - - - -\n");
		for(char[] row : spaces) {
			for(char column : row) {
				result.append(column+"|");
			}
			result.append("\n");
		}
		result.append("- - - - - - - - - - - - - - -\n");
		return result.toString();
	}
	
	/**
	 * Returns the hash code of this board's string representation.
	 */
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	/**
	 * Computes equality based on boards' string representation.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof BoardState)) {
			return false;
		}
		BoardState other = (BoardState) obj;
		return this.toString().equals(other.toString());
	}
}
