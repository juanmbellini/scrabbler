package pruebasJM;

import java.util.SimpleTimeZone;

import utility.Dictionary;

public class State {
	
	final static int SIZE = 15;
	enum Directions {DOWN, RIGHT};
	
	char[][] board;
	int[] letters;
	Movement[] movements;
	int score;
	
	public State(int[] letters) {
		
		board = new char[SIZE][SIZE];
		this.letters = letters;
		score = 0;
		
		for (int i = 0 ; i < board.length ; i++) {
			for (int j = 0 ; j < board[i].length ; j++) {
				board[i][j] = ' ';
			}
		}
		
		
	}
	
	
	public void performMove(Movement movement) {
		
		int x = movement.x, y = movement.y, i = 0;
		
		if (movement.direction == Directions.DOWN) {
			while(i < movement.word.length()) {
				board[x++][y] = movement.word.charAt(i++);
			}
		} else {
			while(i < movement.word.length()) {
				board[x][y++] = movement.word.charAt(i++);
			}
		}
	}
	
	public void undoMove(Movement movement) {
		
		int x = movement.x, y = movement.y, i = 0;
		
		if (movement.direction == Directions.DOWN) {
			while(x < movement.word.length()) {	
				if ( ((y-1) >= 0 && board[x][y-1] == ' ') || ((y+1) < SIZE && board[x][y+1] == ' ' ) ) {
					board[x][y] = ' ';
				}
				x++;
			}
		} else {
			while(y < movement.word.length()) {
				if( ((x-1) >= 0 && board[x-1][y] == ' '))
			}	
		}
	}
	
	
	

	public int compareScores(State o) {
		
		if (o == null) {
			throw new IllegalArgumentException();
		}
		
		return this.score - o.score;
	}
	
	
	private static class Movement {
		
		
		
		int x, y;
		String word;
		Directions direction;
		
		
		public Movement(int x, int y, String word, Directions direction) {
			this.x = x;
			this.y = y;
			this.word = word;
			this.direction = direction;
		}
	}
	

}
