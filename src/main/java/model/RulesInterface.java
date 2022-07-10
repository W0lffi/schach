package model;

/**
 * An interface for the rules of different game modes.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public interface RulesInterface {

	/**
	 * Reverse one move.
	 * @return A boolean value if undo was performed or not.
	 */
	boolean undo();

	/**
	 * Repeat one move, if it's not the current state.
	 * @return A boolean value if redo was performed or not. 
	 */
	boolean redo();
	
	/**
	 * Manage the move of the figure and all after it like check, toggle turn etc.
	 * @param name The letter for the transformation.
	 * @return True if all works correctly.
	 */
	boolean moveTheFigure(char name);
	
	/**
	 * Setter for game.
	 * @param game The current game.
	 */
	void setGame(Game game);
	
	/**
	 * Getter for the figure.
	 * @return The figure to apply the rules to.
	 */
	Figure getFig();
	
	/**
	 * Setter for the figure to apply the rule.
	 * @param fig The current figure chosen by the player.
	 */
	void setFig(Figure fig);
	
	/**
	 * Getter for the language.
	 * @return The language.
	 */
	int getLanguage();
	
	/**
	 * Setter for the game's language.
	 * @param language An integer indicating the chosen language.
	 */
	void setLanguage(int language);
	
	/**
	 * Setter for the line the figure should be moved to.
	 * @param destinationLine A line value.
	 */
	void setDestinationLine(int destinationLine);
	
	/**
	 * Setter for the column the figure should be moved to.
	 * @param destinationColumn A column value.
	 */
	void setDestinationColumn(int destinationColumn);
	
	/**
	 * Setter for the current line the figure is located on.
	 * @param currentLine A line value.
	 */
	void setCurrentLine(int currentLine);
	
	/**
	 * Setter for the current column the figure is located on.
	 * @param currentColumn A column value.
	 */
	void setCurrentColumn(int currentColumn);
	
	/**
	 * Getter for the interrupt of AI due to undo/redo.
	 * @return True if the player pressed on undo or redo, else false and the AI is allowed to move.
	 */
	boolean isInterrupt();
	
	/**
	 * Setter for the interrupt and the AI is not allowed to move anymore.
	 * @param interrupt True if the player pressed undo and redo, else false.
	 */
	void setInterrupt(boolean interrupt);
	
	/**
	 * Call method for JUnit testing of check function.
	 * @return True if king is in check or game is draw.
	 */
	boolean callCheckMate();
}
