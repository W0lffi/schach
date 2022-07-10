package model;

import schach.Constants;

/**
 * The bishop's move set.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class BishopMovement implements Movement {
	@Override
	public String[][] move(Tile[][] tiles, Figure fig, boolean checkTest) {
		String[][] movePosTopLeft = topMovementLeft(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosTopRight = topMovementRight(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosTop = fig.concatMovement(movePosTopLeft, movePosTopRight);
		
		String[][] movePosBotRight = bottomMovementRight(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosBotLeft = bottomMovementLeft(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosBot = fig.concatMovement(movePosBotLeft, movePosBotRight);
		
		return fig.concatMovement(movePosTop, movePosBot);
	}
	
	/**
	 * Calculates the top left move possibilities.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities. 
	 * @param fig The current bishop.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on top left side.
	 */
	private String[][] topMovementLeft(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;c-i>=0;i++) { // top-left side
				if(l-i >= 0 && King.isOwnKingDefended(tiles, l-i, c-i, fig)) {
					movePossibilities[l-i][c-i] = fig.scanDir(tiles, l-i, c-i, fig);
					if(!movePossibilities[l-i][c-i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}
			}
		}else {
			for(int i=1;c-i>=0;i++) { // top-left side
				if(l-i >= 0) {
					movePossibilities[l-i][c-i] = fig.scanDir(tiles, l-i, c-i, fig);
					if(!movePossibilities[l-i][c-i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the top right move possibilities.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities. 
	 * @param fig The current bishop.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on top right side.
	 */
	private String[][] topMovementRight(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;c+i<Constants.BOARD_DIMENSIONS;i++) { // top-right side
				if(Constants.BOARD_DIMENSIONS > l+i && King.isOwnKingDefended(tiles, l+i, c+i, fig)) {
					movePossibilities[l+i][c+i] = fig.scanDir(tiles, l+i, c+i, fig);
					if(!movePossibilities[l+i][c+i].equals(Constants.HAS_MOVE))
						break;
				}
			}
		}else {
			for(int i=1;c+i<Constants.BOARD_DIMENSIONS;i++) { // top-right side
				if(Constants.BOARD_DIMENSIONS > l+i) {
					movePossibilities[l+i][c+i] = fig.scanDir(tiles, l+i, c+i, fig);
					if(!movePossibilities[l+i][c+i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the bottom left move possibilities.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities. 
	 * @param fig The current bishop.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on bottom left side.
	 */
	private String[][] bottomMovementLeft(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;c-i>=0;i++) { // bottom-left side
				if(l+i < Constants.BOARD_DIMENSIONS && King.isOwnKingDefended(tiles, l+i, c-i, fig)) {
					movePossibilities[l+i][c-i] = fig.scanDir(tiles, l+i, c-i, fig);
					if(!movePossibilities[l+i][c-i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}
			}
		}else {
			for(int i=1;c-i>=0;i++) { // bottom-left side
				if(l+i < Constants.BOARD_DIMENSIONS) {
					movePossibilities[l+i][c-i] = fig.scanDir(tiles, l+i, c-i, fig);
					if(!movePossibilities[l+i][c-i].equals(Constants.HAS_MOVE)) {
						break;
					}
					}
				}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the bottom right move possibilities.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities. 
	 * @param fig The current bishop.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on bottom right side.
	 */
	private String[][] bottomMovementRight(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;c+i<Constants.BOARD_DIMENSIONS;i++) { // bottom-right side
				if(l-i >= 0 && King.isOwnKingDefended(tiles, l-i, c+i, fig)) {
					movePossibilities[l-i][c+i] = fig.scanDir(tiles, l-i, c+i, fig);
					if(!movePossibilities[l-i][c+i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}
			}
		}else {
			for(int i=1;c+i<Constants.BOARD_DIMENSIONS;i++) { // bottom-right side
				if(l-i >= 0) {
					movePossibilities[l-i][c+i] = fig.scanDir(tiles, l-i, c+i, fig);
					if(!movePossibilities[l-i][c+i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}
			}
		}
		return movePossibilities;
	}
}