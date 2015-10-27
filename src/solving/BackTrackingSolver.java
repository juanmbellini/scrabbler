package solving;

import general.BoardState;

/**
 * Class used to find an exact solution to the problem with a backtracking strategy. 
 */
public class BackTrackingSolver extends Solver {

	public BackTrackingSolver(BoardState initial, boolean visual) {
		super(initial, visual);
	}

	@Override
	protected BoardState solve(BoardState initial) {
		//TODO Put code in here (i.e. wrapper de la función backtracking)
		//TODO en cada estado nuevo hacer:
		print(best.toPrettyString());	//Cambiar best por current o lo que corresponda
		//No te preocupes si no hay modo visual activado, la función print se encarga de no imprimir si no hay visualizador
		return best;
	}

}
