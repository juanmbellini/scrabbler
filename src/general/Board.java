package general;

public class Board {
	public static final int[] POINTS = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10}; 
	public static enum Direction {DOWN, RIGHT};
	public static final int SIZE = 15;
	private char[][] spaces;

	public Board() {
		spaces = new char[SIZE][SIZE];
		clearBoard();
	}
	
	public boolean isOccupied(int x, int y) {
		return spaces[y][x] != ' ';
	}
	
	public int getScore() {
		int result = 0;
		for(char[] row : spaces) {
			for(char c : row) {
				if(c != ' ') {
					result += POINTS[c-'A'];
				}
			}
		}
		return result;
	}
	
	public boolean isValidMove() {
		//TODO
		return false;
	}
	
	private void clearBoard() {
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++) {
				spaces[row][col] = ' ';
			}
		}
	}
}
