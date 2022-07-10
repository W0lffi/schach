package model;

import schach.Constants;

/**
 * A tile on the board which can contain a figure or hold an empty space. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Tile {
	
	private Figure fig;
	
	/**
	 * Expects the figure of the tile.
	 * @param fig The Figure on the tile. 
	 */
	public Tile(Figure fig) {
		this.fig = fig;
	}
	
	/**
	 * A second constructor for the generation of single figures on single tiles
	 * @param name The character name of the figure; indicates also the color
	 * @param l The line of the figure
	 * @param c The column of the figure
	 */
	public Tile(char name, int l, int c) {
		this.fig = createMe(name, l, c);
	}
	
	/**
	 * This method generates an exact copy of the board 
	 * @param tiles A tiles 2D array with the board configuration
	 * @return A copy of the input
	 */
	public static Tile[][] copyTheTiles(Tile[][] tiles){
		Tile[][] newTiles= new Tile[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
		for(int c = 0; c < Constants.BOARD_DIMENSIONS; c++) {
			for(int l = 0; l < Constants.BOARD_DIMENSIONS; l++) {
				if(tiles[l][c] != null) {
					newTiles[l][c] = tiles[l][c].copyMe(l, c);
				}else {
					newTiles[l][c] = null;
				}
			}
		}
		return newTiles;
	}
	
	/**
	 * Copy the current tile with its properties
	 * @param l The line of the tile to copy
	 * @param c The column of the tile to copy
	 * @return A copy of the tile
	 */
	public Tile copyMe(int l, int c) {
		char figureName = 0;
		if(this.getFigure() != null) {
			figureName = this.getFigure().getName();
			Tile tile = new Tile(createMe(figureName, l, c));
			return tile;
		}
		return null;		
	}
	
	/**
	 * Creates a specified figure on a tile
	 * @param figureName The character name of the figure; indicates also the color
	 * @param l The line of the figure to generate the position
	 * @param c The column of the figure to generate the position
	 * @return A specific figure
	 */
	public Figure createMe(char figureName, int l, int c) {
		Figure fig;
		boolean isWhite = Character.isUpperCase(figureName);
		
		switch(Character.toUpperCase(figureName)) {
			case 'P': 
				fig = new Pawn(isWhite, l, c);
			break;
			case 'R':
				fig = new Rook(isWhite,l,c);
			break;
			case 'K':
				fig = new King(isWhite,l,c);
			break;
			case 'Q':
				fig = new Queen(isWhite,l,c);
			break;
			case 'B':
				fig = new Bishop(isWhite,l,c);
			break;
			case 'N' :
				fig = new Knight(isWhite,l,c);
			break;
				default: 
					fig = null;
			break;
		}
		return fig;
	}
	
	/**
	 * Getter for the figure of the tile
	 * @return Returns the figure of the tile
	 */
	public Figure getFigure() {
		if(this.fig != null) {
			return fig;
		}else {
			return null;
		}	
	}
}
