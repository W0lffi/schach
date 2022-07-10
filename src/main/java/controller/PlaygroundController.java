package controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Computer;
import model.Figure;
import model.Game;
import model.Position;
import model.Tile;
import schach.Constants;
import view.Playground;
import view.PlaygroundInitialize;
import view.ImageHandler;
import view.MainMenuView;
	
/**
 * The controller for the playground
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
@SuppressWarnings("PMD.CyclomaticComplexity") // It would be harder to understand the code if we out source some methods
public class PlaygroundController implements ControllerInterface{	// in an other class.

	private MainMenuView mainMenu;
	private Game game;
	private Playground playground;
	private Position boardPos;
									
	private List<ImageView> lastImages; 
	
	private int[] litterals; 		// 320,400,480,560,640,720,800,880 X
	private int[] numbers;			// 042,122,202,282,362,442,522,602 Y
	
	private boolean[] settings; 	// drawing, transforming, newPosition, new Destination, firstFigure, firstFigureConf, surrender
	
	/**
	 * The constructor expects the playground, the main menu view and the Scene.
	 * @param playground The Playground of the GUI.
	 * @param mainMenu The Main menu view of the GUI.
	 * @param scene The current scene of the GUI.
	 */
	public PlaygroundController(Playground playground, MainMenuView mainMenu, Scene scene) {
		this.mainMenu = mainMenu;
		this.playground = playground;
		game = playground.getGame();
		game.setController(this);
		game.setRunning(true);
		settings = new boolean[7];
		for(int i=0;i<settings.length;i++) {
			settings[i] = false; 
		}
		initPlaygroundButtons(scene, mainMenu);
		initPlaygroundMenuItems();
		
		// clickable move history
		playground.getOutput().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				List<Label> output = playground.getOutput().getItems();
				int selected = playground.getOutput().getSelectionModel().getSelectedIndex();
				int deny = -1;
				if(game.getPlayer2().getClass().equals(Computer.class)) {
					deny = game.getPlayer1().isColor() ? 0 : 1;
				}
				if(deny == -1 || deny == selected % 2 && playground.getOutput().getItems().size() > 0) {
					if(output.get(selected).getTextFill().equals(Constants.REPEATABLE)) {
						repeatMoves(output, selected, false);
					}else {
						reverseMoves(output, selected, false);
					}
				}
			}
		});
		// clickable figures
		scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				clickEvent(e);
			}
		});
	}
	
	/**
	 * Here the playground controller is initialized.
	 */
	public void init() {
		boardPos = new Position(-1, -1, -1, -1);
		settings[0] = true; // drawing
		settings[4] = true;	// first figure
		lastImages = new ArrayList<ImageView>();
		litterals = new int[Constants.BOARD_DIMENSIONS+1];
		litterals[8] = 960;
		numbers = new int[Constants.BOARD_DIMENSIONS+1];
		numbers[8] = 682;
		for(int i=0;i<playground.getLiterals().size();i++) {
			litterals[i] = playground.getLiterals().get(i);
			numbers[i] = playground.getNumbers().get(i);
		}
	}
	
	@Override
	public boolean userAction() {
		if(game.getPlayer2().getClass().equals(Computer.class) && game.getPlayer2().isColor() == game.isTurn() && !settings[1] && !game.getPlayer2().getIntelligence().isInterrupt()) {
			computerAction();
			return true;
		}
		else if(game.getPlayer2().getClass().equals(Computer.class)){
				game.getPlayer2().getIntelligence().setInterrupt(false);
			}
			humanAction();
			return true;
	}
	
	/**
	 * This method makes it possible to reverse moves with the help of the list view on the right side of the board.
	 * @param output All the moves that have been done since the beginning of the game. Reversed moves are shown grayed.
	 * @param _selected The selected move to which the player want's to jump back.
	 * @param onlyOne Checks if there has been only one move since the game started.
	 */
	private void reverseMoves(List<Label> output, int _selected, boolean onlyOne) {
		playground.getBoardAndStates()[4].setVisible(false);
		int selected = _selected;
		int upperIndexReverse;
		int moveCount = 1;
		for(int i=output.size()-1;i>0;i--) {
			if(output.get(i).getTextFill().equals(Constants.REPEATABLE)) {
				moveCount++;
			}else {
				break;
			}
		}
		upperIndexReverse = output.size() - moveCount;
		if(onlyOne) {
			selected = upperIndexReverse;
		}
		if(game.getPlayer2().getClass().equals(Computer.class) && onlyOne && !game.getPlayer2().getIntelligence().isThinking() && game.isRunning()) {
			selected --;
		}
		do {
			if(game.getGamemode().undo()) {
				playground.setLastOutputGrey(moveCount);
				boardPos = new Position(-1, -1, -1, -1);
				game.getBoard().removePosFromBoard(game.getController().getPlayground());
				playground.updateBoard();
				if(game.getPlayer2().getClass().equals(Computer.class)) {
					game.getPlayer2().getIntelligence().setThinking(false);
				}
			}
			upperIndexReverse--;
			moveCount++;
		}while(upperIndexReverse >= selected);
	}
	
	/**
	 * This method makes it possible to repeat moves with the help of the list view on the right side of the board.
	 * @param output All the moves that have been done since the beginning of the game.
	 * @param _selected The selected move to which the player want's to jump forward.
	 * @param onlyOne Checks if there has been only one move since the game started.
	 */
	private void repeatMoves(List<Label> output, int _selected, boolean onlyOne) {
		int selected = _selected;
		int moveCount;
		int lowerIndexRepeat = 0;
		for(Label l : output) {
			if(l.getTextFill().equals(Constants.REVERSIBLE)) {
				lowerIndexRepeat++;
			}
			else {
				break;
			}
		}
		moveCount = output.size() - lowerIndexRepeat;
		if(onlyOne) {
			selected = lowerIndexRepeat;
		}
		if(game.getPlayer2().getClass().equals(Computer.class) && game.isRunning()) {
			selected++;
		}
		do {
			if(game.getGamemode().redo()) {
				playground.setLastOutputBlack(moveCount);
				game.getBoard().removePosFromBoard(game.getController().getPlayground());
				playground.updateBoard();
			}
				lowerIndexRepeat++;
				moveCount--;
		}while(lowerIndexRepeat <= selected);
		if(game.getPlayer2().getClass().equals(Computer.class) && game.isTurn() == game.getPlayer2().isColor()) {
			playground.getBoardAndStates()[4].setVisible(true);
		}
	}
	
	/**
	 * Calls if the a mouse click was detected.
	 * @param e The mouse event.
	 */
	private void clickEvent(MouseEvent e) {
		int line = mirrorCoordinate(positionOnScreen((int)e.getSceneY(), numbers));
		int column = mirrorCoordinate(positionOnScreen((int)e.getSceneX(), litterals));
		Tile[][] tiles = game.getBoard().getTiles();
		if(boardPos.isInBorder(line, column) && !settings[1]) {	// firstFigure
			if(isFigureHere(tiles, line, column)) {
				boardPos.setLine(line);
				boardPos.setColumn(column);
				settings[2] = true; 		// newPosition
				if(settings[5]) {			// firstFigureConf
					settings[4] = false;	// firstFigure
				}
			}
			else if(boardPos.getLine() != -1){
				boardPos.setDestLine(line);
				boardPos.setDestColumn(column);
				settings[3] = true;	// new Destination
			}
		}
	}
	
	/**
	 * Calculates the corresponding playground coordinates relative to the screen coordinates.
	 * @param n The line or column with their pixel coordinations on the screen (0-1280 and 0-720).
	 * @param equals The array with the corresponding board coordinates.
	 * @return The line or column numbers (0-7).
	 */
	private int positionOnScreen(int n, int[] equals) {
		if(n >= equals[0] && n <= equals[8]) {
			for(int i=1;i<equals.length;i++) {
				if(n < equals[i]) {
					return --i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * A Method to check whether a figure can move or not.
	 * @param fig The figure to check.
	 * @return True if it has a move or an enemy in reach, else false.
	 */
	private boolean figureHasMove(Figure fig) {
		String[][] movePos = fig.move(game.getBoard().getTiles(), true);
		return movePos[boardPos.getDestLine()][boardPos.getDestColumn()] == Constants.HAS_MOVE 
				|| movePos[boardPos.getDestLine()][boardPos.getDestColumn()] == Constants.HAS_ENEMY;
	}
	
	/**
	 * This method checks the player's interaction with the game to move a figure on the board.
	 * @return True if the action of the human player is finished.
	 */
	private boolean humanAction() {	
		if(settings[2]) {	// new position
			settings[2] = false;	// new position
			if(settings[0] && (!game.getPlayer2().getClass().equals(Computer.class) || game.getPlayer2().getClass().equals(Computer.class) && game.isTurn() != game.getPlayer2().isColor())) {	// drawing
				drawStates(game.getBoard().getTiles());
			}
		}
		else if(settings[3]) {	// newDestination
			settings[3] = false; // newDestination
			Figure fig = game.getBoard().getTiles()[boardPos.getLine()][boardPos.getColumn()].getFigure();
			if(figureHasMove(fig)) {
				fig.getPosition().setDestLine(boardPos.getDestLine());
				fig.getPosition().setDestColumn(boardPos.getDestColumn());
				game.getGamemode().setFig(fig);
				game.getGamemode().setCurrentLine(boardPos.getLine());
				game.getGamemode().setCurrentColumn(boardPos.getColumn());
				game.getGamemode().setDestinationLine(boardPos.getDestLine());
				game.getGamemode().setDestinationColumn(boardPos.getDestColumn());
				if(isEnemyHere(fig)) {
					int m = hitEnPassantByColor(fig);
					game.getBoard().getTiles()[boardPos.getDestLine()+m][boardPos.getDestColumn()].getFigure().getPosition().setDestLine(-1);
					game.getBoard().getTiles()[boardPos.getDestLine()+m][boardPos.getDestColumn()].getFigure().getPosition().setDestColumn(-1);
					game.getCemetery().setNewDead(true);
				}
				game.getGamemode().moveTheFigure(' ');
				game.getBoard().setLastMovedFigure(fig);
				settings[4] = false;	// firstFigure
				game.setOutput(boardPos.charToValue(boardPos.getColumn()) + (Constants.BOARD_DIMENSIONS - boardPos.getLine()) + "-" + boardPos.charToValue(boardPos.getDestColumn()) + (Constants.BOARD_DIMENSIONS - boardPos.getDestLine()));
				boolean states[] = playground.getStates();
				states[0] = true;		// changed
				playground.setStates(states);
				ImageView[] boardAndStates = playground.getBoardAndStates();
				boardAndStates[2].setVisible(false);
				playground.setBoardAndStates(boardAndStates);
			}
		}
		return true;
	}
	
	/**
	 * This method gets the computer action for processing the input string of the AI to use in humanAction().
	 */
	private void computerAction() {
		String input = game.getPlayer2().getIntelligence().makeMove(game);
		if(game.getGamemode().isInterrupt()){
			game.getGamemode().setInterrupt(false);
			return;
		}
		if(input.equals(null)) {
			this.game.setRunning(false); 
			this.game.setOutput(Constants.UNDECIDED[this.game.getGamemode().getLanguage()]);
			this.game.getViews().get(0).gameEnd(Constants.UNDECIDED[this.game.getGamemode().getLanguage()]);
		}
		boardPos.setLine(Constants.BOARD_DIMENSIONS - Character.getNumericValue(input.charAt(1))); 
		boardPos.setColumn(getCharacterValue(input.charAt(0)));
		boardPos.setDestLine(Constants.BOARD_DIMENSIONS - Character.getNumericValue(input.charAt(4))); 
		boardPos.setDestColumn(getCharacterValue(input.charAt(3)));
		settings[3] = true; // newDestination
		humanAction();
	}
	
	/**
	 * Calculates the board number for each character letter.
	 * @param c The Character letter from a player's input.
	 * @return Return the right number of the letter.
	 */
	private int getCharacterValue(char c) {
		int n;
			switch(c) {
			case 'a': n = 0;
			break;
			case 'b': n = 1;
			break;
			case 'c': n = 2;
			break;
			case 'd': n = 3;
			break;
			case 'e': n = 4;
			break;
			case 'f': n = 5;
			break;
			case 'g': n = 6;
			break;
			case 'h': n = 7;
			break;
				default: n = -1;
			break;
			}
		return n;
	}	
	
	/**
	 * A method to differentiate the possibility of an enpassant move depending on the color.
	 * @param fig The figure/pawn in question.
	 * @return true If an enpassant move is possible.
	 */
	private int hitEnPassantByColor(Figure fig) {
		return fig.isHitEnPassantLeft() || fig.isHitEnPassantRight() ? fig.isColor() ? 1 : -1  : 0;
	}
	
	/**
	 * Calculates which fields should be marked is movable or beatable depending on color.
	 * @param tiles The board with the figures.
	 */
	private void drawStates(Tile[][] tiles) {
		Figure fig = tiles[boardPos.getLine()][boardPos.getColumn()].getFigure();
		String[][] movePos = fig.move(tiles, true);
		List<ImageView> lastImages = game.getController().getLastImages();
		int line = fig.getPosition().getLine();
		int column = fig.getPosition().getColumn();
		if(lastImages.size() > 0) {
			game.getBoard().removePosFromBoard(game.getController().getPlayground());
		}
		movePos = tiles[line][column].getFigure().move(tiles, true);
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {
				if(movePos[l][c] == "true") {
					addImageToContainer(Constants.DIRECTORY[3], "possibleField", mirrorCoordinate(l), mirrorCoordinate(c));
				}
				else if(movePos[l][c] == "enemy" && fig.isColor()) {
					addImageToContainer(Constants.DIRECTORY[3], "blackIsEnemy", mirrorCoordinate(l), mirrorCoordinate(c));
				}
				else if(movePos[l][c] == "enemy") {
					addImageToContainer(Constants.DIRECTORY[3], "whiteIsEnemy", mirrorCoordinate(l), mirrorCoordinate(c));
				}
			}
		}
	}
	
	/**
	 * Creates the new images for move possibilities, check and beatable enemies.
	 * @param dir The directory where the image is saved.
	 * @param name The name of the image file.
	 * @param y The y-coordinate where the image should be drawn.
	 * @param x The x-coordinate where the image should be drawn.
	 */
	private void addImageToContainer(String dir, String name , int y, int x) {
		Playground playground = game.getController().getPlayground();
		List<ImageView> lastImages = game.getController().getLastImages();
		ImageView tmpImage = new ImageView();
		lastImages.add(tmpImage);
		tmpImage.setImage(ImageHandler.getInstance().getImage(dir, name));
		tmpImage.setFitWidth(Constants.FIG_WIDTH_HEIGHT);
		tmpImage.setFitHeight(Constants.FIG_WIDTH_HEIGHT);
		tmpImage.setLayoutY(playground.getNumbers().get(y));
		tmpImage.setLayoutX(playground.getLiterals().get(x));
		if(name == "possibleField") {
			tmpImage.setOpacity(Constants.OPACITY_POSSIBLE_FIELD_CHECK);
		}else {
			tmpImage.setOpacity(Constants.OPACITY_HIT_ENEMY);
		}
		playground.addContainerPart(tmpImage);
	}
	
	
	private int mirrorCoordinate(int n) {
		boolean states[] = playground.getStates();
		return states[1] && !game.isTurn() ? 7 - n : n;
	}
	
	private boolean isEnemyHere(Figure fig) {
		return	game.getBoard().getTiles()[boardPos.getDestLine()][boardPos.getDestColumn()] != null
				&& game.getBoard().getTiles()[boardPos.getDestLine()][boardPos.getDestColumn()].getFigure() != fig 
				|| (fig.isHitEnPassantLeft() || fig.isHitEnPassantRight()) && boardPos.getColumn() != boardPos.getDestColumn();
	}
	
	private boolean isFigureHere(Tile[][] tiles, int line, int column) {
		return settings[4] && tiles[line][column] != null && tiles[line][column].getFigure().isColor() == game.isTurn() && (boardPos.getLine() != line || boardPos.getColumn() != column);
	}		   // firstFigure
	
	private void initPlaygroundButtons(Scene scene, MainMenuView mainMenu) {
		playground.getButtons()[0].setOnAction(event -> { // btnMainMenu
			settings[6] = false;
			game.getBoard().removePosFromBoard(game.getController().getPlayground());
			scene.setRoot(mainMenu);
			playground.getBoardAndStates()[3].setVisible(false);
			boardPos = new Position(-1, -1, -1, -1);
			game.stopGame();
		});
		playground.getButtons()[1].setOnAction(event -> { // undo move
			reverseMoves(playground.getOutput().getItems(), -1, true);
		});
		
		playground.getButtons()[2].setOnAction(event -> { // redo move
			repeatMoves(playground.getOutput().getItems(), -1 , true);
		});
		playground.getButtons()[3].setOnAction(event -> { // surrender
			if(game.isRunning()) {
				Alert surrender = PlaygroundInitialize.getInstance().surrenderDrawAlert(game.getLanguage(), true);
				String color;
				boolean end;
				if(!game.getPlayer2().getClass().equals(Computer.class)) {
					color = game.isTurn() ? Constants.WIN_MSG_1_1[game.getLanguage()] : Constants.WIN_MSG_1_2[game.getLanguage()]; 
					surrender.setContentText("\t" + color.replaceAll(" ", "") + surrender.getContentText());
					Optional<ButtonType> result = surrender.showAndWait();
					end = result.get().getText().equals(Constants.YES[game.getLanguage()]) ? true : false;
					color = game.isTurn() ? Constants.WIN_MSG_1_2[game.getLanguage()] : Constants.WIN_MSG_1_1[game.getLanguage()];
				}else {
					color = game.getPlayer1().isColor() ? Constants.WIN_MSG_1_1[game.getLanguage()] : Constants.WIN_MSG_1_2[game.getLanguage()];
					surrender.setContentText("\t" + color.replaceAll(" ", "") + surrender.getContentText());
					Optional<ButtonType> result = surrender.showAndWait();
					end = result.get().getText().equals(Constants.YES[game.getLanguage()]) ? true : false;
					playground.getBoardAndStates()[4].setVisible(false);
					color = Constants.JOHNNY + " ";
				}
				if(end) {
					settings[6] = true; // surrender
					game.stopGame();
					playground.gameEnd(color + Constants.WIN_MSG_2[game.getLanguage()]);
				}
			}
		});
		playground.getButtons()[4].setOnAction(event -> { // draw
			if(game.isRunning()) {
				boolean end;
				String msg;
				String color;
				if(!game.getPlayer2().getClass().equals(Computer.class)) {
					Alert draw = PlaygroundInitialize.getInstance().surrenderDrawAlert(game.getLanguage(), false);
					color = game.isTurn() ? Constants.WIN_MSG_1_1[game.getLanguage()] : Constants.WIN_MSG_1_2[game.getLanguage()]; 
					draw.setContentText("\t\t   " + color + draw.getContentText());
					Optional<ButtonType> result = draw.showAndWait();
					end = result.get().getText().equals(Constants.YES[game.getLanguage()]) ? true : false;
					color = game.isTurn() ? Constants.WIN_MSG_1_2[game.getLanguage()] : Constants.WIN_MSG_1_1[game.getLanguage()]; 
					msg = end ? Constants.UNDECIDED[game.getLanguage()] : color + "\n" + Constants.NO_DRAW[game.getLanguage()];
				}else {
					end = game.getPlayer2().getIntelligence().drawRequest(game);
					msg = end ? Constants.UNDECIDED[game.getLanguage()] : Constants.JOHNNY  + "\n" + Constants.NO_DRAW[game.getLanguage()];
				}
				if(end) {
					playground.gameEnd(msg);
					game.stopGame();
				}else {
					playground.gameEnd(msg);
				}
			}
			
		});
	}
	
	private void initPlaygroundMenuItems() {
		playground.getOptions().getItems().get(0).setOnAction(event -> { // btndrawMovePos
			settings[0] = !settings[0];
			if(!settings[0] && lastImages.size() > 0) {	//drawing
				game.getBoard().removePosFromBoard(game.getController().getPlayground());	
			}
			else if(boardPos.getLine() >= 0) {
				drawStates(game.getBoard().getTiles());
			}
		});
		
		playground.getOptions().getItems().get(1).setOnAction(event -> { // btnFirstTouchMustMove
			settings[5] = !settings[5];			// firstFigureConf
			if(!settings[5]) {
				settings[4] = true;				// firstFigure
			}
		});
		
		playground.getOptions().getItems().get(2).setOnAction(event -> { // btnMirrorField
			boolean[] states = playground.getStates();
			states[1] = !states[1];
			playground.setStates(states);
		});
		playground.getOptions().getItems().get(3).setOnAction(event -> { // drawCheck
			boolean[] states = playground.getStates();
			states[2] = !states[2];
			playground.setStates(states);
			if(!states[2]) {
				ImageView[] boardAndStates = playground.getBoardAndStates();
				boardAndStates[2].setVisible(false);
				playground.setBoardAndStates(boardAndStates);
			}else {
				Figure whiteKing = Figure.getKingPos(game.getBoard().getTiles(), false);
				Figure blackKing = Figure.getKingPos(game.getBoard().getTiles(), true);
				if(blackKing.isCheck() || whiteKing.isCheck()) {
					ImageView[] boardAndStates = playground.getBoardAndStates();
					boardAndStates[2].setVisible(true);
					playground.setBoardAndStates(boardAndStates);
				}
			}
		});
	}
	
	// Getters and setters
	@Override
	public Position getBoardPos() {return boardPos;}
	@Override
	public boolean[] getSettings() {return settings.clone();}
	@Override
	public void setSettings(boolean[] settings) {this.settings = settings.clone();}
	@Override
	public List<ImageView> getLastImages() {return lastImages;}
	@Override
	public Playground getPlayground() {return playground;}
	@Override
	public MainMenuView getMainMenu() {return mainMenu;}
}