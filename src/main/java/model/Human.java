package model;

/**
 * In this Class human players are created.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Human extends Player{
	
	private boolean color;
	
	/**
	 * The constructor creates a new human player.
	 */
	public Human() {
		
	}
	
	// Setters and getters
	@Override
	public void setColor(boolean color) {this.color = color;}
	
	@Override
	public boolean isColor() {return color;}

	@Override
	public Intelligence getIntelligence() {return null;}
}
