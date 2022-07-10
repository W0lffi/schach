package stubs;

import java.util.ArrayList;

import model.Game;
import model.Normal;
import model.Pawn;
import view.ViewInterface;

/**
 * A stub of the game for JUnit tests.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
public class GameStub  extends Game {
	
	private BoardStub board;
	
	/**
	 * Constructor of the GameStub
	 */
	public GameStub() {
		ArrayList<ViewInterface> views = new ArrayList<ViewInterface>();
		super.setViews(views);
		super.setGamemode(new Normal(null));
		super.setPlayer2(1,1);
		this.board = new BoardStub(this);
	}
	
	/**
	 * Override start function to inhibit game start for testing
	 */
	@Override
	public void start() {}
	
	/**
	 * Override run function to inhibit game start for testing
	 */
	@Override
	public void run() {}
	
	/**
	 * Set-up function to check for a game ending scenario
	 * @return A finished board
	 */
	public BoardStub imitateGameEnding() {
		clearMe();
		board.createKings(0, 0, true);
		board.createKings(7, 7, false);
		board.createBishops(1, 2, true);
		board.createBishops(7, 3, false);
		fillMyCemetery(28);
		return board;
	}
	
	/**
	 * Method to clear the BoardStub for JUnit testing
	 */
	public void clearMe() {
		this.board = new BoardStub();
	}
	
	/**
	 * Method to fill the cemetery list with black pawns for JUnit Testing.
	 * @param numberOfFigs The number pawns to be added to the cemetery list.
	 */
	public void fillMyCemetery(int numberOfFigs) {
		for(int i =0; i < numberOfFigs; i++) {
			super.getCemetery().setBeaten(new Pawn(false, 0, 0));
		}
	}
	
	// Setter
	/**
	 * Overrides the running setter to inhibit game start for testing
	 */
	@Override
	public void setRunning(boolean running) {super.setRunning(true);}
	
	@Override
	public BoardStub getBoard() {
		return this.board;
	}
}
