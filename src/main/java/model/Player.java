package model;

/**
 * The super-class for player creation, human as well as AI. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public abstract class Player {
	
	/**
	 * Getter for the color of the player
	 * @return true for white and false for black
	 */
	public abstract boolean isColor();
	
	/**
	 * Setter for the color of the player
	 * @param color True for white and false for black
	 */
	public abstract void setColor(boolean color);

	/**
	 * Returns the intelligence if exists.
	 * @return THe intelligence.
	 */
	public abstract Intelligence getIntelligence();
	
}
