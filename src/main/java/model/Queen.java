package model;

/**
 * The queen-figures of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Queen extends Figure {
	
	private Movement rookMovement;
	private Movement bishopMovement;
	/**
	 * This method expects the color of the queen.
	 * @param color The color of the queen: white = 'true' and black = 'false'.
	 * @param l The line of the queen.
	 * @param c The column of the queen. 
	 */
	public Queen(boolean color, int l, int c) {
		super(l, c, color,'q');
		rookMovement = new RookMovement();
		bishopMovement = new BishopMovement();
	}
	
	@Override
	public String[][] move(Tile[][] tiles, boolean checkTest) {
		String[][] movePossibilitiesR = rookMovement.move(tiles, this, checkTest);
		String[][] movePossibilitiesB = bishopMovement.move(tiles, this, checkTest);
		return concatMovement(movePossibilitiesR, movePossibilitiesB);
	}
}