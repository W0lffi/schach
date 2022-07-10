package model;

import schach.Constants;

/**
 * The pawn's move set.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class PawnMovement implements Movement {
	@Override
	public String[][] move(Tile[][] tiles, Figure fig, boolean checkTest) {
		int l = fig.getPosition().getLine();
		int m = fig.isColor() ? -1 : 1;
		
		String[][]  movePosEmptyFields = fig.defaultMovePos(tiles);
		if(checkTest && l+m >= 0 && l+m < Constants.BOARD_DIMENSIONS) {
			movePosEmptyFields = getMovePosWithCheckTesting(tiles, fig.defaultMovePos(tiles), fig, m);
		}else if(!checkTest && l+m >= 0 && l+m < Constants.BOARD_DIMENSIONS){
			movePosEmptyFields = getMovePosWithoutCheckTesting(tiles, fig.defaultMovePos(tiles), fig, m);
		}
		String[][] movePosEnemyFields = fig.defaultMovePos(tiles);
		if(fig.getPosition().isInBorder(l+m, fig.getPosition().getColumn())) {
			movePosEnemyFields = getEnemyPos(tiles, fig, checkTest);
		}
		
		String[][] movePossibilities = fig.concatMovement(movePosEmptyFields, movePosEnemyFields);
		
		movePossibilities = hitEnPassant(tiles, movePossibilities, fig, checkTest);

		return movePossibilities;
	}
	
	/**
	 * Calculate the empty fields where the pawn can move on and test whether the own king would be in check after it.
	 * @param tiles The board with the figures.
	 * @param fig The current pawn.
	 * @param m The factor which is added by line depending on the color of the pawn (white=-1, black=1).
	 * @return The fields which are empty and the pawn can move on it.
	 */
	private String[][] getMovePosWithCheckTesting(Tile[][] tiles, String[][] movePos, Figure fig, int m){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
			if(tiles[l+m][c] == null && King.isOwnKingDefended(tiles, l+m, c, fig)) { //move possibilities
				movePossibilities[l+m][c] = Constants.HAS_MOVE;
			}
			if(l+(2*m) >= 0 && l+(2*m) < Constants.BOARD_DIMENSIONS && fig.isFirstMove() && tiles[l+(2*m)][c] == null && King.isOwnKingDefended(tiles, l+(2*m), c, fig) && (movePossibilities[l+m][c] == Constants.HAS_MOVE || tiles[l+m][c] == null)) {
				movePossibilities[l+(2*m)][c] = Constants.HAS_MOVE;
			}
		return movePossibilities;
	}
	
	/**
	 * Calculate the empty fields where the pawn can move on.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities-array.
	 * @param fig The current pawn.
	 * @param m The factor which is added by line depending on the color of the pawn (white=-1, black=1).
	 * @return The fields which are empty and the pawn can move on it.
	 */
	private String[][] getMovePosWithoutCheckTesting(Tile[][] tiles, String[][] movePos, Figure fig, int m){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(tiles[l+m][c] == null){ 												 //move possibilities
			movePossibilities[l+m][c] = Constants.HAS_MOVE;
		}
		if(l+(2*m) >= 0 && l+(2*m) < Constants.BOARD_DIMENSIONS && fig.isFirstMove() && tiles[l+(2*m)][c] == null && (movePossibilities[l+m][c] == Constants.HAS_MOVE || tiles[l+m][c] == null)) {
			movePossibilities[l+(2*m)][c] = Constants.HAS_MOVE;
		}
		return movePossibilities;
	}
	
	/**
	 * Calculate the fields which are contain enemies and the pawn can beat them and test whether the own king would be in check after it.
	 * @param tiles The board with the figures.
	 * @param fig The current pawn.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The fields which are contain enemies and the pawn can beat them.
	 */
	private String[][] getEnemyPos(Tile[][] tiles, Figure fig, boolean checkTest){
		int m = fig.isColor() ? -1 : 1;
		String[][] enemyMovePosLeft = fig.defaultMovePos(tiles);
		String[][] enemyMovePosRight = fig.defaultMovePos(tiles);
		if(checkTest) {
			if(fig.getPosition().getColumn()-1 >= 0) {
				enemyMovePosLeft = getEnemyPosWithCheckTest(tiles, fig, m, -1);
			}
			if(fig.getPosition().getColumn()+1 < Constants.BOARD_DIMENSIONS) {
				enemyMovePosRight = getEnemyPosWithCheckTest(tiles, fig, m, 1);
			}
		}else {
			if(fig.getPosition().getColumn()-1 >= 0) {
				enemyMovePosLeft = getEnemyPosWithoutCheckTest(tiles, fig, m, -1);
			}
			if(fig.getPosition().getColumn()+1 < Constants.BOARD_DIMENSIONS) {
				enemyMovePosRight = getEnemyPosWithoutCheckTest(tiles, fig, m, 1);
			}
		}
		return fig.concatMovement(enemyMovePosLeft, enemyMovePosRight);
	}
	
	/**
	 * Calculates the enemies which are beatable and whether the own king would be in check after it.
	 * @param tiles The board with figures.
	 * @param fig The current pawn.
	 * @param m The factor which is added to the line.
	 * @param n The factor which is added to the column.
	 * @return The beatable enemies when the isn't in check after it.
	 */
	private String[][] getEnemyPosWithCheckTest(Tile[][] tiles, Figure fig, int m , int n){
		String[][] movePossibilities = fig.defaultMovePos(tiles);
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(tiles[l+m][c+n] != null && tiles[l+m][c+n].getFigure().isColor() != fig.isColor() && King.isOwnKingDefended(tiles, l+m, c+n, fig)) { // right side
			movePossibilities[l+m][c+n] = Constants.HAS_ENEMY;
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the beatable enemies but doesn't test whether the king would be in check after it.
	 * @param tiles The board with the figures.
	 * @param fig The current pawn.
	 * @param m The factor which is added to the line.
	 * @param n The factor which is added to the column.
	 * @return The beatable enemies.
	 */
	public String[][] getEnemyPosWithoutCheckTest(Tile[][] tiles, Figure fig, int m, int n) {
		String[][] movePossibilities = fig.defaultMovePos(tiles);
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(tiles[l+m][c+n] != null && tiles[l+m][c+n].getFigure().isColor() != fig.isColor()) { // right side
			movePossibilities[l+m][c+n] = Constants.HAS_ENEMY;
		}
		return movePossibilities;
	}
	
	/**
	 * This method check whether the current pawn can take an enemy with enPassant.
	 * @param tiles The board with the figures.
	 * @param movePossibilities The string-array with the move possibilities of figure which is moved.
	 * @param l The line of the pawn.
	 * @param c The column of the pawn.
	 * @param fig The pawn which is moved.
	 * @return The advanced possible movement.
	 */
	private String[][] hitEnPassant(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		if(fig.isHitEnPassantLeft() || fig.isHitEnPassantRight()) {
			int l = fig.getPosition().getLine();
			int c = fig.getPosition().getColumn();
			int m = fig.isColor() ? -1 : 1;				// If the figure is white test a field one line up(-1), if it's black test one line down(1).
			int n = fig.isHitEnPassantLeft() ? -1 : 1;	// If the fig can hit en passant left test one column more left else test right.
			if(c+n >= 0 && c+n < Constants.BOARD_DIMENSIONS) {
				if(checkTest && King.isOwnKingDefended(tiles, l+m, c+n, fig)) {
						movePossibilities[l+m][c+n] = Constants.HAS_ENEMY;
				}
				else {
					movePossibilities[l+m][c+n] = Constants.HAS_ENEMY;
				}
			}
		}
		return movePossibilities;
	}
}