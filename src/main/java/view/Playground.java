package view;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Computer;
import model.Figure;
import model.Game;
import model.King;
import model.Normal;
import model.Pawn;
import model.Position;
import model.Tile;
import schach.Constants;
import stubs.GameStub;

// Auswahl der Ki
// Aufgeben und Unentschieden button + methode

/**
 * The view for the playground (boardAndStates with figures).
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.CyclomaticComplexity" }) 	// We need a toggle button for the language, so
public class Playground extends VBox implements ViewInterface {			// we couldn't add it to the button-array. It
	private Game game;													// be harder to understand the code if we out-
	private AnchorPane container;										// source a few methods in other.
	private Button buttons[]; // mainMenu, undo, redo, surrender, draw
	private ToggleSwitch btnLanguage;
	private MenuButton options;
	private ImageView[] boardAndStates; // board, boardMirror, isCheck, kingJohnny
	private ImageView[] figures;  // white{0-7 Pawns, 8&15 Rook, 9&14 knight, 10&13 bishop, 11 queen, 12 king,} 
								  // black{16-23 pawns, 24&31 rook, 25&30 knight, 26&29 bishop, 27 queen, 28 king}
	private ListView<Label> output;
	
	private Map<Integer, Integer> literals = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> numbers = new HashMap<Integer, Integer>();
	
	private boolean[] states; // changed, mirror, drawCheck
	
	/**
	 * The constructor for the playground.
	 */
	public Playground() {
		game = new Game();
		game.setGamemode(new Normal(game));
		game.getGamemode().setGame(game);
		game.getGamemode().setLanguage(game.getLanguage());
		game.attach(this);
		boardAndStates = PlaygroundInitialize.getInstance().initBoardAndStates();
		states = new boolean[3];
		for(int i=0;i<states.length;i++) {
			states[i]  = false;
		}
		states[2] = true;
		container = new AnchorPane();
		buttons = new Button[5];
		buttons[0] = PlaygroundInitialize.getInstance().initButtons(0, game.getLanguage());
		buttons[1] = PlaygroundInitialize.getInstance().initButtons(1, game.getLanguage());
		buttons[2] = PlaygroundInitialize.getInstance().initButtons(2, game.getLanguage());
		buttons[3] = PlaygroundInitialize.getInstance().initButtons(3, game.getLanguage());
		buttons[4] = PlaygroundInitialize.getInstance().initButtons(4, game.getLanguage());
		options = PlaygroundInitialize.getInstance().initOptions(game.getLanguage());
		output = PlaygroundInitialize.getInstance().initOutput();
		literals = PlaygroundInitialize.getInstance().initHashmaps(true);
		numbers = PlaygroundInitialize.getInstance().initHashmaps(false);
		figures = PlaygroundInitialize.getInstance().getDefaultPositions(this);
		btnLanguage = new ToggleSwitch(game);
		btnLanguage.setLayoutX(1150);
		btnLanguage.setLayoutY(15);
		init(game);
		container.getChildren().addAll(boardAndStates[0], boardAndStates[1], boardAndStates[2], boardAndStates[3], boardAndStates[4],
				options, buttons[0], buttons[1], buttons[2], buttons[3], buttons[4], btnLanguage, output,
				figures[0], figures[1], figures[2], figures[3], figures[4], figures[5], figures[6], figures[7], 
				figures[8], figures[9], figures[10], figures[11], figures[12], figures[13], figures[14], figures[15], 
				figures[16], figures[17], figures[18], figures[19], figures[20], figures[21], figures[22], figures[23], 
				figures[24], figures[25], figures[26], figures[27], figures[28], figures[29], figures[30], figures[31]);
		getChildren().add(container);
	}
	
	/**
	 * Second constructor for JUnit testing which is not starting a game
	 * @param game An GameStub object created for testing
	 */
	public Playground(GameStub game) {
		this.game = game;
		literals = PlaygroundInitialize.getInstance().initHashmaps(true);
		numbers = PlaygroundInitialize.getInstance().initHashmaps(false);
	}
	
	@Override
	public void init(Game game) {
		states[0] = false;			// changed
		output.getItems().clear();
		game.getCemetery().clear();
		boardAndStates[0].setVisible(true);
		boardAndStates[1].setVisible(false);
		boardAndStates[2].setVisible(false);
		figures = PlaygroundInitialize.getInstance().getDefaultPositions(this);
		container.maxWidth(Constants.SCREEN_WIDTH);
		container.maxHeight(Constants.SCREEN_HEIGHT);
		container.setLayoutX(Constants.SCREEN_WIDTH);
		container.setLayoutY(Constants.SCREEN_HEIGHT);
		if(container.getChildren().size() > Constants.CONTAINER_SIZE) {
			container.getChildren().remove(Constants.CONTAINER_SIZE, container.getChildren().size());
			boolean settings[] = game.getController().getSettings();
			settings[1] = false;	// transforming
			game.getController().setSettings(settings);
		}
	}

	@Override
	public void update() {
		if(states[0] && !game.getController().getSettings()[6]) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Tile[][] tiles = game.getBoard().getTiles();
					Position p = game.getController().getBoardPos();
					Figure fig = tiles[p.getDestLine()][p.getDestColumn()].getFigure();
					// Delete undid moves from list and scroll to the new last item
					if(output.getItems().size() > 0) {
						game.getBoard().delNonExistingMovesFromList(output);
					}
					Label lastMove = new Label(game.getOutputClear());
					lastMove.setTextFill(Constants.REVERSIBLE);
					output.getItems().add(lastMove);
					output.scrollTo(lastMove);
					// if a enemy is dead move him to the cemetery
					if(game.getCemetery().isNewDead()
					&& game.getCemetery().getBeaten().get(game.getCemetery().getBeaten().size()-1).getPosition().getDestLine() == -1) {
						moveToCemetery(game.getCemetery().getBeaten().get(game.getCemetery().getBeaten().size()-1));
						game.getCemetery().setNewDead(false);
					}
					// move the figure
					moveFigureImage(fig, p.getLine(), p.getColumn());
					// did the king a castling
					if(fig.getClass().equals(King.class)) {
						castlings(fig);
					}
					// transform character if the pawn is at the last line
					if(fig.getClass().equals(Pawn.class)) {
						transform(fig);	
					}
					// is enemy in check?
					Figure enemyKing = Figure.getKingPos(tiles, fig.isColor());
					if(enemyKing.isCheck()) {
						drawCheck(enemyKing);
					}
					// Should the field be mirrored?
					if(states[1]) {
						mirrorField();
					}
					// Add the positions of the figure images to the storage for undo redo
					saveFigureImgs();
					
					p.setColumn(-1);
					p.setLine(-1);
					p.setDestColumn(-1);
					p.setDestLine(-1);
					boolean settings[] = game.getController().getSettings();
					settings[4] = true;		// firstFigure
					game.getController().setSettings(settings);
					if(game.isTurn() == game.getPlayer2().isColor() && game.getPlayer2().getClass().equals(Computer.class)) {
						boardAndStates[4].setVisible(true);
					}else {
						boardAndStates[4].setVisible(false);
					}
				}
			});
		states[0] = false;
		}
	}
	
	private void saveFigureImgs() {
		String positions = "";
		for(ImageView iv : figures) {
				positions += iv.getLayoutX() + "#" + iv.getLayoutY() + "#";
		}
		game.getBoard().getBoards().setFigureImgPositions(positions);
	}
	
	@Override
	@SuppressWarnings("PMD.AvoidReassigningLoopVariables") 	// It's useful here to increment the variable outside
	public void updateBoard() {								// of the for-loop  signature
		Figure whiteKing = Figure.getKingPos(game.getBoard().getTiles(), false);
		Figure blackKing = Figure.getKingPos(game.getBoard().getTiles(), true);
		drawCheck(whiteKing);
		drawCheck(blackKing);
		String positions = game.getBoard().getBoards().getFigureImgPositions();
		String[] figureImgPositions = positions.split("#");
		int figureCount = 0;
		for (int i=0;i<figureImgPositions.length; i++) {
			figures[figureCount].setLayoutX(Double.valueOf(figureImgPositions[i]));
			figures[figureCount].setLayoutY(Double.valueOf(figureImgPositions[++i]));
			if(figures[figureCount].getFitHeight() == Constants.FIG_CEMETERY_WIDTH_HEIGHT && figures[figureCount].getLayoutX() >= literals.get(0)) {
				figures[figureCount].setFitHeight(Constants.FIG_WIDTH_HEIGHT);
				figures[figureCount].setFitWidth(Constants.FIG_WIDTH_HEIGHT);
			}
			else if(figures[figureCount].getFitHeight() == Constants.FIG_WIDTH_HEIGHT && figures[figureCount].getLayoutX() < literals.get(0)) {
				figures[figureCount].setFitHeight(Constants.FIG_CEMETERY_WIDTH_HEIGHT);
				figures[figureCount].setFitWidth(Constants.FIG_CEMETERY_WIDTH_HEIGHT);
			}
			figureCount++;
		}
	}
	
	/**
	 * Draws the red field on the king if he is in check.
	 * @param enemyKing The king.
	 */
	private void drawCheck(Figure enemyKing) {
		if(states[2]) {	// drawCheck
			boardAndStates[2].setLayoutX(literals.get(enemyKing.getPosition().getColumn()));
			boardAndStates[2].setLayoutY(numbers.get(enemyKing.getPosition().getLine()));
			boardAndStates[2].setVisible(true);
		}
		if(!enemyKing.isCheck()) {
			boardAndStates[2].setVisible(false);
		}
	}
	
	/**
	 * Transform the pawn.
	 * @param fig The pawn.
	 */
	private void transform(Figure fig) {
		if(fig.getPosition().getDestLine() == 0 || fig.getPosition().getDestLine() == 7) {
			int indexFigImg = getIndexOfFigureImage(numbers.get(fig.getPosition().getDestLine()), literals.get(fig.getPosition().getDestColumn()));
			if(game.getPlayer2().getClass().equals(Computer.class) && game.getPlayer1().isColor() == game.isTurn()){
				String imgTrans = fig.isColor() ? "queenWhite" : "queenBlack";
				Pawn.transformation(game.getBoard().getTiles(), fig.getPosition().getDestLine(), fig.getPosition().getDestColumn(), 'Q');
				figures[indexFigImg].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], imgTrans));
			}else {
				boolean[] settingsL = game.getController().getSettings();
				settingsL[1] = true;	// transforming
				game.getController().setSettings(settingsL);
				Button btnRook = PlaygroundInitialize.getInstance().initTransButton(figures[indexFigImg], 0);
				Button btnKnight = PlaygroundInitialize.getInstance().initTransButton(figures[indexFigImg], 1);
				Button btnBishop = PlaygroundInitialize.getInstance().initTransButton(figures[indexFigImg], 2);
				Button btnQueen = PlaygroundInitialize.getInstance().initTransButton(figures[indexFigImg], 3);
				container.getChildren().addAll(btnRook, btnKnight, btnBishop, btnQueen);
				btnRook.setOnAction(event -> {
					String imgTrans = fig.isColor() ? "rookWhite" : "rookBlack";
					pawnTransCheck(fig, imgTrans, indexFigImg, 'R');
					container.getChildren().removeAll(btnRook, btnKnight, btnBishop, btnQueen);
				});
				btnKnight.setOnAction(event -> {
					String imgTrans = fig.isColor() ? "knightWhite" : "knightBlack";
					pawnTransCheck(fig, imgTrans, indexFigImg, 'N');
					container.getChildren().removeAll(btnRook, btnKnight, btnBishop, btnQueen);
				});
				btnBishop.setOnAction(event -> {
					String imgTrans = fig.isColor() ? "bishopWhite" : "bishopBlack";
					pawnTransCheck(fig, imgTrans, indexFigImg, 'B');
					container.getChildren().removeAll(btnRook, btnKnight, btnBishop, btnQueen);
				});
				btnQueen.setOnAction(event -> {
					String imgTrans = fig.isColor() ? "queenWhite" : "queenBlack";
					pawnTransCheck(fig, imgTrans, indexFigImg, 'Q');
					container.getChildren().removeAll(btnRook, btnKnight, btnBishop, btnQueen);
				});
			}

		}
	}
	
	/**
	 * Mirroring the figure images on the playground.
	 */
	@SuppressWarnings("PMD.CyclomaticComplexity")
	private void mirrorField() {
		for(int i=0;i<container.getChildren().size();i++) {
			if(i == 2 || i > 12) { // i=2 checkField i>12 figures in the container
				ImageView image = (ImageView) container.getChildren().get(i);
				if(i != 2 && image.getFitHeight() == Constants.FIG_WIDTH_HEIGHT || i > 42 || i == 2 && boardAndStates[2].isVisible() && !game.isTurn()) {
/* mirror x */		container.getChildren().get(i).setLayoutX(Position.mirrorPosition(this, new Position((int)container.getChildren().get(i).getLayoutY(), (int)container.getChildren().get(i).getLayoutX())).getColumn());
/* mirror y */		container.getChildren().get(i).setLayoutY(Position.mirrorPosition(this, new Position((int)container.getChildren().get(i).getLayoutY(), (int)container.getChildren().get(i).getLayoutX())).getLine());
				}
			}
		}
		boardAndStates[0].setVisible(!boardAndStates[0].isVisible());
		boardAndStates[1].setVisible(!boardAndStates[1].isVisible());
		if(game.getPlayer2().getClass().equals(Computer.class)) {
			System.out.println(boardAndStates[4].getLayoutY());
			if(boardAndStates[3].getLayoutY() == Constants.JOHNNY_Y_BLACK) {
				boardAndStates[3].setLayoutY(Constants.JOHNNY_Y_WHITE);
				boardAndStates[4].setLayoutY(Constants.BUBBLE_Y_WHITE);
			}else {
				boardAndStates[3].setLayoutY(Constants.JOHNNY_Y_BLACK);
				boardAndStates[4].setLayoutY(Constants.BUBBLE_Y_BLACK);
			}
		}
	}
	
	/**
	 * Tests whether the king did a castling, and if updates the rook image position.
	 * @param fig The king.
	 */
	private void castlings(Figure fig) {
		String move = output.getItems().get(output.getItems().size()-1).getText();
		int l = fig.isColor() ? 7 : 0;
		int currentColumn = Character.getNumericValue(move.charAt(0));
		int destinationColumn = Character.getNumericValue(move.charAt(3));
		if(Math.abs(destinationColumn - currentColumn) == 2) {
			int cs = destinationColumn == 12 ? literals.get(0) : literals.get(7);  // the source column where the rook stands before the castling depends on the direction of the king's move
			int cd = destinationColumn == 12 ? literals.get(3) : literals.get(5);  // the new column where the rook stands after the castling depends on the direction of the king's move
			int indexFigImg = getIndexOfFigureImage(numbers.get(l), cs);
			figures[indexFigImg].setLayoutX(cd);
		}
	}
	/**
	 * Move the figure images to the cemetery if they are beaten.
	 * @param beatenFig The beaten figure.
	 */
	private void moveToCemetery(Figure beatenFig) {
		int countFigure = game.getCemetery().countFigsByColor(beatenFig.isColor()) - 1;
		int wMultiplicator = countFigure % 5 == 0 ? 0 : countFigure % 5;
		int hFactor = countFigure / 5;
		int hMultiplicator = beatenFig.isColor() ? hFactor * -1 : hFactor;
		int height = beatenFig.isColor() ? 629 + (Constants.CEMETERY_POSITIONS_Y + 11 ) * hMultiplicator : Constants.CEMETERY_POSITIONS_Y  + Constants.FIG_CEMETERY_WIDTH_HEIGHT * hMultiplicator;
		int width = Constants.CEMETERY_POSITIONS_X + (Constants.FIG_CEMETERY_WIDTH_HEIGHT * wMultiplicator);
		int indexFigImg;
		if(states[1] && game.isTurn()) {
			indexFigImg = getIndexOfFigureImage(numbers.get(7-beatenFig.getPosition().getLine()), literals.get(7-beatenFig.getPosition().getColumn()));
		}else {
			indexFigImg = getIndexOfFigureImage(numbers.get(beatenFig.getPosition().getLine()), literals.get(beatenFig.getPosition().getColumn()));
		}
		figures[indexFigImg].setLayoutX(width);
		figures[indexFigImg].setLayoutY(height);
		figures[indexFigImg].setFitHeight(Constants.FIG_CEMETERY_WIDTH_HEIGHT);
		figures[indexFigImg].setFitWidth(Constants.FIG_CEMETERY_WIDTH_HEIGHT);
	}

	/**
	 * Moves the figures images.
	 * @param fig The figure which has moved.
	 * @param line The line where the figure was standing.
	 * @param column The column where the figure was standing.
	 */
	private void moveFigureImage(Figure fig, int line, int column) {
		int destLine = fig.getPosition().getDestLine();
		int destColumn = fig.getPosition().getDestColumn();
		int indexFigImg;
		if(states[1] && game.isTurn()) {
			indexFigImg = getIndexOfFigureImage(numbers.get(7-line), literals.get(7-column));
		}else {
			indexFigImg = getIndexOfFigureImage(numbers.get(line), literals.get(column));
		}
		if(states[1] && game.isTurn()) {
			figures[indexFigImg].setLayoutY(numbers.get(7-destLine));
			figures[indexFigImg].setLayoutX(literals.get(7-destColumn));
		}else {
			figures[indexFigImg].setLayoutY(numbers.get(destLine));
			figures[indexFigImg].setLayoutX(literals.get(destColumn));
		}
		game.getBoard().removePosFromBoard(this);
	}
	
	/**
	 * Searches a imageView.
	 * @param imgLine The searched line of the field.
	 * @param imgColumn The searched column of the field.
	 * @return The searched imageView
	 */
	private int getIndexOfFigureImage(int imgLine, int imgColumn) {
		for(int i=0;i<figures.length;i++) {
			if(figures[i].getLayoutX() == imgColumn && figures[i].getLayoutY() == imgLine) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Adds an image to the scene container.
	 * @param image The image.
	 */
	public void addContainerPart(ImageView image) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				container.getChildren().add(image);
			}
		});
	}
	
	/**
	 * Removes an image from the scene container.
	 * @param image The image which has to be removed.
	 */
	public void remContainerPart(ImageView image) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				container.getChildren().remove(image);
			}
		});
	}
	
	/**
	 * Transforms the pawn in the background and set the new image.
	 * @param fig The pawn.
	 * @param imgTrans	The name of the new image.
	 * @param indexFigImg The index of the image in the image array.
	 * @param trans The transformation character.
	 */
	private void pawnTransCheck(Figure fig, String imgTrans, int indexFigImg, char trans) {
		Figure tmpFig = Pawn.transformation(game.getBoard().getTiles(), fig.getPosition().getDestLine(), fig.getPosition().getDestColumn(), trans);
		figures[indexFigImg].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], imgTrans));
		boolean[] settings = game.getController().getSettings();
		settings[1] = false;	// transforming
		game.getController().setSettings(settings);
		Figure enemyKing = Figure.getKingPos(game.getBoard().getTiles(), fig.isColor());
		enemyKing.setCheck(King.verifyCheckEnemy(game.getBoard().getTiles(), tmpFig.isColor()));
		if(enemyKing.isCheck()) {
			drawCheck(enemyKing);
		}
	}
	
	@Override
	public void gameEnd(String content) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert end = PlaygroundInitialize.getInstance().endAlert(game, content);
				end.setX(Constants.SCREEN_WIDTH/2 + 150);
				end.setY(Constants.SCREEN_HEIGHT/2 + 100);
				end.showAndWait();
			}
		});
	}
	
	@Override
	public int initConsoleOut(String[] out, int smaller) {return 0;}
	
	// Getter and setter
	@Override
	public void setLanguage(int language) {
		buttons[0].getTooltip().setText(Constants.EXIT_TOOLTIP[language]);
		buttons[1].getTooltip().setText(Constants.UNDO_TOOLTIP[language]);
		buttons[2].getTooltip().setText(Constants.REDO_TOOLTIP[language]);
		buttons[3].getTooltip().setText(Constants.SURRENDER_TOOLTIP[language]);
		buttons[4].getTooltip().setText(Constants.DRAW_TOOLTIP[language]);
		((CheckBox)((CustomMenuItem)options.getItems().get(0)).getContent()).setText(Constants.MOVE_POSSIBILITIES_BTN[language]);
		Tooltip tpMovePos = new Tooltip(Constants.MOVE_POSSIBILITIES_TOOLTIP[language]);
		tpMovePos.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		Tooltip.install(((CustomMenuItem)options.getItems().get(0)).getContent(), tpMovePos);
		((CheckBox)((CustomMenuItem)options.getItems().get(1)).getContent()).setText(Constants.FIRST_FIGURE_BTN[language]);
		Tooltip tpFirst = new Tooltip(Constants.FIRST_FIGURE_TOOLTIP[language]);
		tpFirst.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		Tooltip.install(((CustomMenuItem)options.getItems().get(1)).getContent(), tpFirst);
		((CheckBox)((CustomMenuItem)options.getItems().get(2)).getContent()).setText(Constants.MIRROR_FIELD_BTN[language]);
		Tooltip tpMirror = new Tooltip(Constants.MIRROR_FIELD_TOOLTIP[language]);
		tpMirror.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		Tooltip.install(((CustomMenuItem)options.getItems().get(2)).getContent(), tpMirror);
		((CheckBox)((CustomMenuItem)options.getItems().get(3)).getContent()).setText(Constants.DRAW_CHECK_BTN[language]);
		Tooltip tpDrawCheck = new Tooltip(Constants.DRAW_CHECK_TOOLTIP[language]);
		tpDrawCheck.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		Tooltip.install(((CustomMenuItem)options.getItems().get(3)).getContent(), tpDrawCheck);
		if(game.getController().getMainMenu().getMainScene().getRoot().getClass().equals(MainMenuView.class)) {
			btnLanguage.updateBtn();
		}
	}
	
	@Override
	public int getLanguage() {return 0;}
	@Override
	public Game getGame() {return game;}
	@Override
	public ToggleSwitch getBtnLanguage() {return null;}
	public Button[] getButtons() {return buttons.clone();}
	public MenuButton getOptions() {return options;}
	public ImageView[] getFigures() {return figures.clone();}
	public void setFigures(ImageView[] figures) {this.figures = figures.clone();}
	public ListView<Label> getOutput(){return output;}
	public void setBoardAndStates(ImageView[] boardAndStates) {this.boardAndStates = boardAndStates.clone();}
	public ImageView[] getBoardAndStates() {return boardAndStates.clone();}
	public boolean[] getStates() {return states.clone();}
	public void setStates(boolean states[]) {
		if(states[1] != this.states[1] && !game.isTurn()) {
			mirrorField();
		}
		this.states = states.clone();
	}
	public Map<Integer, Integer> getLiterals(){return literals;}
	public Map<Integer, Integer> getNumbers(){return numbers;}
	
	/**
	 * Save the Figure images in the storage.
	 */
	public void setSaveFigureImgs() {saveFigureImgs();}
	
	/**
	 * The last entry in the list will be gray.
	 * @param moveCount Count of performed moves during the game. 
	 */
	public void setLastOutputGrey(int moveCount) {output.getItems().get(output.getItems().size() - moveCount).setTextFill(Constants.REPEATABLE);}
	
	/**
	 * The last entry in the list will be white.
	 * @param moveCount Count of performed moves during the game.  
	 */
	public void setLastOutputBlack(int moveCount) {output.getItems().get(output.getItems().size() - moveCount).setTextFill(Constants.REVERSIBLE);}
}