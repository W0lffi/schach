package stubs;
import java.util.Scanner;

import controller.ConsoleController;
import model.Game;
import model.Normal;

/**
 * A stub of the controller for JUnit tests.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
public class ControllerStub extends ConsoleController {

	/**
	 * The constructor of the ControllerStub used for testing
	 */
	public ControllerStub() {
		Game game = new Game();
		game.setPlayer2(1,1);
		super.setGame(game);
		super.setRules(new Normal(super.getGame()));
		super.setView(new ConsoleStub());
		super.setSc(new Scanner(System.in));
	}
}
