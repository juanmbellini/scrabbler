package general;

import java.util.Set;

public class Validator {
	
	private Set<String> dictionary;
	
	public Validator(Set<String> dictionary){
		this.dictionary=dictionary;
	}
	
	public boolean wordExists(String word){
		if(word.length()<=1 || word.length()>=8){
			return false;
		}
		return dictionary.contains(word);
	}
	
	public boolean validateMovement(){
		//TODO
		return true;
	}
}
