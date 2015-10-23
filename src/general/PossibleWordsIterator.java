package general;
import java.util.Iterator;
/*funciona perfecto, testeado*/
public class PossibleWordsIterator implements Iterator<String>{

	private Iterator<String> iteratorDictionary;
	private int[] letters;
	private String current;
	
	public PossibleWordsIterator(Iterator<String> iteratorDictionary, int[] letters){
		this.iteratorDictionary = iteratorDictionary;
		this.letters=letters;
	}

	public boolean hasNext() {
		while(iteratorDictionary.hasNext()){
			String current = iteratorDictionary.next();
				if(Validator.hasWord(current, letters)){
					this.current = current;
					return true;
				}
		}
		return false;
	}

	public String next() {
		return current;
	}

	public void remove() {
		iteratorDictionary.remove();
	}

}
