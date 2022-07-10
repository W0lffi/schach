package model;

/**
 * This class present an AI-player as as second player.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Computer extends Player{

	private boolean color;
	private Intelligence intelligence;
	
	/**
	 * The default constructor which needs information about AI
	 * @param intelligence 1 for SimpleAI, else advanced AI.
	 */
	public Computer(int intelligence){
		this.intelligence = intelligence==1 ? new SimpleAI() : new AI();
		if(intelligence == 3) {
			this.intelligence.setDepthForAI(5);
		}
		this.intelligence.setFirstMove();
	}
	
	@Override
	public boolean isColor() {return color;}

	@Override
	public void setColor(boolean color) {
		this.color = color;
		this.intelligence.setColor(this.color);
	}
	
	@Override
	public Intelligence getIntelligence() {
		return this.intelligence;
	}

}
