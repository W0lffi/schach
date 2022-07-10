package model;

import schach.Constants;

/**
 * The knight's move set.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class KnightMovement implements Movement {
	@Override
	public String[][] move(Tile[][] tiles, Figure fig, boolean checkTest) {
		String[][] movePosHighTopLeft = highTopLeftMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosFlatTopLeft = flatTopLeftMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosTopLeft = fig.concatMovement(movePosHighTopLeft, movePosFlatTopLeft);
		
		String[][] movePosHighTopRight = highTopRightMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosgflatTopRight = flatTopRightMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosTopRight = fig.concatMovement(movePosHighTopRight, movePosgflatTopRight);
		String[][] movePosTop = fig.concatMovement(movePosTopLeft, movePosTopRight);
		
		String[][] movePosHighBotLeft = highBotLeftMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosFlatBotLeft = flatBotLeftMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosBotLeft = fig.concatMovement(movePosHighBotLeft, movePosFlatBotLeft);
		
		String[][] movePosHighBotRight = highBotRightMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		String[][] movePosFlatBotRight = flatBotRightMovement(tiles, fig.defaultMovePos(tiles), fig, checkTest);
		
		String[][] movePosBotRight = fig.concatMovement(movePosHighBotRight, movePosFlatBotRight);
		String[][] movePosBot = fig.concatMovement(movePosBotLeft, movePosBotRight);
		return fig.concatMovement(movePosTop, movePosBot);
	}
	
	/**
	 * Calculates available move possibilities on high top left side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on high top left side.
	 */
	private String[][] highTopLeftMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l-2 >= 0 && c-1 >= 0 && King.isOwnKingDefended(tiles, l-2, c-1, fig)) {
				movePossibilities[l-2][c-1] = fig.scanDir(tiles, l-2, c-1, fig); // top left 1
			}
		}else {
			if(l-2 >= 0 && c-1 >= 0) {
				movePossibilities[l-2][c-1] = fig.scanDir(tiles, l-2, c-1, fig); // top left 1
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on flat top left side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on flat top left side.
	 */
	private String[][] flatTopLeftMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		
		if(checkTest) {
			if(l-1 >= 0 && c-2 >= 0 && King.isOwnKingDefended(tiles, l-1, c-2, fig)) {
				movePossibilities[l-1][c-2]	= fig.scanDir(tiles, l-1, c-2, fig); // top left 2
			}
		}else {
			if(l-1 >= 0 && c-2 >= 0) {
				movePossibilities[l-1][c-2]	= fig.scanDir(tiles, l-1, c-2, fig); // top left 2
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on high top right side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on high top right side.
	 */
	private String[][] highTopRightMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l-2 >= 0 && c+1 < Constants.BOARD_DIMENSIONS && King.isOwnKingDefended(tiles, l-2, c+1, fig)) {
				movePossibilities[l-2][c+1] = fig.scanDir(tiles, l-2, c+1, fig); // top right 1
			}
		}else {
			if(l-2 >= 0 && c+1 < Constants.BOARD_DIMENSIONS) {
				movePossibilities[l-2][c+1] = fig.scanDir(tiles, l-2, c+1, fig); // top right 1
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on flat top right side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on flat top right side.
	 */
	private String[][] flatTopRightMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l-1 >= 0 &&  c+2 < Constants.BOARD_DIMENSIONS && King.isOwnKingDefended(tiles, l-1, c+2, fig)) {
				movePossibilities[l-1][c+2]	= fig.scanDir(tiles, l-1, c+2, fig); // top right 2
			}
		}else {
			if(l-1 >= 0 &&  c+2 < Constants.BOARD_DIMENSIONS) {
				movePossibilities[l-1][c+2]	= fig.scanDir(tiles, l-1, c+2, fig); // top right 2
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on high bottom left side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on high bottom left side.
	 */
	private String[][] highBotLeftMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l+2 < Constants.BOARD_DIMENSIONS && c-1 >= 0 && King.isOwnKingDefended(tiles, l+2, c-1, fig)) {
				movePossibilities[l+2][c-1] = fig.scanDir(tiles, l+2, c-1, fig); // bottom left 1
			}
		}else {
			if(l+2 < Constants.BOARD_DIMENSIONS && c-1 >= 0) {
				movePossibilities[l+2][c-1] = fig.scanDir(tiles, l+2, c-1, fig); // bottom left 1
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on flat bottom left side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on flat bottom left side.
	 */
	private String[][] flatBotLeftMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l+1 < Constants.BOARD_DIMENSIONS && c-2 >= 0 && King.isOwnKingDefended(tiles, l+1, c-2, fig)) {
				movePossibilities[l+1][c-2]	= fig.scanDir(tiles, l+1, c-2, fig); // bottom left 2
			}
		}else{
			if(l+1 < Constants.BOARD_DIMENSIONS && c-2 >= 0) {
				movePossibilities[l+1][c-2]	= fig.scanDir(tiles, l+1, c-2, fig); // bottom left 2
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on high bottom right side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on high bottom right side.
	 */
	private String[][] highBotRightMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l+2 < Constants.BOARD_DIMENSIONS && c+1 < Constants.BOARD_DIMENSIONS && King.isOwnKingDefended(tiles, l+2, c+1, fig)) {
				movePossibilities[l+2][c+1] = fig.scanDir(tiles, l+2, c+1, fig); // bottom right 1
			}
		}else {
			if(l+2 < Constants.BOARD_DIMENSIONS && c+1 < Constants.BOARD_DIMENSIONS) {
				movePossibilities[l+2][c+1] = fig.scanDir(tiles, l+2, c+1, fig); // bottom right 1
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Calculates available move possibilities on flat bottom right side.
	 * @param tiles The board with the figures.
	 * @param movePos The move possibilities.
	 * @param fig The current knight.
	 * @param checkTest Affects whether the movement calls the check-method too.
	 * @return The available move possibilities on flat bottom left side.
	 */
	private String[][] flatBotRightMovement(Tile[][] tiles, String[][] movePos, Figure fig, boolean checkTest){
		String[][] movePossibilities = movePos;
		int l = fig.getPosition().getLine();
		int c  = fig.getPosition().getColumn();
		if(checkTest) {
			if(l+1 < Constants.BOARD_DIMENSIONS && c+2 < Constants.BOARD_DIMENSIONS && King.isOwnKingDefended(tiles, l+1, c+2, fig)) {
				movePossibilities[l+1][c+2]	= fig.scanDir(tiles, l+1, c+2, fig); // bottom right 2
			}
		} else {
			if(l+1 < Constants.BOARD_DIMENSIONS && c+2 < Constants.BOARD_DIMENSIONS) {
				movePossibilities[l+1][c+2]	= fig.scanDir(tiles, l+1, c+2, fig); // bottom right 2
			}
		}
		return movePossibilities;
	}
}