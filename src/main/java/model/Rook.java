package model;

/**
 * The rook-figures of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Rook extends Figure {
	
	private Movement rookMovement;
	
	/**
	 * Expects the color of the rook.
	 * @param color The color of the rook, white is equivalent to 'true' and black to 'false'.
	 * @param l The line of the rook.
	 * @param c The column of the rook. 
	 */
	public Rook(boolean color, int l, int c) {
		super(l, c, color,'r');
		rookMovement = new RookMovement();
	}
	
	@Override
	public String[][] move(Tile[][] tiles, boolean checkTest) {
		return rookMovement.move(tiles, this, checkTest);
	}
}
