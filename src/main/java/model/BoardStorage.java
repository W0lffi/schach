package model;

import java.util.ArrayList;
import java.util.List;

import schach.Constants;

/**
* The board storage contains the past boards as strings.
* @author Christin Krause, Jan Rehfeld, Sven Wolff
*/
public class BoardStorage {
	
	private List<String> boards;
	private List<String> tombs;
	private List<String> figureImgPositions;
	private List<Boolean> checksWhite;
	private List<Boolean> checksBlack;
	
	private int boardCount;
	
	/**
	 * The constructor expect no parameters.
	 */
	public BoardStorage() {
		boards = new ArrayList<String>();
		tombs = new ArrayList<String>();
		figureImgPositions = new ArrayList<String>();
		checksWhite = new ArrayList<Boolean>();
		checksBlack = new ArrayList<Boolean>();
		boardCount = -1;
	}
	
	/**
	 * Clears the storage.
	 */
	public void clear() {
		boards.clear();
		tombs.clear();
		figureImgPositions.clear();
		checksWhite.clear();
		checksBlack.clear();
		boardCount = -1;
	}
	
	/**
	 * Removes boards, cemeteries and checks from lists which no longer needed.
	 */
	public void updateBoardCount() {
		int size;
		while(boardCount < boards.size()-1) {
			size = boards.size()-1;
			boards.remove(size);
			tombs.remove(size);
			checksWhite.remove(size);
			checksBlack.remove(size);
			if(figureImgPositions.size() > 0) {
				figureImgPositions.remove(size);
			}
		}
	}
	
	/**
	 * Method to translate a board into a 64 character String which saves storage 
	 * @param board The board which has to be translated
	 * @param addToList A boolean indicating if the result has to be included to the storage of a game.
	 * @return A 64 character String with the input board setting
	 */
	public String boardToString(Board board, boolean addToList) {
		Tile[][] tiles = board.getTiles();
		String boardString = "";
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tiles[l][c] == null) {
					boardString += '-';
				}
				else {
					boardString += tiles[l][c].getFigure().getName();
				}
			}
		}
		if(addToList){
			Figure whiteKing = Figure.getKingPos(tiles, false);
			Figure blackKing = Figure.getKingPos(tiles, true);
			boards.add(boardString);
			tombs.add(createCemeteryList(board.getGame().getCemetery()));
			checksWhite.add(whiteKing.isCheck());
			checksBlack.add(blackKing.isCheck());
			boardCount++;
		}
		return boardString;
	}
	
	/**
	 * Method to translate a tile 2D array into a 64 character String which saves storage 
	 * @param tiles The tiles 2D array which has to be translated.
	 * @return A 64 character String with the input tiles 2D array setting
	 */
	public static String tilesToString(Tile[][] tiles) {
		String boardString = "";
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tiles[l][c] == null) {
					boardString += '-';
				}
				else {
					boardString += tiles[l][c].getFigure().getName();
				}
			}
		}
		return boardString;
	}
	
	/**
	 * Method to create a board out from a 64 character String.
	 * @param boardString The string to create a board from.
	 * @return A board object.
	 */
	public Board stringToBoard(String boardString) {
		Tile[][] tiles = new Tile[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS];
		for(int i = 0; i < boardString.length(); i++) {
			if(boardString.charAt(i) != '-') {
				int startLine = -2;
				int l = i/Constants.BOARD_DIMENSIONS; 
				int c = i%Constants.BOARD_DIMENSIONS;
				tiles[l][c] = new Tile(boardString.charAt(i), l, c);
				startLine = tiles[l][c].getFigure().isColor() ? 6 : 1;
				if(tiles[l][c].getFigure().getClass().equals(Pawn.class) && tiles[l][c].getFigure().getPosition().getLine() != startLine) {
					tiles[l][c].getFigure().setFirstMove(false);
				}
			}
		}
		Board board = new Board(tiles);
		board.setMovePos(new String[Constants.BOARD_DIMENSIONS][Constants.BOARD_DIMENSIONS]);
		board.clearMovePos();
		return board;
	}
	
	/**
	 * Method to create a list of beaten figures.
	 * @param cemetery The cemetery of a current board.
	 * @return A list of all figures on the cemetery.
	 */
	private String createCemeteryList(Cemetery cemetery) {
		String list = "";
		List<Figure> figureList = cemetery.getBeaten();
		for(Figure f : figureList) {
			list += f.getName();
		}
		return list;
	}
	
	// Getter and setter
	
	public List<String> getBoards(){return this.boards;}
	public int getBoardCount() {return boardCount;}
	public void setFigureImgPositions(String figureImgPositions) {this.figureImgPositions.add(figureImgPositions);}
	public String getFigureImgPositions() {return figureImgPositions.get(boardCount);}
	
	/**
	 * Getter for the board one round before.
	 * @return The board from the round before.
	 */
	public String getBoardsPartBefore() {return boardCount > 0 ? boards.get(--boardCount) : "";}
	
	/**
	 * Getter for the board one round in future, when a move was reversed.
	 * @return The future move.
	 */
	public String getBoardsPartAfter() {return boardCount < boards.size()-1 ? boards.get(++boardCount) : "";}
	
	/**
	 * Getter for the cemetery one round before.
	 * @return The cemetery from the round before.
	 */
	public String getTombsBefore(){return boardCount >= 0 ? tombs.get(boardCount) : "";}
	
	/**
	 * Getter for the cemetery one round in future, when a move was reversed.
	 * @return The future cemetery.
	 */
	public String getTombsAfter() { return boardCount <= tombs.size()-1 ? tombs.get(boardCount) : "";}
	
	/**
	 * Getter for the check-state to the right time of the white king.
	 * @return The right check-state.
	 */
	public boolean isChecksWhite() {return checksWhite.get(boardCount);}
	
	/**
	 * Getter for the check-state to the right time of the black king.
	 * @return The right check-state.
	 */
	public boolean isChecksBlack() {return checksBlack.get(boardCount);}
	
	// For JUnit
	public void setBoards(String s) { boards.add(s); boardCount++;}
	public void setBoardCount(int i) {this.boardCount = i;}
}