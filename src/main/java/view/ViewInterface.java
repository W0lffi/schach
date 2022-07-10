package view;
import model.Game;

/**
 * The interface for the different view which should contain a few methods.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public interface ViewInterface {
	
	/**
	 *  Initializes the view 
	 *  @param game The current game object. 
	 */
	void init(Game game);
	
	/**
	 * Show the current status of the game on the screen.
	 */
	void update();
	
	/**
	 * Updates the position of the figures etc. if a move was reversed or repeated. 
	 */
	void updateBoard();
	
	/**
	 * This method is called by the initialize method and prints the text for it.
	 * @param out The text which will be printed on the console.
	 * @param smaller The upper limit of the options which can be selected.
	 * @return select The selected option of the current menu.
	 */
	int  initConsoleOut(String[] out, int smaller);
	
	/**
	 * Is called when the game is over.
	 * @param content A message for end screen.
	 */
	void gameEnd(String content);
	
	/**
	 * Setter for language.
	 * @param language The language.
	 */
	void setLanguage(int language);
	
	/**
	 * Getter for the game
	 * @return The game object of the game
	 */
	Game getGame();
	
	/**
	 * Getter for the chosen language 
	 * @return A number indicating the chosen game language English or German
	 */
	int getLanguage();

	/**
	 * Getter for the Toggle-Switch.
	 * @return The Toggle-Switch.
	 */
	ToggleSwitch getBtnLanguage();
}
