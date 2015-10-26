package general;

public class Token {
	char letter;
	int index;
	
	public Token(char letter, int index){
		this.letter=letter;
		this.index=index;
	}
	public char getLetter() {
		return letter;
	}
	
	
	public int getIndex() {
		return index;
	}
}
