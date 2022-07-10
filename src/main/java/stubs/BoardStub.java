package stubs;

import model.Bishop;
import model.Board;
import model.BoardStorage;
import model.Game;
import model.King;
import model.Knight;
import model.Pawn;
import model.Queen;
import model.Rook;
import model.Tile;
import schach.Constants;

/**
 * A stub of the board for JUnit tests.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class BoardStub extends Board {
	
	/**
	 * Constructor for BoardStub which prepares the testing board
	 */
	public BoardStub() {
		super(new Game());
		super.setTiles(new Tile[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS]);
		super.setMovePos(new String[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS]);
		super.clearMovePos();
	}
	
	/**
	 * Second constructor for BoardStub which prepares the testing board.
	 * @param game A dummy game object to use for test. 
	 */
	public BoardStub(Game game) {
		super(game);
		super.setTiles(new Tile[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS]);
		super.setMovePos(new String[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS]);
		super.clearMovePos();
		super.init();
		super.setBoards(new BoardStorage());
		super.getBoards().boardToString(this, true);
		
	}
	
	/**
	 * Creates pawns at specified positions for the generation of testing set-ups
	 * @param l The line for the figure
	 * @param c The column for the figure
	 * @param color The color for the figure
	 */
	public void createPawns(int l, int c, boolean color) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = new Tile(new Pawn(color, l, c));
	}

	/**
	 * Creates rooks at specified positions for the generation of testing set-ups
	 * @param l The line for the figure
	 * @param c The column for the figure
	 * @param color The color for the figure
	 */
	public void createRooks(int l, int c, boolean color) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = new Tile(new Rook(color, l, c));
		
	}
	
	/**
	 * Creates knights at specified positions for the generation of testing set-ups
	 * @param l The line for the figure
	 * @param c The column for the figure
	 * @param color The color for the figure
	 */
	public void createKnights(int l, int c, boolean color) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = new Tile(new Knight(color, l, c));
		
	}
	
	/**
	 * Creates bishops at specified positions for the generation of testing set-ups
	 * @param l The line for the figure
	 * @param c The column for the figure
	 * @param color The color for the figure
	 */
	public void createBishops(int l, int c, boolean color) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = new Tile(new Bishop(color, l, c));
		
	}
	
	/**
	 * Creates queens at specified positions for the generation of testing set-ups
	 * @param l The line for the figure
	 * @param c The column for the figure
	 * @param color The color for the figure
	 */
	public void createQueens(int l, int c, boolean color) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = new Tile(new Queen(color, l, c));
		
	}
	
	/**
	 * Creates kings at specified positions for the generation of testing set-ups
	 * @param l The line for the figure
	 * @param c The column for the figure
	 * @param color The color for the figure
	 */
	public void createKings(int l, int c, boolean color) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = new Tile(new King(color, l, c));
	}
	
	/**
	 * Deletes a figure on the board
	 * @param l The line of the figure to be removed
	 * @param c The column of the figure to be removed
	 */
	public void removeMyFigure(int l, int c) {
		Tile[][] tiles = super.getTiles();
		tiles[l][c] = null;
		super.setTiles(tiles);
	}
	
	// For JUnit Testing
	//public Game getGame() {return super.game;}
	//public void setGame(Game game) {super.game = game;}
}
