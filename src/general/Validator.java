package general;

import java.util.Set;

import general.Board.Direction;

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
	
	/*revisa si poner una palabra ahi es valido o no....	REVISARRRRR  REPITE CODIGOOO*/
	public static boolean isValidMovement(int x, int y, String word, char[][] spaces, Board.Direction dir){
		//TODO REVISAR BIEN T O D O repite codigo y capaz no anda bien
		if(dictionary == null || x < 0 || y < 0){	//si la estoy copiando desde afuera
			return false;
		}
		if(dir == Direction.DOWN){
			if(y + word.length()>Board.SIZE){  //si se pasa
				return false;
			}
			for(int i=y; i < y + word.length(); i++){   		//se fija si 
				if(spaces[y][x-1]!=' ' || spaces[y][x+1]!=' '){ //no forma palabras nuevas y si lo 
					StringBuffer wordaux= new StringBuffer();	//hace las checkea y se fija que exista
					for(int j = x; j > 0 && spaces[y][j-1]!=' '; j--){
						wordaux.append(spaces[y][j]);
					}
					wordaux.reverse();
					for(int j = x; j < 15 && spaces[y][j+1]!=' '; j++){
						wordaux.append(spaces[y][j]);
					}
					if(!wordExists(wordaux.toString())){
						return false;
					}
				}
			}
		}
		else{
			if(x + word.length()>Board.SIZE){
				return false;
			}	
			for(int i=x; i < x + word.length(); i++){
				if(spaces[y-1][x]!=' ' || spaces[y+1][x]!=' '){
					StringBuffer wordaux= new StringBuffer();
					for(int j = y; j > 0 && spaces[j-1][x]!=' '; j--){
						wordaux.append(spaces[j][x]);
					}
					wordaux.reverse();
					for(int j = y; j < 15 && spaces[j+1][x]!=' '; j++){
						wordaux.append(spaces[j][x]);
					}
					if(!wordExists(wordaux.toString())){
						return false;
					}
				}
			}
		}
		return true;		
	}

	public static boolean hasWord(String word, int[] letters) {
		//TODO se podria mejorar el codigo y ser mas eficiente, ya ponerlas en el tablero
		int i=0;
		for(i=0; i < word.length() && letters[(int)(word.charAt(i)-'A')] >=1; i++){
			letters[(int)(word.charAt(i)-'A')]--;
		}
		if(i == word.length()){
			for(i=0; i < word.length() ; i++){
				letters[(int)(word.charAt(i)-'A')]++;
			}
			return true;
		}
		for(; i >= 0 ; i--){
			letters[(int)(word.charAt(i)-'A')]++;
		}
		return false;
	}
}
