package general;

import general.BoardState.Direction;

import java.util.Set;

import solving.Helper;
import utility.Dictionary;

public class Solver {
	private Dictionary dictionary;
	private int[] letters;
	private char[][] bestAnswer;
	private int bestScore;
	
	
	public Solver(Dictionary dictionary){
		this.dictionary=dictionary;
	}


	public Dictionary getDictionary() {
		return dictionary;
	}


	public int[] getLetters() {
		return letters;
	}


	public char[][] getBestAnswer() {
		return bestAnswer;
	}
	
	
	public BoardState backTracking(Dictionary dictionary, int[] letters){
		BoardState board = new BoardState(letters);
		this.dictionary = dictionary;
		BoardState result;
		BoardState bestBoard = board;
		bestScore = bestBoard.getScore();
		for(String word:dictionary.giveMeWords(null)){
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
