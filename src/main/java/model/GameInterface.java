package model;

import view.ViewInterface;

/**
 * This interface provides the game with the following methods.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public interface GameInterface {
	/**
	 * This method attaches a view object to the game.
	 * @param obs The view object.
	 */
	void attach(ViewInterface obs);
	
	/**
	 * This method detaches a view object from the game.
	 * @param obs The view object.
	 */
	void detach(ViewInterface obs);
	
	/**
	 * This method notifies every attached view object about changes.
	 */
	void viewNotify();
}
