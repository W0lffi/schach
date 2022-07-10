package model;

import java.util.ArrayList;
import java.util.List;

import schach.Constants;

/**
 * This class contains the normal rules for chess
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
@SuppressWarnings("PMD.CyclomaticComplexity") // It would be harder to understand the code if we out source some methods
public class Normal implements RulesInterface{	// in an other class.

	private Game game;
	private Tile[][] tiles;
	private Figure fig;
	
	private List<Figure> hitEnPassantList;
	
	private int language;
	private int destinationLine;
	private int destinationColumn;
	private int currentLine;
	private int currentColumn;
	
	private boolean interrupt;
	
	/**
	 * Constructor of normal rules for chess
	 * @param game The current game
	 */
	public Normal(Game game) {
		hitEnPassantList = new ArrayList<Figure>();
		this.game = game;
		this.interrupt = false;
	}
	
	@Override
	public boolean undo() {
		BoardStorage boardsBefore = game.getBoard().getBoards();
		 String curBoard = boardsBefore.getBoardsPartBefore();
		 String curCem = boardsBefore.getTombsBefore();
		 this.interrupt = true;
		 if(!curBoard.equals("")) {
			 game.setBoard(boardsBefore.stringToBoard(curBoard));
			 game.getBoard().setGame(this.game);
			 game.getCemetery().setBeatenFromString(curCem);
			 game.getBoard().setBoards(boardsBefore);
			 tiles = game.getBoard().getTiles();
			 Figure whiteKing = Figure.getKingPos(tiles, false);
			 Figure blackKing = Figure.getKingPos(tiles, true);
			 whiteKing.setCheck(game.getBoard().getBoards().isChecksWhite());
			 blackKing.setCheck(game.getBoard().getBoards().isChecksBlack());
			 game.setOutput(Constants.UNDO[language]);
			 if(game.getPlayer2().getClass().equals(Computer.class) && game.isTurn() == game.getPlayer2().isColor()) {
				 game.setTurn(!game.isTurn());
			 }
			 return true;
		 }else {
			 game.setOutput(Constants.UNDO_ERROR[language]);
			 return false;
		 }
	}
	
	@Override
	public boolean redo() {
		this.interrupt = true;
		BoardStorage boardsFuture = game.getBoard().getBoards();
		String nextBoard = boardsFuture.getBoardsPartAfter();
		String nextCem = boardsFuture.getTombsAfter();
		if(!nextBoard.equals("")) {
			 game.setBoard(boardsFuture.stringToBoard(nextBoard));
			 game.getBoard().setGame(this.game);
			 game.getCemetery().setBeatenFromString(nextCem);
			 game.getBoard().setBoards(boardsFuture);
			 tiles = game.getBoard().getTiles();
			 Figure whiteKing = Figure.getKingPos(tiles, false);
			 Figure blackKing = Figure.getKingPos(tiles, true);
			 whiteKing.setCheck(game.getBoard().getBoards().isChecksWhite());
			 blackKing.setCheck(game.getBoard().getBoards().isChecksBlack());
			 game.setOutput(Constants.REDO[language]);
			 if(game.getPlayer2().getClass().equals(Computer.class)  && game.isTurn() == game.getPlayer2().isColor()
			 || game.getBoard().isLastMoveByHuman()) {
				 game.setTurn(!game.isTurn());
		 	 }
			 return true;
		}else {
			 game.setOutput(Constants.REDO_ERROR[language]);
			 return false;
		}
	}
	
	@Override
	public boolean moveTheFigure(char name) {
		tiles = game.getBoard().getTiles();
		makeMove(name);
		checkMateDraw();
		game.getBoard().getBoards().updateBoardCount();
		game.getBoard().getBoards().boardToString(game.getBoard(), true);
		game.setTurn(!game.isTurn());
		return true;
	}
	
	/**
	 * This method let's the player make his turn and move a figure to a new position.
	 * If there is an enemy figure on the target tile this figure is removed and put onto the cemetery.
	 * Special moves like castling, hitting an enemy pawn enPassant and the pawn transformation are covered.
	 * @param name The character for Transformation.
	 */
	private void makeMove(char name) {
		tiles = fig.getPosition().moveFromPositionToPosition(game, tiles, fig);
		hitEnPassantStuff();
		if(fig.isFirstMove() && fig.getClass().equals(King.class)) {							// If the figure is a King  
			castlings(tiles, fig);																// test whether he did a castling
		}
		if(fig.getClass().equals(Pawn.class) && (destinationLine == 0 || destinationLine == 7)) { 	// If a pawn gets on line as far as possible away from his start field
			fig = Pawn.transformation(tiles, destinationLine, destinationColumn, name);				// transform him into a rook, bishop, knight or queen
		}
		game.getBoard().setTiles(tiles);
		fig.setFirstMove(false);
		if(!fig.getClass().equals(King.class) && King.verifyCheckEnemy(tiles, fig.isColor())) {
			String color = !fig.isColor() ? Constants.CHECK_MSG_2_1[language] : Constants.CHECK_MSG_2_2[language];
			game.setOutput(Constants.CHECK_MSG_1[language] + color + Constants.CHECK_MSG_3[language]);
			Figure king = Figure.getKingPos(tiles, fig.isColor()); // get enemy king
			king.setCheck(true);
		}
		Figure king = Figure.getKingPos(tiles, !fig.isColor()); // get own king
		king.setCheck(King.isOwnKingDefended(tiles, king.getPosition().getLine(), king.getPosition().getColumn(), king));
	}
	
	/**
	 * Contains and call all methods which are needed to use hit en passant correctly.  
	 */
	private void hitEnPassantStuff() {
		if(fig.getClass().equals(Pawn.class)) {
			hitEnPassant(tiles, fig);
		}
		if(hitEnPassantList.size() > 0) {
			hitEnPassantList.get(0).setHitEnPassantLeft(false);
			hitEnPassantList.get(0).setHitEnPassantRight(false);
			hitEnPassantList.remove(0);
		}
		if(fig.getClass().equals(Pawn.class)) {													// Sets the possibility to the enemy to hit himself by enPassant
			setHitEnPassant(tiles,fig);
		}
	}
	
	/**
	 * This method removes the enemy pawn as a result of an enPassant situation.
	 * @param tiles The current board with the figures.
	 * @param fig The current pawn.
	 * @return Returns the updated board.
	 */
	private Tile[][] hitEnPassant(Tile[][] tiles, Figure fig){
		if(fig.isHitEnPassantLeft() || fig.isHitEnPassantRight()){
			int m = fig.isColor() ? 1 : -1;
			if(tiles[destinationLine+m][destinationColumn] != null) {
				game.getCemetery().setBeaten(tiles[destinationLine+m][destinationColumn].getFigure());
			}
			tiles[destinationLine+m][destinationColumn] = null;
		}
		return tiles;
	}
	
	/**
	 * This method checks whether a pawn triggers an enPassant situation on an enemy.
	 * @param tiles The current board with the figures.
	 * @param fig The moved pawn.
	 */
	private void setHitEnPassant(Tile[][] tiles, Figure fig) {
		if(Math.abs(destinationLine - currentLine) == 2) {		// If the pawn moved 2 fields (first Move)
			int column = currentColumn;
			int line = fig.isColor() ? 4 : 3;
			setHitEnPassantLeftOrRight(tiles, fig, column+1, line);
			setHitEnPassantLeftOrRight(tiles, fig, column-1, line);
		}
	}
	
	/**
	 * Looks on enemy side whether a pawn exist who can hit en Passant the moved pawn in next round.
	 * @param column The center column to look at  
	 * @param tiles The Tiles of the game
	 * @param line The line to look at depending of the color
	 * @param fig The figure which moved
	 */
	private void setHitEnPassantLeftOrRight(Tile[][] tiles, Figure fig, int column, int line) {
		if( column >= 0 && column < Constants.BOARD_DIMENSIONS && tiles[line][column] != null && tiles[line][column].getFigure().getClass().equals(fig.getClass()) 
		&& fig.isColor() != tiles[line][column].getFigure().isColor()) {
			if(column > currentColumn) {
				tiles[line][column].getFigure().setHitEnPassantLeft(true);
			}else { 
				tiles[line][column].getFigure().setHitEnPassantRight(true);
			}
			hitEnPassantList.add(tiles[line][column].getFigure());
		}
	}
	
	/**
	 * This method moves the rook if the king performed a castling.
	 * @param tiles The current board with the figures.
	 * @param fig The king.
	 */
	private void castlings(Tile[][] tiles, Figure fig) {
			int l = fig.isColor() ? 7 : 0;			  // the line depends on the color so it's the top or bottom line
			if(fig.getPosition().getDestLine() == l && Math.abs(destinationColumn - currentColumn) == 2) {
				int cs = destinationColumn == 2 ? 0 : 7;  // the source column where the rook stands before the castling depends on the direction of the king's move
				int cd = destinationColumn == 2 ? 3 : 5;  // the new column where the rook stands after the castling depends on the direction of the king's move
				tiles[l][cs].getFigure().getPosition().setColumn(cd); 
				tiles[l][cd] = new Tile(tiles[l][cs].getFigure());
				tiles[l][cs] = null;
				tiles[l][cd].getFigure().getPosition().setLine(l);
				tiles[l][cd].getFigure().getPosition().setColumn(cd);
			}
	}
	
	/**
	 * Calculates whether a king is check mate or its draw.
	 * @return True if one king is in check mate or if its draw. 
	 */
	private boolean checkMateDraw() {
		game.getBoard().clearMovePos();
		Figure king = Figure.getKingPos(tiles, fig.isColor()); // get enemy fig
		if(king.isCheck()) {			// checkmate
			if(searchFiguresOnField(king)) {
				return false;
			}else{
				game.setRunning(false);
				String color = fig.isColor() ? Constants.WIN_MSG_1_1[language] : Constants.WIN_MSG_1_2[language];
				if(game.getPlayer2().getClass().equals(Computer.class) && fig.isColor() == game.getPlayer2().isColor()) {
					color = Constants.JOHNNY + " ";
				}
				game.setOutput(color + Constants.WIN_MSG_2[language]);
				game.getViews().get(0).gameEnd(color + Constants.WIN_MSG_2[language]);
				return true;
			}
		}						// draw
		else if(specialCases()) {
			game.setRunning(false);
			game.setOutput(Constants.UNDECIDED[language]);
			game.getViews().get(0).gameEnd(Constants.UNDECIDED[language]);
			return true;
		}
		else {					// if the king isn't in check but he and no other figure can move, it's also draw 
			if(searchFiguresOnField(king)) {
				return false;
			}else {
				game.setRunning(false);
				game.setOutput(Constants.UNDECIDED[language]);
				game.getViews().get(0).gameEnd(Constants.UNDECIDED[language]);
				return true;
			}
		}
	}
	
	/**
	 * Iterate about the Board and search for figures with the same color as the king, if one is found test whether he can move or beat an enemy.
	 * @param king The king.
	 * @return True if the figure have a valid move or can beat an enemy.
	 */
	private boolean searchFiguresOnField(Figure king) {
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(tiles[l][c] != null && tiles[l][c].getFigure().isColor() == king.isColor()) {
					game.getBoard().setMovePos(tiles[l][c].getFigure().move(tiles, true));
					if(testFigureMoveAvailable()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Scan the move possibilities array and search for "true" and "enemy".
	 * @return True if the figure can move or beat an enemy.
	 */
	private boolean testFigureMoveAvailable() {
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(game.getBoard().getMovePos()[l][c] == Constants.HAS_MOVE || game.getBoard().getMovePos()[l][c] == Constants.HAS_ENEMY) {
					return true;
				}
			}
		}
		game.getBoard().clearMovePos();
		return false;
	}

	/**
	 * Special cases for draw.
	 * @return True if its draw.
	 */
	private boolean specialCases() {
		return game.getCemetery().getBeaten().size() == 30 
		|| game.getCemetery().getBeaten().size() == 29 && (fig.searchFigure(tiles, 'b', 'z') || fig.searchFigure(tiles, 'B', 'z') || fig.searchFigure(tiles, 'n', 'z') || fig.searchFigure(tiles, 'N', 'z'))
		|| game.getCemetery().getBeaten().size() == 28 && fig.searchFigure(tiles, 'b', 'B') ? true : false;
	}
	
	// Getters and setters
	
	@Override
	public void setGame(Game game) {this.game = game;}
	@Override
	public void setFig(Figure fig) {this.fig = fig;}
	@Override 
	public int getLanguage() {return language;}
	@Override
	public void setLanguage(int language) {this.language = language;}
	@Override
	public void setDestinationLine(int destinationLine) {this.destinationLine = destinationLine;}
	@Override
	public void setDestinationColumn(int destinationColumn) {this.destinationColumn = destinationColumn;}
	@Override
	public void setCurrentLine(int currentLine) {this.currentLine = currentLine;}
	@Override
	public void setCurrentColumn(int currentColumn) {this.currentColumn = currentColumn;}
	@Override
	public boolean isInterrupt() {return this.interrupt;}
	@Override
	public void setInterrupt(boolean interrupt) {this.interrupt = interrupt;} 
	
	// For JUnit
	public Game getGame() {return game;}
	@Override
	public boolean callCheckMate() {
		this.tiles = game.getBoard().getTiles(); 
		return checkMateDraw();
	}
	
	@Override
	public Figure getFig() {return fig;}
	public List<Figure> getHitEnPassantList() {return hitEnPassantList;}
	public void setHitEnPassantList(List<Figure> hitEnPassantList) {this.hitEnPassantList = hitEnPassantList;}
}
