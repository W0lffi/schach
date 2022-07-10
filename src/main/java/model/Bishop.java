package model;

/**
 * The bishop-figures of the game. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Bishop extends Figure {
	
	private Movement bishopMovement;
	
	/**
	 * Expects the color of the figure.
	 * @param color The color of the bishop, white is equivalent to 'true' and black to 'false'.
	 * @param l The line of the figure.
	 * @param c The column of the figure.
	 */
	public Bishop(boolean color, int l, int c) {
		super(l, c, color,'b');
		bishopMovement = new BishopMovement();
	}
	
	@Override 
	public String[][] move(Tile[][] tiles, boolean checkTest) {
		return bishopMovement.move(tiles, this, checkTest);
	}
}
