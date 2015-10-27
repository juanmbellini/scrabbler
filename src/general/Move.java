package general;

import general.BoardState.Direction;

/**
 * Class used to represent moves of adding new words to a board.  Since moves are
 * constantly done and undone, moves can also store some information about the
 * board's current state in order to restore it properly when undoing a move.
 */
public class Move {
    private String word;
    private char[] previousState;
    private char[] addedLetters;
    private int x, y;
    private int score;
    private Direction dir;
    
    /**
     * Constructs a new Move object with the specified information.
     *
     * @param word The word to place.
     * @param x The word's starting row.
     * @param y The word's starting column.
     * @param dir Right or down.
     */
    public Move(String word, int x, int y, Direction dir) {
        this.word = word;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    
    public String getWord() {
        return word;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public Direction getDirection() {
        return dir;
    }
    
    public char[] getPreviousState() {
        return previousState;
    }
    
    public void setPreviousState(char[] previousState) {
        this.previousState = previousState;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public char[] getAddedLetters() {
        return addedLetters;
    }
    
    public void setAddedLetters(char[] addedLetters) {
        this.addedLetters = addedLetters;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dir == null) ? 0 : dir.hashCode());
        result = prime * result + ((word == null) ? 0 : word.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Move)) {
            return false;
        }
        Move other = (Move) obj;
        return dir == other.dir && word.equals(other.word) && x == other.x && y == other.y;
    }
    
    @Override
    public String toString() {
        return "\"" + word + "\" @ ("+ x + "," + y + ") going " + dir;
    }
}
