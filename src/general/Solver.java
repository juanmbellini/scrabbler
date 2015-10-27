package general;

import java.util.HashSet;
import java.util.Set;

import general.BoardState.Direction;
import gui.StateVisualizer;
import solving.Helper;
import utility.Dictionary;
import utility.WordCondition;

public class Solver {
	private Dictionary dictionary;
	private int[] letters;
	private char[][] bestAnswer;
	private int bestScore;
	private static StateVisualizer v = new StateVisualizer();
	
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
		for(String word:dictionary.giveMeWords(new HashSet<WordCondition>())){
			for(int i=0 ; i<word.length(); i++){
				Move movement = new Move(word, 6 - i, 6, Direction.RIGHT);
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
		v.print(currentBoard.toPrettyString());
		System.out.println(currentBoard.toPrettyString());
		Set<Move> movements=Helper.getPossibleMoves(currentBoard, this.dictionary);
		if(movements.isEmpty()){
			if(currentBoard.getScore() > bestScore){
				return new BoardState(currentBoard);
			}
			return new BoardState(currentBoard);
		}
		for(Move movement: movements){
			currentBoard.doMove(movement);
			BoardState result = backTracking(currentBoard, bestBoard);
			if(result.getScore() > bestBoard.getScore()){
				bestBoard = new BoardState(currentBoard);
				bestScore = bestBoard.getScore();
			}
			currentBoard.undoMove(movement);
		}
		return new BoardState(currentBoard);
	}
}
