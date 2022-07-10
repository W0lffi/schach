package model;

import schach.Constants;

/**
 * The king's move set.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class KingMovement implements Movement {
	@Override
	public String[][] move(Tile[][] tiles, Figure fig, boolean checkTest) {
		String[][] straightMovePos;
		if(checkTest) {
			straightMovePos = straightMovementWithCheckTest(tiles, fig.defaultMovePos(tiles), fig);
		}else {
			straightMovePos = straightMovementWithoutCheckTest(tiles, fig.defaultMovePos(tiles), fig);
		}
		String[][] diagonalMovePos = diagonalMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] normalMovePos = fig.concatMovement(straightMovePos, diagonalMovePos);
		String[][] castlingMovePos = normalMovePos;
		if(fig.isFirstMove() && !fig.isCheck() && checkTest) {
			castlingMovePos = castlings(tiles, normalMovePos, fig);
		}
		return fig.concatMovement(normalMovePos, castlingMovePos);
	}
	
	/**
	 * Calculates the left, top, right and bottom move possibilities of the King.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities-array.
	 * @param fig The current King.
	 * @return The straight move possibilities.
	 */
	private String[][] straightMovementWithCheckTest(Tile[][] tiles, String[][] movePos, Figure fig){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c = fig.getPosition().getColumn();
		if(c+1 < Constants.BOARD_DIMENSIONS && !King.isOwnKingDefended(tiles, l, c+1, fig)) {
			movePossibilities[l][c+1] = fig.scanDir(tiles, l, c+1, fig); // right side
		}
		if(c-1 >= 0 && !King.isOwnKingDefended(tiles, l, c-1, fig)) {
			movePossibilities[l][c-1] = fig.scanDir(tiles, l, c-1, fig); // left side
		}
		if(l-1 >= 0 && !King.isOwnKingDefended(tiles, l-1, c, fig)) {
			movePossibilities[l-1][c] = fig.scanDir(tiles, l-1, c, fig); // top side
		}
		if(l+1 < Constants.BOARD_DIMENSIONS && !King.isOwnKingDefended(tiles, l+1, c, fig)) {
			movePossibilities[l+1][c] = fig.scanDir(tiles, l+1, c, fig); // bottom side
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the left, top, right and bottom move possibilities of the King.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities-array.
	 * @param fig The current King.
	 * @return The straight move possibilities.
	 */
	private String[][] straightMovementWithoutCheckTest(Tile[][] tiles, String[][] movePos, Figure fig){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c = fig.getPosition().getColumn();
		if(c-1 >= 0) {
			movePossibilities[l][c-1] = fig.scanDir(tiles, l, c-1, fig); // left side
		}
		if(l-1 >= 0) {
			movePossibilities[l-1][c] = fig.scanDir(tiles, l-1, c, fig); // top side
		}
		if(c+1 < Constants.BOARD_DIMENSIONS) {
			movePossibilities[l][c+1] = fig.scanDir(tiles, l, c+1, fig); // right side
		}
		if(l+1 < Constants.BOARD_DIMENSIONS) {
			movePossibilities[l+1][c] = fig.scanDir(tiles, l+1, c, fig); // bottom side
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the left-top, right-top, left-bottom and right-bottom move possibilities.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities-array.
	 * @param fig The current King.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The left-top, right-top, left-bottom and right-bottom move possibilities.
	 */
	private String[][] diagonalMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] diagonalmovePosTop;
		String[][] diagonalmovePosBot;
		if(checkTest) {
			diagonalmovePosTop = diagonalMovementTopWithCheckTest(tiles, movePos, fig);
			diagonalmovePosBot = diagonalMovementBotWithCheckTest(tiles, movePos, fig);
		}else {
			diagonalmovePosTop = diagonalMovementTopWithoutCheckTest(tiles, movePos, fig);
			diagonalmovePosBot = diagonalMovementBotWithoutCheckTest(tiles, movePos, fig);
		}
		
		return fig.concatMovement(diagonalmovePosTop, diagonalmovePosBot);
	}
	
	/**
	 * Calculates the diagonal movement on top side and check whether the king would be in check.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current king.
	 * @return The available diagonal moves on top side. 
	 */
	private String[][] diagonalMovementTopWithCheckTest(Tile[][] tiles, String[][] movePos, Figure fig){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c = fig.getPosition().getColumn();
		if(l-1 >= 0 && c+1 < Constants.BOARD_DIMENSIONS && !King.isOwnKingDefended(tiles, l-1, c+1, fig)) {
			movePossibilities[l-1][c+1] = fig.scanDir(tiles, l-1, c+1, fig); // right-top side
		}
		if(l-1 >= 0 && c-1 >= 0 && !King.isOwnKingDefended(tiles, l-1, c-1, fig)) {
			movePossibilities[l-1][c-1] = fig.scanDir(tiles, l-1, c-1, fig); // left-top side
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the diagonal movement on bottom side and check whether the king would be in check.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current king.
	 * @return The available diagonal moves on bottom side. 
	 */
	private String[][] diagonalMovementBotWithCheckTest(Tile[][] tiles, String[][] movePos, Figure fig){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c = fig.getPosition().getColumn();
		if(l+1 < Constants.BOARD_DIMENSIONS && c+1 < Constants.BOARD_DIMENSIONS && !King.isOwnKingDefended(tiles, l+1, c+1, fig)) {
			movePossibilities[l+1][c+1] = fig.scanDir(tiles, l+1, c+1, fig); // right-bottom side
		}
		if(l+1 < Constants.BOARD_DIMENSIONS && c-1 >= 0 && !King.isOwnKingDefended(tiles, l+1, c-1, fig)) {
			movePossibilities[l+1][c-1] = fig.scanDir(tiles, l+1, c-1, fig); // left-bottom side
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the diagonal movement on top side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current king.
	 * @return The available diagonal moves on top side. 
	 */
	private String[][] diagonalMovementTopWithoutCheckTest(Tile[][] tiles, String[][] movePos, Figure fig){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c = fig.getPosition().getColumn();
		if(l-1 >= 0 && c+1 < Constants.BOARD_DIMENSIONS) {
			movePossibilities[l-1][c+1] = fig.scanDir(tiles, l-1, c+1, fig); // right-top side
		}
		if(l-1 >= 0 && c-1 >= 0) {
			movePossibilities[l-1][c-1] = fig.scanDir(tiles, l-1, c-1, fig); // left-top side
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the diagonal movement on bottom side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current king.
	 * @return The available diagonal moves on bottom side. 
	 */
	private String[][] diagonalMovementBotWithoutCheckTest(Tile[][] tiles, String[][] movePos, Figure fig){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c = fig.getPosition().getColumn();
		if(l+1 < Constants.BOARD_DIMENSIONS && c+1 < Constants.BOARD_DIMENSIONS) {
			movePossibilities[l+1][c+1] = fig.scanDir(tiles, l+1, c+1, fig); // right-bottom side
		}
		if(l+1 < Constants.BOARD_DIMENSIONS && c-1 >= 0) {
			movePossibilities[l+1][c-1] = fig.scanDir(tiles, l+1, c-1, fig); // left-bottom side
		}
		return movePossibilities;
	}
	
	/**
	 * This method checks whether the king can perform a castling.
	 * @param tiles The current board with the figures on it.
	 * @param movePos The string-array with the movement possibilities of the king.
	 * @param fig The king.
	 * @return The advanced movement possibilities.
	 */
	private String[][] castlings(Tile[][] tiles, String[][] movePos, Figure fig){
		int l = fig.isColor() ? 7 : 0;
		String[][] smallCastlingMovePos = smallCastling(tiles, movePos, fig, l);
		String[][] bigCastlingMovePos = fig.defaultMovePos(tiles);
		if(tiles[l][1] == null) {
			bigCastlingMovePos = bigCastling(tiles, bigCastlingMovePos, fig, l);
		}
		return fig.concatMovement(smallCastlingMovePos, bigCastlingMovePos);
	}
	
	/**
	 * This method checks whether the king can perform a small castling.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current king.
	 * @param l The factor which determines the line of the castling.
	 * @return The advanced move possibilities if they're possible.
	 */
	private String[][] smallCastling(Tile[][] tiles, String[][] movePos, Figure fig, int l){
		String[][] movePossibilities = movePos;
		if(tiles[l][5] == null && !King.isOwnKingDefended(tiles, l, 5, fig)
		&& tiles[l][6] == null && !King.isOwnKingDefended(tiles, l, 6, fig)
		&& tiles[l][7] != null && tiles[l][7].getFigure().getClass().equals(Rook.class) && tiles[l][7].getFigure().isColor() == fig.isColor() && tiles[l][7].getFigure().isFirstMove()) {
			movePossibilities[l][6] = Constants.HAS_MOVE;
		}
		return movePossibilities;
	}
	
	/**
	 * This method checks whether the king can perform a big castling.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current king.
	 * @param l The factor which determines the line of the castling.
	 * @return The advanced move possibilities if they're possible.
	 */
	private String[][] bigCastling(Tile[][] tiles, String[][] movePos, Figure fig, int l){
		String[][] movePossibilities = movePos;
		if(tiles[l][2] == null && !King.isOwnKingDefended(tiles, l, 2, fig)
		&& tiles[l][3] == null && !King.isOwnKingDefended(tiles, l, 3, fig)
		&& tiles[l][0] != null && tiles[l][0].getFigure().getClass().equals(Rook.class) && tiles[l][0].getFigure().isColor() == fig.isColor() && tiles[l][0].getFigure().isFirstMove()) {
			movePossibilities[l][2] = Constants.HAS_MOVE;
		}
		return movePossibilities;
	}
}