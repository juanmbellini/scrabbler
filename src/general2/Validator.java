package general2;

import java.util.Set;

public class Validator {
	private static Set<String> dictionary;
	
	public static void setDictionary(Set<String> dictionary) {
		Validator.dictionary = dictionary;
	}
	
	public static boolean wordExists(String word){
		if(word.length()<=1 || word.length()>=8){
			return false;
		}
		return dictionary.contains(word);
	}
}
