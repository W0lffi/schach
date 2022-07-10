package controller;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import schach.Constants;
import view.Playground;
import view.PlaygroundInitialize;
import view.MainMenuView;

/**
 * The controller for the main menu. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class MainMenuController {

	private MainMenuView mainMenu;
	private Playground playground;
	private Scene scene;
	
	/**
	 * The main menu constructor expects the main menu view, playground view, playground controller and the scene. 
	 * @param mainMenu The main menu view.
	 * @param playground The playground view.
	 * @param scene The scene.
	 */
	public MainMenuController(MainMenuView mainMenu, Playground playground, Scene scene) {
		this.mainMenu = mainMenu;
		this.playground = playground;
		this.scene = scene;
	}
	
	/**
	 * We initialize the three buttons that we have in our main menu with their whole functionality.
	 */
	public void init() {
		mainMenu.getBtnStartHuman().setOnAction(event -> {
			scene.setRoot(playground);
			playground.getGame().setPlayer2(1,1);
			playground.getGame().setTurn(true);
			playground.getGame().getBoard().init();
			playground.init(playground.getGame());
			playground.getGame().getBoard().getBoards().clear();
			playground.setSaveFigureImgs();
			playground.getGame().getBoard().getBoards().boardToString(playground.getGame().getBoard(), true);
			playground.getGame().getPlayer1().setColor(true);
			playground.getGame().getPlayer2().setColor(false);
			playground.getGame().start(true);
		});
		mainMenu.getBtnStartCom().setOnAction(event ->{
			scene.setRoot(playground);
			playground.getGame().setTurn(true);
			playground.getGame().getBoard().init();
			playground.init(playground.getGame());
			playground.getGame().getBoard().getBoards().clear();
			playground.setSaveFigureImgs();
			playground.getGame().getBoard().getBoards().boardToString(playground.getGame().getBoard(), true);
			Alert alert = PlaygroundInitialize.getInstance().colorComputerAlert(playground.getGame(), false);
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get().getText().equals(Constants.EASY[playground.getGame().getLanguage()])) {
				playground.getGame().setPlayer2(2, 1);
			}else if(result.get().getText().equals(Constants.DIFFICULT[playground.getGame().getLanguage()])){
				playground.getGame().setPlayer2(2, 2);
			}else {
				playground.getGame().setPlayer2(2, 3);
			}
			boolean color = true;
			alert = PlaygroundInitialize.getInstance().colorComputerAlert(playground.getGame(), true);
			result = alert.showAndWait();
			color = result.get().getText().equals(Constants.WIN_MSG_1_1[playground.getGame().getGamemode().getLanguage()]) ? true : false;
			playground.getGame().getPlayer1().setColor(color);
			playground.getGame().getPlayer2().setColor(!color);
			playground.getGame().start(true);
			double y = color ? Constants.JOHNNY_Y_BLACK : Constants.JOHNNY_Y_WHITE;
			playground.getBoardAndStates()[3].setLayoutY(y);
			playground.getBoardAndStates()[3].setVisible(true);
			playground.getBoardAndStates()[4].setLayoutY(y+10.0d);
			if(!color) {
				playground.getBoardAndStates()[4].setVisible(true);
			}
		});
		mainMenu.getBtnQuit().setOnAction(event ->{
			System.exit(1);
		});
	}
}