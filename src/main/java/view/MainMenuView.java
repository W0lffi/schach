package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Game;
import schach.Constants;

/**
 * The view for the main menu.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class MainMenuView extends VBox implements ViewInterface {
	
	private Scene scene;
	private AnchorPane container;
	private ToggleSwitch btnLanguage;
	private Button btnStartHuman;
	private Button btnStartCom;
	private Button btnQuit;

	/**
	 * The constructor expect no parameters.
	 */
	public MainMenuView() {
		container = new AnchorPane();
		container.setLayoutX(Constants.SCREEN_WIDTH);
		container.setLayoutY(Constants.SCREEN_HEIGHT);
		Image imgBackground = ImageHandler.getInstance().getImage(Constants.DIRECTORY[0], "menuBackground");
		ImageView background = new ImageView();
		background.setImage(imgBackground);
		background.setFitHeight(Constants.SCREEN_HEIGHT);
		background.setFitWidth(Constants.SCREEN_WIDTH);
		background.setLayoutX(0);
		background.setLayoutY(0);
		initButtons();
		initTexts(background);
		getChildren().addAll(container);
	}
	
	@Override
	public void init(Game game) {
		btnLanguage = new ToggleSwitch(game);
		btnLanguage.setLayoutX(1150);
		btnLanguage.setLayoutY(15);
		game.attach(this);
		container.getChildren().add(btnLanguage);
	}
	
	@SuppressWarnings("PMD.AvoidDuplicateLiterals") // It would be useless to make a "VS" constant...  
	private void initButtons() {
		btnStartHuman = new Button(Constants.MAIN_MENU_START_HUMAN[1] + " VS " + Constants.MAIN_MENU_START_HUMAN[1]);
		btnStartCom = new Button(Constants.MAIN_MENU_START_HUMAN[1] + " VS " + Constants.MAIN_MENU_START_COM);
		btnQuit = new Button(Constants.MAIN_MENU_BTN_QUIT[1]);
		btnStartHuman.setLayoutX(580);
		btnStartHuman.setLayoutY(285);
		btnStartCom.setLayoutX(570);
		btnStartCom.setLayoutY(350);
		btnQuit.setLayoutX(605);
		btnQuit.setLayoutY(425);
	}
	
	private void initTexts(ImageView background) {
		Text txtTitle = new Text("The board with the figures\n\t\ttbWTF");
		txtTitle.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		txtTitle.setFill(new Color(0d, 0d, 0d, 1d));
		txtTitle.setLayoutX(260);
		txtTitle.setLayoutY(110);
		Text txtTitleShadow = new Text("The board with the figures\n\t\ttbWTF");
		txtTitleShadow.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 50));
		txtTitleShadow.setFill(new Color(0.6d, 0.5d, 0.4d, 0.4d));
		txtTitleShadow.setLayoutX(250);
		txtTitleShadow.setLayoutY(100);
		container.getChildren().addAll(background, txtTitleShadow, txtTitle, btnStartHuman, btnStartCom, btnQuit);
	}
	
	@Override
	public void update() {}
	@Override
	public void updateBoard() {}
	@Override
	public int initConsoleOut(String[] out, int smaller) {return 0;}
	@Override
	public void gameEnd(String content) {}
	@Override
	public Game getGame() {return null;}
	
	// Getters and Setters
	
	@Override
	public int getLanguage() {return 0;}
	@Override
	public void setLanguage(int language) {
		if(scene.getRoot().getClass().equals(Playground.class)) {
			btnLanguage.updateBtn();
		}
		btnStartHuman.setText(Constants.MAIN_MENU_START_HUMAN[language] + " VS " + Constants.MAIN_MENU_START_HUMAN[language]);
		btnStartCom.setText(Constants.MAIN_MENU_START_HUMAN[language] + " VS " + Constants.MAIN_MENU_START_COM);
		btnQuit.setText(Constants.MAIN_MENU_BTN_QUIT[language]);
		if(language == 0) {
			btnQuit.setLayoutX(btnQuit.getLayoutX()-5);
		}else {
			btnQuit.setLayoutX(btnQuit.getLayoutX()+5);
		}
	}
	@Override
	public ToggleSwitch getBtnLanguage() {return btnLanguage;}
	public Button getBtnStartHuman() {return btnStartHuman;}
	public Button getBtnStartCom() {return btnStartCom;}
	public Button getBtnQuit() {return btnQuit;}
	
	/**
	 * Getter for the scene.
	 * @return The scene.
	 */
	public Scene getMainScene() {return scene;}
	public void setScene(Scene scene) {this.scene = scene;}
}
