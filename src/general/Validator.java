package general;

import java.util.Set;

import general.Board.Direction;

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
	
	/*revisa si poner una palabra ahi es valido o no....	REVISARRRRR  REPITE CODIGOOO*/
	public boolean validateMovement(int x, int y, String word, char[][] spaces, Board.Direction dir){
		//TODO REVISAR BIEN T O D O repite codigo y capaz no anda bien
		if(x < 0 || y < 0){	//si la estoy copiando desde afuera
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
	
	
}