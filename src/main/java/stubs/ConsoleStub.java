package stubs;

import model.Game;
import view.ToggleSwitch;
import view.ViewInterface;

/**
 * A stub of the console for JUnit tests.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class ConsoleStub implements ViewInterface{
	
	private Game game;
	
	/**
	 * The constructor of the ConsoleStub used for testing
	 */
	public ConsoleStub() {
		game = new GameStub();
	}
	
	@Override
	public void init(Game game) {}

	@Override
	public void update() {
		game.getOutputClear();
	}
	
	@Override
	public int initConsoleOut(String[] out, int smaller) {return 0;}
	@Override
	public void updateBoard() {}
	@Override
	public void gameEnd(String content) {}
	
	// Getter and setter
	
	@Override
	public Game getGame() {return this.game;}
	@Override
	public int getLanguage() {return 0;}
	@Override
	public void setLanguage(int language) {}
	@Override
	public ToggleSwitch getBtnLanguage() {return null;}
}