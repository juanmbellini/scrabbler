package general;
import java.util.Iterator;

public class PossibleWordsIterator<String> implements Iterator<String>{

	Iterator<String> iteratorDictionary;
	
	public PossibleWordsIterator(Iterator<String> iteratorDictionary){
		this.iteratorDictionary = iteratorDictionary;
	}

	public boolean hasNext() {
		return iteratorDictionary.hasNext();
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return null;
	}

}
