package stubs;

import model.Figure;
import model.Tile;
import schach.Constants;

/**
 * A stub of the figure for JUnit tests.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
public class FigureStub extends Figure {
	
	private boolean firstMove;
	
	/**
	 * Constructor of the FigureStub used in testing
	 * @param l The line of the figure
	 * @param c The column of the figure
	 * @param color The color of the figure
	 * @param name The char type of the figure
	 */
	public FigureStub(int l, int c, boolean color, char name) {
		super(l, c, color, name);
	}

	@Override
	public String[][] move(Tile[][] tiles, boolean checkTest) {
		String[][] movements = new String[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
		for(int l=0; l < Constants.BOARD_DIMENSIONS; l++) {
			for(int c=0; c < Constants.BOARD_DIMENSIONS; c++) {
				movements[l][c] = "false";
			}
		}
		return movements;
	}

	// Getters and Setters
	@Override
	public boolean isFirstMove() {return this.firstMove;}
	@Override
	public void setFirstMove(boolean firstMove) {this.firstMove = firstMove;}
}
