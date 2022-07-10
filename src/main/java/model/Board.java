package model;

import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import schach.Constants;
import view.Playground;

/**
 * The board contains the 64 tiles of the board.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Board {
	private Game game;
	private Tile[][] tiles;
	private String[][] movePos;
	private Figure lastMovedFigure;
	private BoardStorage boards;
	
	/**
	 * The constructor doesn't expect any parameters is only for JUnit.
	 */
	public Board() {
		init();
		boards = new BoardStorage();
	}
	
	/**
	 * This constructor creates a 2D-tiles-array with the dimensions of a chess board and initializes it.
	 * @param game The current game. 
	 */
	public Board(Game game) {
		this.game = game;
		init();
		boards = new BoardStorage();
		boards.boardToString(this, true);
	}
	
	/**
	 * Third constructor for stringToBoard method.
	 * @param tiles The created tiles.
	 */
	public Board(Tile[][] tiles) {
		this.tiles = tiles.clone();
	}
	
	/**
	 * Initialize the board with the default positions.
	 */
	public void init() {
		tiles = new Tile[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
		initTiles();
		movePos = new String[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
		clearMovePos();
		lastMovedFigure = null;
	}
	
	/**
	 * This method returns a single tile with figure
	 * @param l The line of the tile of interest.
	 * @param c The column of the tile of interest.
	 * @return returns the tile if it exists. 
	 */
	public Tile getSingleTile(int l, int c) {
		if (tiles[l][c] != null) {
			return tiles[l][c];
		}
		return null;
	}
	
	/**
	 * This method creates a single figure on a tile; Used for AI
	 * @param position The position for the figure
	 * @param type The character name of the figure; indicates also the color
	 * @return The tile with a figure which had to be created. 
	 */
	public Tile createSingleFigure(Position position, char type) {
		Tile tile = new Tile(type, position.getLine(), position.getColumn());
		return tile;
	}
	
	/**
	 * This method sets the initial move possibilities to the default values.
	 */
	public void clearMovePos(){
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
	}
	
	/**
	 * Removes the gray entries in the list view of the playground.
	 * @param output The list view.
	 */
	public void delNonExistingMovesFromList(ListView<Label> output) {
		int entry = output.getItems().size() - 1; 
		Label lastOutput = output.getItems().get(entry);
		while(lastOutput.getTextFill().equals(Constants.REPEATABLE)) {
			output.getItems().remove(entry);
			if(output.getItems().size() > 0) {
				lastOutput = output.getItems().get(--entry);
			}else {
				break;
			}
		}
	}
	
	/**
	 * Calculates whether the last redone move was by human if the second player is an AI.
	 * @return True if the last redone move was by human.
	 */
	public boolean isLastMoveByHuman() {
		if(game.getPlayer2().getClass().equals(Computer.class)) {
			int boardsSize = this.boards.getBoards().size()-1;
			int boardCount = this.boards.getBoardCount();
			int moveColor = game.getPlayer1().isColor() ? 1 : 0; 
			if(boardCount == boardsSize && boardCount % 2 == moveColor) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method removes images from the playground board.
	 * @param board A playground of the game for GUI methods
	 */
	public void removePosFromBoard(Playground board) {
		List<ImageView> lastImages = game.getController().getLastImages();
		for(ImageView image : lastImages) {
			board.remContainerPart(image);
		}
	}
	
	/**
	 * This method initializes the creation of the figures on the board.
	 */
	private void initTiles() {
		createPawns();
		createRooks();
		createKnights();
		createBishops();
		createQueens();
		createKings();
	}

	/**
	 * This method creates the sixteen pawns in the right color on the right tiles.
	 */
	private void createPawns() {
		for(int i=0;i<Constants.BOARD_DIMENSIONS;i++) {
			tiles[6][i] = new Tile(new Pawn(true, 6, i));
		}
		for(int i=0;i<Constants.BOARD_DIMENSIONS;i++) {
			tiles[1][i] = new Tile(new Pawn(false, 1, i));
		}
	}
	
	/**
	 * This method creates the four rooks in the right color on the right tiles. 
	 */
	private void createRooks() {
		tiles[7][0] = new Tile(new Rook(true, 7, 0));
		tiles[7][7] = new Tile(new Rook(true, 7, 7));
		tiles[0][0] = new Tile(new Rook(false, 0, 0));
		tiles[0][7] = new Tile(new Rook(false, 0, 7));
	}
	
	/**
	 * This method creates the four knights in the right color on the right tiles.
	 */
	private void createKnights() {
		tiles[7][1] = new Tile(new Knight(true, 7, 1));
		tiles[7][6] = new Tile(new Knight(true, 7, 6));
		tiles[0][1] = new Tile(new Knight(false, 0, 1));
		tiles[0][6] = new Tile(new Knight(false, 0, 6));
	}
	
	/**
	 * This method creates the four bishops in the right color on the right tiles. 
	 */
	private void createBishops() {
		tiles[7][2] =  new Tile(new Bishop(true, 7, 2));
		tiles[7][2].getFigure().setFieldColor(false);
		tiles[7][5] =  new Tile(new Bishop(true, 7, 5));
		tiles[7][5].getFigure().setFieldColor(true);
		tiles[0][2] =  new Tile(new Bishop(false, 0, 2));
		tiles[0][2].getFigure().setFieldColor(true);
		tiles[0][5] =  new Tile(new Bishop(false, 0, 5));
		tiles[0][5].getFigure().setFieldColor(false);
	}
	
	/**
	 * This method creates the two queens in the right color in the right tiles.
	 */
	private void createQueens() {
		tiles[7][3] = new Tile(new Queen(true, 7, 3));
		tiles[0][3] = new Tile(new Queen(false, 0, 3));
	}	
	
	/**
	 * This method creates the kings in the right color in the right tiles.
	 */
	private void createKings() {
		tiles[7][4] = new Tile(new King(true, 7, 4));
		tiles[0][4] = new Tile(new King(false, 0, 4));
	}
	
	// Setters and getters
	public void setGame(Game game){this.game = game;}
	public Game getGame(){return this.game;}
	public void setTiles(Tile[][] tiles) {this.tiles = tiles.clone();}
	public Tile[][] getTiles() {return tiles.clone();}
	public void setMovePos(String[][] movePossibilities) {this.movePos = movePossibilities.clone();}
	public String[][] getMovePos() {return movePos.clone();}
	public void setLastMovedFigure(Figure lastMoveFigure) {this.lastMovedFigure = lastMoveFigure;}
	public Figure getLastMovedFigure() {return lastMovedFigure;}
	public BoardStorage getBoards() {return boards;}
	public void setBoards(BoardStorage boards) {this.boards = boards;}
}
