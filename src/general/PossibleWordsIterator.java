package general;
import java.util.Iterator;

public class PossibleWordsIterator implements Iterator<String>{

	Iterator<String> iteratorDictionary;
	int[] letters;
	String current;
	
	public PossibleWordsIterator(Iterator<String> iteratorDictionary, int[] letters){
		this.iteratorDictionary = iteratorDictionary;
		this.letters=letters;
	}

	public boolean hasNext() {
		while(iteratorDictionary.hasNext()){
			current = iteratorDictionary.next();
			for(int i=0; i < current.length(); i++){
				if(Validator.hasWord(current, letters)){
					this.current = current;	//TODO esta línea no hace nada, revisar
					return true;
				}
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
