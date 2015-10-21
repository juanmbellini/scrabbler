package general;

/**
 * Class designed to represent the board. It contains all slots with either spaces
 * or letters.  Can perform certain logic on specific slots to help decide whether
 * a move is valid or not. 
 */
public class Board implements Comparable<Board> {
	public static final int[] LETTER_POINTS = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; 
	public static enum Direction {DOWN, RIGHT};
	public static final int SIZE = 15;	//Square board
	private char[][] spaces;
	int score;

	/**
	 * Creates a new board and marks all its slots as empty.
	 */
	public Board() {
		score = 0;
		spaces = new char[SIZE][SIZE];
		clearBoard();
	}
	
	/**
	 * Creates a new board with the same state as the specified board. 
	 * 
	 * @param b The board whose state to replicate.
	 */
	public Board(Board b) {
		score = b.score;
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
	 * Places the specified word, character by character, in this board's spaces
	 * and recalculates the board's score.
	 * 
	 * @param word The word to add.
	 * @param x The starting column.
	 * @param y The starting row.
	 * @param dir Down or right.
	 * @return {@code true} if the movement is valid and the board was updated,
	 * {@code false} otherwise.
	 */
	public boolean placeWord(String word, int x, int y, Direction dir) {
		/*if(!Validator.isValidMovement(x, y, word, this.spaces, dir)) {
			return false;
		}*/
		int deltaX = dir == Direction.RIGHT ? 1 : 0,
				deltaY = dir == Direction.DOWN ? 1 : 0;
		for(char c : word.toCharArray()) {
			spaces[y][x] = c;
			x += deltaX;
			y += deltaY;
			score += LETTER_POINTS[c-'A'];
		}
		return true;
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
	 * Returns a textual representation of this board's current state.
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
	
	//TODO alinear texto
	public String toPrettyString() {
		StringBuilder result = new StringBuilder();
		result.append("---------------\n");
		for(char[] row : spaces) {
			for(char column : row) {
				result.append(column+"  ");
			}
			result.append("\n");
		}
		result.append("---------------\n");
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
		if(!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
		return this.toString().equals(other.toString());
	}

	/**
	 * Scores with lower scores are considered smaller.
	 */
	@Override
	public int compareTo(Board o) {
		return Integer.compare(calculateScore(), o.calculateScore());
	}
}
