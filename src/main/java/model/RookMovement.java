package model;

import schach.Constants;

/**
 * The rook's move set.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class RookMovement implements Movement {
	@Override
	public String[][] move(Tile[][] tiles, Figure fig, boolean checkTest) {
		String[][] movePosLeft = leftMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosTop = topMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosTopLeft = fig.concatMovement(movePosLeft, movePosTop);
		
		String[][] movePosRight = rightMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosBot = botMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosRightBot = fig.concatMovement(movePosRight, movePosBot);
		return fig.concatMovement(movePosTopLeft, movePosRightBot);
	}
	
	/**
	 * Calculates the available fields on the left side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current rook.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available fields on the left side. 
	 */
	private String[][] leftMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;c-i>=0;i++) { // left side
				if(King.isOwnKingDefended(tiles, l, c-i, fig)) {
					movePossibilities[l][c-i] = fig.scanDir(tiles, l, c-i, fig);
					if(!movePossibilities[l][c-i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}else if(!movePossibilities[l][c-i].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}else {
			for(int i=1;c-i>=0;i++) { // left side
				movePossibilities[l][c-i] = fig.scanDir(tiles, l, c-i, fig);
				if(!movePossibilities[l][c-i].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the available fields on the top side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current rook.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available fields on the top side. 
	 */
	private String[][] topMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;l-i>=0;i++) { // top side
				if(King.isOwnKingDefended(tiles, l-i, c, fig)) {
					movePossibilities[l-i][c] = fig.scanDir(tiles, l-i, c, fig);
					if(!movePossibilities[l-i][c].equals(Constants.HAS_MOVE)) {
						break;
					}
				} else if(!movePossibilities[l-i][c].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}else {
			for(int i=1;l-i>=0;i++) { // top side
				movePossibilities[l-i][c] = fig.scanDir(tiles, l-i, c, fig);
				if(!movePossibilities[l-i][c].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the available fields on the right side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current rook.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available fields on the right side. 
	 */
	private String[][] rightMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;c+i<Constants.BOARD_DIMENSIONS;i++) { // right side
				if(King.isOwnKingDefended(tiles, l, c+i, fig)) {
					movePossibilities[l][c+i] = fig.scanDir(tiles, l, c+i, fig);
					if(!movePossibilities[l][c+i].equals(Constants.HAS_MOVE)) {
						break;
					}
				}else if(!movePossibilities[l][c+i].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}else {
			for(int i=1;c+i<Constants.BOARD_DIMENSIONS;i++) { // right side
				movePossibilities[l][c+i] = fig.scanDir(tiles, l, c+i, fig);
				if(!movePossibilities[l][c+i].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates the available fields on the bottom side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current rook.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available fields on the bottom side. 
	 */
	private String[][] botMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest) {
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			for(int i=1;l+i<Constants.BOARD_DIMENSIONS;i++) { // bottom side
				if(King.isOwnKingDefended(tiles, l+i, c, fig)) {	
					movePossibilities[l+i][c] = fig.scanDir(tiles, l+i, c, fig);
					if(!movePossibilities[l+i][c].equals(Constants.HAS_MOVE)) {
						break;
					}
				} else if(!movePossibilities[l+i][c].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}else {
			for(int i=1;l+i<Constants.BOARD_DIMENSIONS;i++) { // bottom side
				movePossibilities[l+i][c] = fig.scanDir(tiles, l+i, c, fig);
				if(!movePossibilities[l+i][c].equals(Constants.HAS_MOVE)) {
					break;
				}
			}
		}
		return movePossibilities;
	}
}