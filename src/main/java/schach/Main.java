package schach;

import controller.ConsoleController;
import controller.MainMenuController;
import controller.PlaygroundController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.MainMenuView;
import view.Playground;
import view.Console;
import view.ViewInterface;

/**
 * The entry point of the program. The game and all its components are initialized here.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Main extends Application{
	
	/**
	 * The main method creates a new view depending on the input parameters. It creates a new Controller and passes the view to it.
	 * @param args The arguments which are passed by the user in the command line. It should be empty or else contain "--no-gui".
	 */
    public static void main(String[] args) {
    	ViewInterface view = null;
    	if(args.length > 0 &&  args[0].equals("--no-gui")) {		// If the --no-gui argument was passed start the game in console-mode
    		if(args.length > 1 && args[1].equals("--simple")) {
    			view = new Console(true);
    		}else {
    			view = new Console(false);
    		}
    		new ConsoleController(view);
    	}	
    	else { 													// otherwise start the gui
    		launch();
    	}
    	System.exit(0);
    }

    @Override
	public void start(Stage stage) throws Exception {
		MainMenuView mainMenu = new MainMenuView();
		Playground board = new Playground();
		mainMenu.init(board.getGame());
		Scene scene = new Scene(mainMenu, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		mainMenu.setScene(scene);
		scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
		MainMenuController menuController = new MainMenuController(mainMenu, board, scene);
		menuController.init();
		PlaygroundController playgroundController = new PlaygroundController(board, mainMenu, scene);
		playgroundController.init();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
}