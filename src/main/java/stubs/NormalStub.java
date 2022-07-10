package stubs;

import model.Game;
import model.Normal;

/**
 * NormalStub class for JUnit testing which sets a game and calls methods of parent class Normal
 * @author Christin Krause
 *
 */
public class NormalStub extends Normal {
	
	/**
	 * Stub for normal game mode
	 * @param game The game object to work on  
	 */
	public NormalStub(Game game) {
		super(game);
	}
	
	/*public NormalStub() {
		
	}
	
	// Getters and setters
	public Game getGame() {return super.getGame();}
	public void setGame(GameStub game) {super.setGame(game);}
	*/
}
