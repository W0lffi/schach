package model;

/**
 * This class implements a simple AI for chess.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
public class SimpleAI extends Intelligence {
	
	int[] weights;
	boolean thinking;
	
	/**
	 * Constructor for simple AI, needs Intelligence.
	 */
	public SimpleAI() {
		super();
		int[] weights = {100, 4, 10, 9999, 1000, 100};
		this.weights = weights;
		super.setDepthForAI(1);
		thinking = false;
	}
	
	@Override
	public String makeMove(Game game) {
		thinking = true;
		super.setUpMovementCalculation();
		super.generateGraph(game.getBoard(),0,0, super.color);
		
		if(super.isFirstMove()) {
			// Math.random returns value between 0 and 1, without 1. 
			int firstMovesIndexes =  (int) (Math.random() * (super.moves.size()-2) + 1);
			super.firstMoveWasSet();
			return super.moves.get(firstMovesIndexes);
		}
		String move = null;
		if(super.moves.size()>2) {
			move = super.moves.get(super.getNodeValues(this.weights));
		}else if(super.moves.size() == 2) {
			move = super.moves.get(1);
		}else {
			move = "";
		}
		thinking = false;
		return move;
	}
	
	@Override
	public boolean isThinking() {return thinking;}
	@Override
	public void setThinking(boolean thinking) {this.thinking = thinking;}
}