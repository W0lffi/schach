package model;

/**
 * The knight-figures of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Knight extends Figure {
	
	private Movement knightMovement;
	
	/**
	 * This method expects the color of the knight.
	 * @param color The color of the knight: white = 'true' and black = 'false'.
	 * @param l The line of the knight.
	 * @param c The column of the knight. 
	 */
	public Knight(boolean color, int l, int c) {
		super(l, c, color, 'n');
		knightMovement = new KnightMovement();
	}
	
	@Override
	public String[][] move(Tile[][] tiles, boolean checkTest) {
		return knightMovement.move(tiles, this, checkTest);
	}
}
