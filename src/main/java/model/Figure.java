package model;

import schach.Constants;

/**
 * The Figure class is the abstract parent class for our different figures.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
@SuppressWarnings("PMD.DataClass") 	//  We have no more methods which would be useful here... its not a data-class only 
public abstract class Figure { 		// PMD has this opinion 
	
	private Position position;
	private char name;
	private boolean color;
	private boolean check;
	private boolean beaten;
	private boolean firstMove;
	private boolean hitEnPassantRight;
	private boolean hitEnPassantLeft;
	private boolean fieldColor;			// only used by bishops for a draw possibility 
	
	/**
	 * The constructor expects color and name of the figures.
	 * @param color The color of a figure: 'true' = white and 'false' = black.
	 * @param l The line of the figure.
	 * @param c The column of the figure.
	 * @param name The name of a figure as a single char: 'p' = pawn, 'r' = rook, 'n' = knight, 'b' = bishop, 'q' = queen and 'k' = King.
	 */
	public Figure(int l, int c, boolean color, char name) {
		position = new Position(l,c);
		this.color = color;
		this.name = color ? Character.toUpperCase(name) : name;
		fieldColor = false;
		firstMove  = true;
	}
	
	/**
	 * This method processes the movement possibilities of the figures.
	 * @param tiles The 2D board tiles of the game
	 * @param checkTest A value true or false to test if the king might be in check
	 * @return returns a String 2D array with true, false or enemy 
	 */
	public abstract String[][] move(Tile[][] tiles, boolean checkTest);
	
	/**
	 * Search a figure on the field. Only used for draw.
	 * @param tiles The current tiles of the game.
	 * @param figure1 A figure which will be searched.
	 * @param figure2 A figure which will be searched.
	 * @return True if the figure is on the field.
	 */
	public boolean searchFigure(Tile[][] tiles, char figure1, char figure2) {
		Figure fig1 = null;
		Figure fig2 = null;
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(figure2 != 'z') {
					if(figureExist(tiles, figure1, l, c)){
						fig1 = tiles[l][c].getFigure();
					}
					else if(figureExist(tiles, figure2, l, c)){
						fig2 = tiles[l][c].getFigure();
					}
					if(bothHaveOneBishopWithSameFieldColor(fig1, fig2)){
						return true;
					}
				}else {
					if(figureExist(tiles, figure1, l, c)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Test whether a figure is on the field which should be tested and whether its the right figure.
	 * @param tiles The board with the figures.
	 * @param figure The name of the searched figure.
	 * @param l The line which should be tested.
	 * @param c The column which should be tested.
	 * @return True if the searched figure stands on the field otherwise false.
	 */
	private boolean figureExist(Tile[][] tiles, char figure, int l, int c) {
		return tiles[l][c] != null && tiles[l][c].getFigure().getName() == figure ? true : false;
	}
	
	/**
	 * Test whether both teams have one Bishop and if their are running on the same field colors.
	 * @param bishop1 A bishop.
	 * @param bishop2 A Bishop.
	 * @return True if both teams have a bishop with the same field color otherwise false.
	 */
	private boolean bothHaveOneBishopWithSameFieldColor(Figure bishop1, Figure bishop2) {
		return bishop1 != null && bishop2 != null && bishop1.isFieldColor() == bishop2.isFieldColor() ? true : false;
	}
	
	/**
	 * Finds the king with the opposite color than input-color on the board. 
	 * @param tiles The board with the figures.
	 * @param color The color of the current figure.
	 * @return The enemy king.
	 */
	public static Figure getKingPos(Tile[][] tiles, boolean color) {
		String boardString = BoardStorage.tilesToString(tiles);
		int index = -1;
		if(color) {
			index = boardString.indexOf("k");
		}else {
			index = boardString.indexOf("K");
		}

		if(index > -1) {
			return tiles[index/Constants.BOARD_DIMENSIONS][index%Constants.BOARD_DIMENSIONS].getFigure();
		}
		else {
			return null;
		}
	}
	
	/**
	 * This method checks whether a figure can move to a field next to it.
	 * @param tiles The field with the figures. 
	 * @param l The line which is tested.
	 * @param c The column which is tested.
	 * @param fig The figure which is going to be moved.
	 * @return Returns the String "false" if the figure can't move to the target destination, "enemy" if there is an enemy or "true" if the character can move there.
	 */
	protected String scanDir(Tile[][] tiles, int l, int c, Figure fig) {
		String s = Constants.HAS_NO_MOVE;
		if(tiles[l][c] == null) {
			s = Constants.HAS_MOVE;
		}
		else if(tiles[l][c].getFigure().isColor() != fig.isColor()) {
			s = Constants.HAS_ENEMY;
		}
		else {
			s = Character.toString(tiles[l][c].getFigure().getName());
		}
		return s;
	}
	
	/**
	 * This method concatenates two movement possibility arrays of to one. 
	 * @param movement1 The movement possibilities of the rook.
	 * @param movement2 The movement possibilities of the bishop.
	 * @return The movement possibilities of the queen.
	 */
	protected String[][] concatMovement(String[][] movement1, String[][] movement2){
		String[][] movePossibilities = movement1;
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(movement2[l][c].equals(Constants.HAS_MOVE) || movement2[l][c].equals(Constants.HAS_ENEMY)) {
					movePossibilities[l][c] = movement2[l][c];
				}
			}
		}
		return movePossibilities;
	}
	
	/**
	 * Write the names from the characters and false in the move possibilities-array, expect the tiles-array.
	 * @param tiles The tiles 2D array of the game.
	 * @return The move possibilities of the current chosen figure.
	 */
	protected String[][] defaultMovePos(Tile[][] tiles){
		String[][] movePos = new String[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tiles[l][c] == null) {
					movePos[l][c] = Constants.HAS_NO_MOVE;
				}
				else {
					movePos[l][c] = Character.toString(tiles[l][c].getFigure().getName());
				}
			}
		}
		return movePos;
	}
	
	// Getters and setters
	public boolean isFirstMove() {return firstMove;}
	public void setFirstMove(boolean firstMove) {this.firstMove = firstMove;}
	public Position getPosition() {return position;}
	public void setBeaten(boolean beaten) {this.beaten = beaten;}
	public boolean isBeaten() {return beaten;}
	public boolean isColor() {return color;}
	public char getName() {return name;}
	public void setCheck(boolean check) {this.check = check;}
	public boolean isCheck() {return check;}
	public void setHitEnPassantRight(boolean hitEnPassantRight) {this.hitEnPassantRight = hitEnPassantRight;}
	public boolean isHitEnPassantRight() {return hitEnPassantRight;}
	public void setHitEnPassantLeft(boolean hitEnPassantLeft) {this.hitEnPassantLeft = hitEnPassantLeft;}
	public boolean isHitEnPassantLeft() {return hitEnPassantLeft;}
	public void setFieldColor(boolean fieldColor) {this.fieldColor = fieldColor;}
	public boolean isFieldColor() {return fieldColor;}
}