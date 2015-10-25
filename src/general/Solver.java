package general;

import general.BoardState.Direction;

import java.util.Set;

import solving.Helper;

public class Solver {
	private Set<String> dictionary;
	private int[] letters;
	private char[][] bestAnswer;
	private int bestScore;
	
	
	public Solver(Set<String> dictionary){
		this.dictionary=dictionary;
	}


	public Set<String> getDictionary() {
		return dictionary;
	}


	public int[] getLetters() {
		return letters;
	}


	public char[][] getBestAnswer() {
		return bestAnswer;
	}
	public BoardState backTracking(Set<String> dictionary, int[] letters){
		BoardState board = new BoardState(letters);
		this.dictionary = dictionary;
		PossibleWordsIterator it = new PossibleWordsIterator(dictionary.iterator(), letters);
		BoardState result;
		BoardState bestBoard = board;
		bestScore = bestBoard.getScore();
		while(it.hasNext()){
			String word = it.next();
			for(int i=0 ; i<word.length(); i++){
				Move movement = new Move(word, 7 + i, 7, Direction.RIGHT);
				board.doMove(movement);
				result = backTracking(board, bestBoard);
				if(result.getScore() > bestBoard.getScore()){
					bestBoard = result;
					bestScore = bestBoard.getScore();
				}
				board.undoMove(movement);
			}
		}
		return bestBoard;
	}
	
	private BoardState backTracking(BoardState currentBoard, BoardState bestBoard){
		Set<Move> movements=Helper.getPossibleMoves(currentBoard, this.dictionary);
		if(movements.isEmpty()){
			if(currentBoard.getScore() > bestScore){
				return currentBoard;
			}
			return bestBoard;
		}
		for(Move movement: movements){
			currentBoard.doMove(movement);
			BoardState result = backTracking(currentBoard, bestBoard);
			if(result.getScore() > bestBoard.getScore()){
				bestBoard = result;
				bestScore = bestBoard.getScore();
			}
		}
		return bestBoard;
	}
}
