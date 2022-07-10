package model;

import java.util.Map;

import schach.Constants;
import view.Playground;

/**
 * The position of the figures.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
//@SuppressWarnings("PMD.DataClass")
public class Position {
	private int line;
	private int column;
	private int destLine;
	private int destColumn;
	
	/**
	 * The default constructor to create a position object for each figure.
	 * @param line The line of the figure.
	 * @param column The column of the figure.
	 */
	public Position(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	/** The second constructor which is used by the verifyCheckOwnFig-method of king. 
	 * @param line The line value of the for-loop.
	 * @param column The column value of the for-loop.
	 * @param destLine The test-line value of the figure.
	 * @param destColumn The test-column value of the figure.
	 */
	public Position(int line, int column, int destLine, int destColumn) {
		this.line = line;
		this.column = column;
		this.destLine = destLine;
		this.destColumn = destColumn;
	}

	/**
	 * Move the figure from the old to the new position.
	 * @param game The game.
	 * @param board The board with the figures.
	 * @param fig The figure which should be move.
	 * @return An adapted tile array with the moved figure.
	 */
	public Tile[][] moveFromPositionToPosition(Game game, Tile[][] board, Figure fig){
		Tile[][] tiles = board;
		int currentLine = fig.getPosition().getLine();
		int currentColumn = fig.getPosition().getColumn();
		int destinationLine = fig.getPosition().getDestLine();
		int destinationColumn = fig.getPosition().getDestColumn();
		if(tiles[destinationLine][destinationColumn] != null)													// If an enemy is on the destination field 
			game.getCemetery().setBeaten(tiles[destinationLine][destinationColumn].getFigure());				// and add him to the cemetery-list
		tiles[destinationLine][destinationColumn] = new Tile(fig);
		tiles[currentLine][currentColumn] = null;
		fig.getPosition().setLine(destinationLine);
		fig.getPosition().setColumn(destinationColumn);
		return tiles;
	}
	
	 /** This method is used for the heuristic of the AI
	 * @param fig The king, found by fig.getKingPos()
	 * @param board The current board of the game
	 * @return The euclidean distance from my figure to another figure.
	 */
	public double distanceToKing(Figure fig, Board board) {
		return Math.sqrt(Math.pow(fig.getPosition().getLine() - this.line, 2) + Math.pow(fig.getPosition().getColumn() - this.column, 2));
	}
	
	/**
	 * Calculates whether the selected line and column is inside of the board.
	 * @param line The line.
	 * @param column The column.
	 * @return True if the described field is in the board.
	 */
	public boolean isInBorder(int line, int column) {
		return line >= 0 && column >= 0 && line < Constants.BOARD_DIMENSIONS && column < Constants.BOARD_DIMENSIONS;
	}
	
	/**
	 * Translate the integer to the board character.
	 * @param n The integer.
	 * @return The board character.
	 */
	public String charToValue(int n) {
		switch(n) {
			case 0: return "a";
			case 1: return "b";
			case 2: return "c";
			case 3: return "d";
			case 4: return "e";
			case 5: return "f";
			case 6: return "g";
			case 7: return "h";
			default:
			return "?";
		}
	}
	
	/**
	 * Calculates the key to the corresponding image position of the map.
	 * @param playground The board with the figure images.
	 * @param imgPos The image position.
	 * @return The keys of the image position.
	 */
	public static Position mirrorPosition(Playground playground, Position imgPos) {
		Map<Integer, Integer> litterals = playground.getLiterals();
		Map<Integer, Integer> numbers = playground.getNumbers();
		int x = imgPos.getColumn();
		int y = imgPos.getLine();
		for(int i=0;i<litterals.size();i++) {
			if(litterals.get(i) == x) {
				x = i;
			}
			if(numbers.get(i) == y) {
				y = i;
			}
		}
		x = litterals.get(7-x);
		y = numbers.get(7-y);
		return new Position(y, x);
	}
	
	// Setters and getters
	
	public int getLine() {return line;}
	public void setLine(int line) {this.line = line;}
	public int getColumn() {return column;}
	public void setColumn(int column) {this.column = column;}
	public int getDestLine() {return destLine;}
	public void setDestLine(int destLine) {this.destLine = destLine;}
	public int getDestColumn() {return destColumn;}
	public void setDestColumn(int destColumn) {this.destColumn = destColumn;}
}
