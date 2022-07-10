package model;

import schach.Constants;

/**
 * The king-figures of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class King extends Figure {
	
	private Movement kingMovement;
	
	/**
	 * This method expects the color of a figure.
	 * @param color The color of the king: white = 'true' and black = 'false'.
	 * @param l The line of the king.
	 * @param c The column of the king. 
	 */
	public King(boolean color, int l, int c) {
		super(l, c, color,'k');
		super.setCheck(false);
		kingMovement = new KingMovement();
	}

	@Override
	public String[][] move(Tile[][] tiles,boolean checkTest) {
		return kingMovement.move(tiles, this, checkTest);
	}
	
	/**
	 * Calculate whether a figure can move and the own king wouldn't be in check after it.
	 * @param tiles The board with the figures.
	 * @param testLine The line of the coordinate which should be checked.
	 * @param testColumn The column of the coordinate which should be checked.
	 * @param fig The figure which the player wants to move.
	 * @return True if the king wouldn't be in check after it otherwise false.
	 */
	public static boolean isOwnKingDefended(Tile[][] tiles, int testLine, int testColumn, Figure fig) {
		Tile [][] tmpTiles = Tile.copyTheTiles(tiles);
		String[][] tmpMovePos = fig.defaultMovePos(tmpTiles);
		if(tmpTiles[testLine][testColumn] == null || tmpTiles[testLine][testColumn].getFigure().isColor()!= fig.isColor()) {
			tmpTiles[testLine][testColumn] = new Tile(fig);
			tmpTiles[fig.getPosition().getLine()][fig.getPosition().getColumn()] = null;
			tmpMovePos = fig.defaultMovePos(tmpTiles);
			Figure ownKing = Figure.getKingPos(tiles, !fig.isColor());
			for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
				for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
					if(!fig.getClass().equals(King.class)
					&& kingInCheckWhenFigureOnTestfield(tmpTiles, tmpMovePos, fig, new Position(l, c, ownKing.getPosition().getLine(), ownKing.getPosition().getColumn()))) {
						return false;
					}
					else if(fig.getClass().equals(King.class) && ifFigIsKing(tmpTiles, tmpMovePos, fig, new Position(l, c, testLine, testColumn))) {
						return true;
					}
				}
			}
		}
		return scanClass(fig);
	}
	
	/**
	 * Calculates the correct boolean return value depending on figure is king.
	 * @param fig The current figure.
	 * @return False if the figure is a king.
	 */
	private static boolean scanClass(Figure fig) {
		return fig.getClass().equals(King.class) ? false : true;
	}
	
	/**
	 * Calculates whether the own king would be in check if the figure which can be a king too stand on the test field.
	 * @param tmpTiles The temporary board with the figures.
	 * @param tmpMovePossibilities The temporary move possibilities.
	 * @param fig The current figure.
	 * @param testPos The field where figure temporarily stands on.
	 * @return True if the own king would be in check.
	 */
	private static boolean kingInCheckWhenFigureOnTestfield(Tile[][] tmpTiles, String[][] tmpMovePossibilities, Figure fig, Position testPos) {
		String[][] tmpMovePos = tmpMovePossibilities;
		int l = testPos.getLine();
		int c = testPos.getColumn();
		int testLine = testPos.getDestLine();
		int testColumn = testPos.getDestColumn();
		if(tmpTiles[l][c] != null && tmpTiles[l][c].getFigure().isColor() != fig.isColor() && !tmpTiles[l][c].getFigure().getClass().equals(King.class)) {
			tmpMovePos = tmpTiles[l][c].getFigure().move(tmpTiles, false);
				if(tmpMovePos[testLine][testColumn] == Constants.HAS_ENEMY) {
					return true;
				}
		}
		return false;
	}
	
	/**
	 * Calculates whether the own king move is denied by a enemy figure because he would be in check after it.
	 * @param tmpTiles The temporary board with the figures.
	 * @param tmpMovePossibilities The temporary move possibilities.
	 * @param fig The current king.
	 * @param testPos The field where king temporarily stands on.
	 * @return
	 */
	private static boolean ifFigIsKing(Tile[][] tmpTiles, String[][] tmpMovePos, Figure fig, Position testPos) {
		if(kingInCheckWhenFigureOnTestfield(tmpTiles, tmpMovePos, fig, testPos)) {
			return true;
		}else if(moveDeniedByWhichFigure(tmpTiles, tmpMovePos, fig, testPos)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Calculates whether the enemy king denies the move of the king because the own king would be in check after it.
	 * @param tmpTiles The temporary board with the figures.
	 * @param tmpMovePossibilities The temporary move possibilities.
	 * @param fig The current king.
	 * @param testPos The field where king temporarily stands on.
	 * @return True if the own king would be in check.
	 */
	private static boolean moveDeniedByWhichFigure(Tile[][] tmpTiles, String[][] tmpMovePossibilities, Figure fig, Position testPos) {
		String[][] tmpMovePos = tmpMovePossibilities;
		int testLine = testPos.getDestLine();
		int testColumn = testPos.getDestColumn();
		Figure enemyKing  = Figure.getKingPos(tmpTiles, fig.isColor());
		tmpMovePos = enemyKing.move(tmpTiles, false);
		return tmpMovePos[testLine][testColumn] == Constants.HAS_ENEMY ? true : false;
	}
	
	/**
	 * Checks if the enemy King is in check after a move.
	 * @param tiles The board with the figures.
	 * @param color The color of the team.
	 * @return Return true if the figure put the enemy king in check.
	 */
	public static boolean verifyCheckEnemy(Tile[][] tiles, boolean color) {
		String[][] tmpMovePos;
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tiles[l][c] != null && tiles[l][c].getFigure().isColor() == color) {
					tmpMovePos = tiles[l][c].getFigure().move(tiles, false);
					if(checkMovePos(tiles, tmpMovePos, color)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether a figure has an enemy which is a king.
	 * @param tiles The board with the figures.
	 * @param tmpMovePos The move possibilities of the figure.
	 * @param color The color of the figure.
	 * @return True if the figure has a king as enemy.
	 */
	private static boolean checkMovePos(Tile[][] tiles, String[][] tmpMovePos, boolean color) {
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tmpMovePos[l][c] == "enemy" &&  tiles[l][c].getFigure().getClass().equals(King.class) && tiles[l][c].getFigure().isColor() != color) {
					return true;
				}
			}
		}
		return false;
	}
	
}