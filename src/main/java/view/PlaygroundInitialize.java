package view;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Game;
import schach.Constants;

/**
 * This class initializes the board, states and figures on the playground.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class PlaygroundInitialize {
	
	private static PlaygroundInitialize instance;
	private ImageView[] figures;
	
	
	/**
	 * Basic constructor for this class.
	 */
	private PlaygroundInitialize() {};
	
	/**
	 * Creates a new instance if none exists and returns the instance.
	 * @return A new playground instance.
	 */
	public static PlaygroundInitialize getInstance() {
		if(instance == null) {
			instance = new PlaygroundInitialize();
		}
		return instance;
	}
	
	/**
	 * Initialize the buttons of the playground. 
	 * @param btnNumber The number of the button to be initialized. 
	 * @param language The language.
	 * @return The button.
	 */
	@SuppressWarnings("PMD.NcssCount") //  It would be stupid to make a second initButtons method ...
	public Button initButtons(int btnNumber, int language) {
		Button button = new Button();
		ImageView imageview = new ImageView();
		Tooltip tooltip = new Tooltip();
		imageview.setFitHeight(Constants.BUTTON_IMG_WIDTH_HEIGHT);
		imageview.setFitWidth(Constants.BUTTON_IMG_WIDTH_HEIGHT);
		switch(btnNumber) {
			case 0:			// exit
				button.setLayoutX(1200);
				button.setLayoutY(650);
				imageview.setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "exit"));
				tooltip.setText(Constants.EXIT_TOOLTIP[language]);
			break;
			case 1:			// undo
				button.setLayoutX(85);
				button.setLayoutY(Constants.SCREEN_HEIGHT/2 - 80);
				imageview.setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "undo"));
				tooltip.setText(Constants.UNDO_TOOLTIP[language]);
			break;
			case 2:			// redo
				button.setLayoutX(145);
				button.setLayoutY(Constants.SCREEN_HEIGHT/2 - 80);
				imageview.setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "redo"));
				tooltip.setText(Constants.REDO_TOOLTIP[language]);
			break;
			case 3:			// surrender
				button.setLayoutX(85);
				button.setLayoutY(Constants.SCREEN_HEIGHT/2 - 30);
				imageview.setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "surrender"));
				tooltip.setText(Constants.SURRENDER_TOOLTIP[language]);
			break;
			case 4:			// draw
				button.setLayoutX(145);
				button.setLayoutY(Constants.SCREEN_HEIGHT/2 - 30);
				imageview.setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "draw"));
				tooltip.setText(Constants.DRAW_TOOLTIP[language]);
			break;
				default:
					System.out.println("Invalid button index");
			break;
		}
		button.setGraphic(imageview);
		button.setTooltip(tooltip);
		tooltip.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		return button;
	}
	
	/**
	 * Initialize the options-menuButton of the playground.
	 * @param language The language.
	 * @return The menuButton with his items.
	 */
	public MenuButton initOptions(int language) {
		CheckBox cbMovePossibilities = new CheckBox(Constants.MOVE_POSSIBILITIES_BTN[language]);
		cbMovePossibilities.setSelected(true);
		CustomMenuItem movePossibilities = new CustomMenuItem(cbMovePossibilities);
		Tooltip moveTp = new Tooltip(Constants.MOVE_POSSIBILITIES_TOOLTIP[language]);
		moveTp.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		
		CheckBox cbFirstigure = new CheckBox(Constants.FIRST_FIGURE_BTN[language]);
		CustomMenuItem firstFigure = new CustomMenuItem(cbFirstigure);
		Tooltip firstTp = new Tooltip(Constants.FIRST_FIGURE_TOOLTIP[language]);
		firstTp.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		
		CheckBox cbMirrorField = new CheckBox(Constants.MIRROR_FIELD_BTN[language]);
		CustomMenuItem mirrorField = new CustomMenuItem(cbMirrorField);
		Tooltip mirrorTp = new Tooltip(Constants.MIRROR_FIELD_TOOLTIP[language]);
		mirrorTp.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		
		CheckBox cbDrawCheck = new CheckBox(Constants.DRAW_CHECK_BTN[language]);
		cbDrawCheck.setSelected(true);
		CustomMenuItem drawCheck = new CustomMenuItem(cbDrawCheck);
		Tooltip checkTp = new Tooltip(Constants.DRAW_CHECK_TOOLTIP[language]);
		checkTp.setShowDelay(new Duration(Constants.TOOLTIP_TIME));
		ImageView imgVOption = new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "options"));
		imgVOption.setFitHeight(Constants.BUTTON_IMG_WIDTH_HEIGHT);
		imgVOption.setFitWidth(Constants.BUTTON_IMG_WIDTH_HEIGHT);
		
		MenuButton options = new MenuButton("", imgVOption, movePossibilities, firstFigure, mirrorField, drawCheck);
		Tooltip.install(movePossibilities.getContent(), moveTp);
		Tooltip.install(firstFigure.getContent(), firstTp);
		Tooltip.install(mirrorField.getContent(), mirrorTp);
		Tooltip.install(drawCheck.getContent(), checkTp);
		options.setLayoutX(1230);
		options.setLayoutY(5);
		options.setPopupSide(Side.LEFT);
		return options;
	}
	
	/**
	 * Initialize the output listView of the playground.
	 * @return A ListView with all labels. 
	 */
	public ListView<Label> initOutput() {
		ListView<Label> output = new ListView<Label>();
		output.setPrefSize(75, 200);
		output.setLayoutX(1120);
		output.setLayoutY(220);
		return output;
	}
	
	/**
	 * Initialize the literals and numbers maps of playground.
	 * @param axes Affect whether literals or numbers will be initialized, true for literals.
	 * @return The literals or numbers map.
	 */
	public Map<Integer, Integer> initHashmaps(boolean axes) {
		Map<Integer, Integer> coordinates = new HashMap<Integer, Integer>();
		if(axes) {	// literals
			coordinates.put(0, 320);
			coordinates.put(1, 400);
			coordinates.put(2, 480);
			coordinates.put(3, 560);
			coordinates.put(4, 640);
			coordinates.put(5, 720);
			coordinates.put(6, 800);
			coordinates.put(7, 880);
		}else {		// numbers
			coordinates.put(0, 40);
			coordinates.put(1, 120);
			coordinates.put(2, 200);
			coordinates.put(3, 280);
			coordinates.put(4, 360);
			coordinates.put(5, 440);
			coordinates.put(6, 520);
			coordinates.put(7, 600);
		}
		return coordinates;
	}
	
	/**
	 * Initialize the boardAndStates array.
	 * @return The boardAndStates array.
	 */
	public ImageView[] initBoardAndStates() {
		ImageView[] boardAndStates = new ImageView[5];
		for(int i=0; i<boardAndStates.length;i++) {
			boardAndStates[i] = new ImageView();
		}
		boardAndStates[0].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[1], "boardClassic"));
		boardAndStates[1].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[1], "boardMirrorClassic"));
		for(int i=0;i<2;i++) {
			boardAndStates[i].setFitHeight(Constants.SCREEN_HEIGHT);
			boardAndStates[i].setFitWidth(Constants.SCREEN_WIDTH);
			boardAndStates[i].setLayoutX(0);
			boardAndStates[i].setLayoutY(0);
		}
		boardAndStates[1].setVisible(false);
		boardAndStates[2].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[3], "isCheck"));
		boardAndStates[2].setFitHeight(Constants.FIG_WIDTH_HEIGHT);
		boardAndStates[2].setFitWidth(Constants.FIG_WIDTH_HEIGHT);
		boardAndStates[2].setLayoutX(0);
		boardAndStates[2].setLayoutY(0);
		boardAndStates[2].setOpacity(Constants.OPACITY_POSSIBLE_FIELD_CHECK);
		boardAndStates[2].setVisible(false);
		boardAndStates[3].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[3], "kingJohnny"));
		boardAndStates[3].setFitHeight(100.0d);
		boardAndStates[3].setFitWidth(100.0d);
		boardAndStates[3].setLayoutX(Constants.JOHNNY_X);
		boardAndStates[3].setVisible(false);
		boardAndStates[4].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[3], "bubble"));
		boardAndStates[4].setFitHeight(25.0d);
		boardAndStates[4].setFitWidth(50.0d);
		boardAndStates[4].setLayoutX(Constants.BUBBLE_X);
		boardAndStates[4].setVisible(false);
		return boardAndStates;
	}
	
	/**
	 * Create a dialog for choosing the color.
	 * @param game The current game.
	 * @param color A boolean with the color input of the computer.
	 * @return The created dialog.
	 */
	public Alert colorComputerAlert(Game game, boolean color){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.getDialogPane().getStylesheets().add(getClass().getResource("stylesheets/alert.css").toExternalForm());
		ButtonType buttonLeft;
		ButtonType middle;
		ButtonType buttonRight;
		alert.setGraphic(null);
		alert.setHeaderText("");
		alert.setY(Constants.SCREEN_HEIGHT/2 + 100);
		if(color) {
			alert.setX(Constants.SCREEN_WIDTH/2 + 150);
			alert.setTitle(Constants.CHOOSE_COLOR_ALERT[game.getGamemode().getLanguage()]);
			alert.setContentText("\t\t" + Constants.CHOOSE_COLOR_DIALOG[game.getGamemode().getLanguage()]);
			buttonLeft = new ButtonType(Constants.WIN_MSG_1_1[game.getGamemode().getLanguage()]);
			buttonRight = new ButtonType(Constants.WIN_MSG_1_2[game.getGamemode().getLanguage()]);
			alert.getButtonTypes().setAll(buttonLeft, buttonRight);
		}else {
			alert.setX(Constants.SCREEN_WIDTH/2 + 80);
			alert.setTitle(Constants.CHOOSE_COMPUTER_ALERT[game.getGamemode().getLanguage()]);
			alert.setContentText("\t\t" + Constants.CHOOSE_COMPUTER_DIALOG[game.getGamemode().getLanguage()]);
			buttonLeft = new ButtonType(Constants.EASY[game.getGamemode().getLanguage()]);
			middle = new ButtonType(Constants.DIFFICULT[game.getGamemode().getLanguage()]);
			buttonRight = new ButtonType(Constants.COFFEE[game.getGamemode().getLanguage()]);
			alert.getButtonTypes().setAll(buttonLeft, middle, buttonRight);
		}
		
		return alert;
	}
	
	/**
	 * Dialog for draw or surrender
	 * @param language The current language.
	 * @param surrender The boolean if surrender.
	 * @return The created dialog. 
	 */
	public Alert surrenderDrawAlert(int language, boolean surrender) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.getDialogPane().getStylesheets().add(getClass().getResource("stylesheets/alert.css").toExternalForm());
		alert.setTitle("");
		alert.setGraphic(null);
		alert.setHeaderText("");
		String contentText = surrender ? Constants.SURRENDER_DIALOG[language] : Constants.DRAW_DIALOG[language];
		alert.setContentText(contentText);
		ButtonType yes = new ButtonType(Constants.YES[language]);
		ButtonType no = new ButtonType(Constants.NO[language]);
		alert.setX(Constants.SCREEN_WIDTH/2 + 150);
		alert.setY(Constants.SCREEN_HEIGHT/2 + 100);
		alert.getButtonTypes().setAll(yes, no);
		return alert;
	}
	
	/**
	 * Create a dialog for game end.
	 * @param game The game.
	 * @param content The win or draw message.
	 * @return The created dialog.
	 */
	public Alert endAlert(Game game, String content){
		Alert end = new Alert(AlertType.INFORMATION);
		end.initStyle(StageStyle.UNDECORATED);
		end.getDialogPane().getStylesheets().add(getClass().getResource("stylesheets/alert.css").toExternalForm());
		end.setTitle("");
		end.setGraphic(null);
		end.setHeaderText("");
		end.setContentText("\t\t\t\t" + content);
		return end;
	}
	
	/**
	 * Initialize the transformation buttons.
	 * @param figPos The position of the current pawn image.
	 * @param figure The figure turns into.
	 * @return The transformation button.
	 */
	public Button initTransButton(ImageView figPos, int figure) {
		Button button = new Button();
		switch(figure) {
			case 0: // rook
				button.setLayoutX(figPos.getLayoutX()-50);
				button.setLayoutY(figPos.getLayoutY());
				button.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "rookTrans")));
			break;
			case 1: // knight
				button.setLayoutX(figPos.getLayoutX()-10);
				button.setLayoutY(figPos.getLayoutY()-40);
				button.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "knightTrans")));
			break;
			case 2: // bishop
				button.setLayoutX(figPos.getLayoutX()+40);
				button.setLayoutY(figPos.getLayoutY()-40);
				button.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "bishopTrans")));
			break;
			case 3: // queen
				button.setLayoutX(figPos.getLayoutX()+80);
				button.setLayoutY(figPos.getLayoutY());
				button.setGraphic(new ImageView(ImageHandler.getInstance().getImage(Constants.DIRECTORY[4], "queenTrans")));
			break;
				default:
					System.out.println("Invalid figure");
			break;
		}
		((ImageView) button.getGraphic()).setFitHeight(25);
		((ImageView) button.getGraphic()).setFitWidth(25);
		return button;
	}
	
	/**
	 * Expect the playground, and make the start position of the figure images.
	 * @param playground The playground
	 * @return The default positions of all figure images.
	 */
	public ImageView[] getDefaultPositions(Playground playground) {
		if(figures == null) {
			figures = new ImageView[32];
			for(int i=0;i<figures.length;i++) {
				figures[i] = new ImageView();
			}
		}
			initializePawnViews(playground);
			initializeRookViews(playground);
			initializeKnightViews(playground);
			initializeBishopViews(playground);
			initializeQueenViews(playground);
			initializeKingViews(playground);
			for(ImageView figure : figures) {
				figure.setFitWidth(Constants.FIG_WIDTH_HEIGHT);
				figure.setFitHeight(Constants.FIG_WIDTH_HEIGHT);
			}
		return figures.clone();
	}
	
	/**
	 * Initializes the pawns.
	 */
	private void initializePawnViews(Playground playground) {
		// white pawns
		for(int i=0; i<Constants.BOARD_DIMENSIONS;i++) {
			figures[i].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "pawnWhite"));
			figures[i].setLayoutY(playground.getNumbers().get(6));
			figures[i].setLayoutX(playground.getLiterals().get(i));
		}
		//black pawns
		int count = 0;
		for(int i=16; i<24;i++) {
			figures[i].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "pawnBlack"));
			figures[i].setLayoutY(playground.getNumbers().get(1));
			figures[i].setLayoutX(playground.getLiterals().get(count));
			count++;
		}
	}
	
	/**
	 * Initializes the rooks.
	 */
	private void initializeRookViews(Playground playground) {
		// white rooks
		figures[8].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "rookWhite"));
		figures[8].setLayoutY(playground.getNumbers().get(7));
		figures[8].setLayoutX(playground.getLiterals().get(0));

		figures[15].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "rookWhite"));
		figures[15].setLayoutY(playground.getNumbers().get(7));
		figures[15].setLayoutX(playground.getLiterals().get(7));
		// black rooks
		figures[24].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "rookBlack"));
		figures[24].setLayoutY(playground.getNumbers().get(0));
		figures[24].setLayoutX(playground.getLiterals().get(0));
					
		figures[31].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "rookBlack"));
		figures[31].setLayoutY(playground.getNumbers().get(0));
		figures[31].setLayoutX(playground.getLiterals().get(7));
	}
	
	/**
	 * Initializes the knights.
	 */
	private void initializeKnightViews(Playground playground) {
		// white knights
		figures[9].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "knightWhite"));
		figures[9].setLayoutY(playground.getNumbers().get(7));
		figures[9].setLayoutX(playground.getLiterals().get(1));
			
		figures[14].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "knightWhite"));
		figures[14].setLayoutY(playground.getNumbers().get(7));
		figures[14].setLayoutX(playground.getLiterals().get(6));
		// black knights
		figures[25].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "knightBlack"));
		figures[25].setLayoutY(playground.getNumbers().get(0));
		figures[25].setLayoutX(playground.getLiterals().get(1));
				
		figures[30].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "knightBlack"));
		figures[30].setLayoutY(playground.getNumbers().get(0));
		figures[30].setLayoutX(playground.getLiterals().get(6));
	}
	
	/**
	 * Initializes the bishops.
	 */
	private void initializeBishopViews(Playground playground) {
		// white bishops
		figures[10].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "bishopWhite"));
		figures[10].setLayoutY(playground.getNumbers().get(7));
		figures[10].setLayoutX(playground.getLiterals().get(2));
					
		figures[13].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "bishopWhite"));
		figures[13].setLayoutY(playground.getNumbers().get(7));
		figures[13].setLayoutX(playground.getLiterals().get(5));
		// black bishops
		figures[26].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "bishopBlack"));
		figures[26].setLayoutY(playground.getNumbers().get(0));
		figures[26].setLayoutX(playground.getLiterals().get(2));
						
		figures[29].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "bishopBlack"));
		figures[29].setLayoutY(playground.getNumbers().get(0));
		figures[29].setLayoutX(playground.getLiterals().get(5));
	}
	
	/**
	 * Initializes the queens.
	 */
	private void initializeQueenViews(Playground playground) {
		// white queen
		figures[11].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "queenWhite"));
		figures[11].setLayoutY(playground.getNumbers().get(7));
		figures[11].setLayoutX(playground.getLiterals().get(3));
		// black queen	
		figures[27].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "queenBlack"));
		figures[27].setLayoutY(playground.getNumbers().get(0));
		figures[27].setLayoutX(playground.getLiterals().get(3));
	}
	
	/**
	 * Initializes the kings.
	 */
	private void initializeKingViews(Playground playground) {
		// white king
		figures[12].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "kingWhite"));
		figures[12].setLayoutY(playground.getNumbers().get(7));
		figures[12].setLayoutX(playground.getLiterals().get(4));
		// black king	
		figures[28].setImage(ImageHandler.getInstance().getImage(Constants.DIRECTORY[2], "kingBlack"));
		figures[28].setLayoutY(playground.getNumbers().get(0));
		figures[28].setLayoutX(playground.getLiterals().get(4));
	}
}