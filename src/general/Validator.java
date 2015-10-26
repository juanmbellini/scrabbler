package general;

import java.util.Set;

import general.BoardState.Direction;

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
	
	/*revisa si poner una palabra ahi es valido o no*/
	public static boolean isValidMovement(Move m, BoardState b){
		int x = m.getX(), y = m.getY();
		String word = m.getWord();
		char[][] spaces = b.getSpaces();
		if(dictionary == null || x < 0 || y < 0){	//si la estoy copiando desde afuera
			return false;
		}
		if(m.getDirection() == Direction.DOWN){
			if(y + word.length()>=BoardState.SIZE){  //si se pasa
				return false;
			}
			for(int i=y, letterCounter = 0; i < y + word.length(); i++, letterCounter++){   		//se fija si 
				if(b.isOccupied(x-1, i) || b.isOccupied(x+1, i)){ //no forma palabras nuevas y si lo 
					StringBuffer wordaux= new StringBuffer(word.charAt(letterCounter));	//hace las checkea y se fija que exista
					for(int j = x - 1; j > 0 && spaces[i][j]!=' '; j--){
						wordaux.append(spaces[i][j]);
					}
					wordaux.reverse();
					for(int j = x + 1; j < 15 && spaces[i][j]!=' '; j++){
						wordaux.append(spaces[i][j]);
					}
					if(!dictionary.contains(wordaux.toString())){
						return false;
					}
				}
			}
			if(spaces[y-1][x]!=' ' || spaces[y+m.getWord().length()][x]!=' '){
				StringBuffer wordaux = new StringBuffer();
				for(int j = y - 1; j > 0 && spaces[j][x]!=' '; j--){
					wordaux.append(spaces[j][x]);
				}
				wordaux.reverse();
				wordaux.append(m.getWord());
				for(int j = y + m.getWord().length(); j<BoardState.SIZE && spaces[j][x]!=' '; j++){
					wordaux.append(spaces[j][x]);
				}
				if(wordaux.length()>7){
					return false;
				}
				if(!dictionary.contains(wordaux.toString())){
					return false;
				}
			}
		}
		else{
			if(x + word.length()>=BoardState.SIZE){
				return false;
			}	
			for(int i=x , letterCounter = 0; i < x + word.length(); i++, letterCounter++){
				if(b.isOccupied(i, y-1) || b.isOccupied(i, y+1)){
					StringBuffer wordaux= new StringBuffer(word.charAt(letterCounter));
					for(int j = y - 1; j > 0 && spaces[j][i]!=' '; j--){
						wordaux.append(spaces[j][i]);
					}
					wordaux.reverse();
					for(int j = y + 1; j < 15 && spaces[j][i]!=' '; j++){
						wordaux.append(spaces[j][i]);
					}
					if(!dictionary.contains(wordaux.toString())){
						return false;
					}
				}
			}
			if(spaces[y][x-1]!=' ' || spaces[y][x+m.getWord().length()]!=' '){
				StringBuffer wordaux = new StringBuffer();
				for(int j = x - 1; j > 0 && spaces[y][j]!=' '; j--){
					wordaux.append(spaces[y][j]);
				}
				wordaux.reverse();
				wordaux.append(m.getWord());
				for(int j = x + m.getWord().length(); j<BoardState.SIZE && spaces[y][j]!=' '; j++){
					wordaux.append(spaces[y][j]);
				}
				if(wordaux.length()>7){
					return false;
				}
				if(!dictionary.contains(wordaux.toString())){
					return false;
				}
			}
		}
		return true;		
	}

	public static boolean hasWord(String word, int[] letters) {
		//TODO se podria mejorar el codigo y ser mas eficiente, ya ponerlas en el tablero

		int i;
		for(i = 0; i < word.length() && letters[word.charAt(i)-'A'] >= 1; i++) {
			letters[(int)(word.charAt(i)-'A')]--;
		}
		if(i == word.length()){
			for(i=0; i < word.length() ; i++){
				letters[(word.charAt(i)-'A')]++;
			}
			return true;
		}
		for(; i >= 0 ; i--){
			letters[(word.charAt(i)-'A')]++;
		}
		return false;
	}
}
