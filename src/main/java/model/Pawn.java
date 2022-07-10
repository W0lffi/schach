package model;

/**
 * The pawn-figures of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Pawn extends Figure {
	
	private Movement pawnMovement;
	/**
	 * Expects the color of the king.
	 * @param color The color of the king, white is equivalent to 'true' and black to 'false'.
	 * @param l The line of the king.
	 * @param c The column of the king. 
	 */
	public Pawn(boolean color, int l, int c) {
		super(l, c, color,'p');
		pawnMovement = new PawnMovement();
	}
	
	@Override
	public String[][] move(Tile[][] tiles, boolean checkTest) {
		return pawnMovement.move(tiles, this, checkTest);
	}
	
	/**
	 * This method transform a pawn to a rook, bishop, knight, or queen depending on players choice.
	 * @param tiles The board with the figures.
	 * @param l The line of the pawn.
	 * @param c The column of the pawn.
	 * @param trans Type of the new figure, depending on players choice.
	 * @return A former pawn figure which is transformed into a different figure.
	 */
	public static Figure transformation(Tile[][] tiles, int l, int c, char trans) {
		boolean color = tiles[l][c].getFigure().isColor();
		switch(trans) {
			case 'R':
				tiles[l][c] = new Tile(new Rook(color, l, c));
			break;
			case 'B':
				tiles[l][c] = new Tile(new Bishop(color, l , c));
			break;
			case 'N':
				tiles[l][c] = new Tile(new Knight(color, l , c));
			break;
			case 'Q':
				tiles[l][c] = new Tile(new Queen(color, l, c));
			break;
				default:
			break;
		}
		return tiles[l][c].getFigure();
	}
}
