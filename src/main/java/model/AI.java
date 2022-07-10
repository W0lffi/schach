package model;

import java.util.ArrayList;
import java.util.List;

import schach.Constants;

/**
 * This class represents the AI which a player can play against.
 * 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class AI extends Intelligence{

	private int[] weights;
	private int count;
	private int figCount;
	
	private boolean thinking;
	
	/**
	 * Constructor for an AI, needs Intelligence
	 */
	public AI () {
		super();
		super.firstMoveWasSet();
		thinking = false;
	}
	
	/**
	 * Method for the management of the movement call.
	 * @param game The current game object.
	 * @return A movement string in the form of a typical console user input, or an interrupt string if the user performed an undo/redo during thinking.
	 */
	@Override
	public String makeMove(Game game) {
		//long start = System.currentTimeMillis();
		thinking = true;
		if(super.depth > 3 && game.getViews().get(0).getClass().equals(view.Console.class)) {
			System.out.println(Constants.WARNING_DEPTH[game.getLanguage()]);
		}
		String move = prepareMove(game);
		communicateWithPlayer(game);
		if(super.isInterrupt()) {
			super.setInterrupt(false);
			thinking = false;
			return "Interrupt";
		}else {
			thinking = false;
			//long end = System.currentTimeMillis();
			//System.out.println((end - start)/1000.0/60.0);
			return move;
		}
	} 
	
	/**
	 * This method calls the preparation methods to generate a movement string.
	 * @param game The current game object.
	 * @return A movement string in the form of a typical console user input.
	 */
	private String prepareMove(Game game) {
		this.count = 0;
		this.figCount = 0;
		this.weights = setWeights(game);
		super.setUpMovementCalculation();
		communicateWithPlayer(game);
		int tempDepth = super.depth;
		if(figCount > 8) {
			super.depth = 3;
		}
		String move = "";
		if(!super.isInterrupt()) {
			super.generateGraph(game.getBoard(),0,0, super.color);
			communicateWithPlayer(game);
			move = makeTheSearch();
		}else {
			move = "Interrupt";
		}
		super.depth = tempDepth;
		return move;
	}
	
	/**
	 * Generates the movement by calling the min/max search
	 * @return A movement string
	 */
	private String makeTheSearch() {
		List<Integer> nodeIDs = super.adjacencyList.get(0);
		int idx = 0;
		List<Board> possibleFirstMoves = new ArrayList<Board>();
		possibleFirstMoves = getChildrenOfNode(0);
		double evaluateBest = Double.MIN_VALUE;
		String move = null;
		for(Board b: possibleFirstMoves) {
			double tempValue = minMaxSearch(b, 1, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
			if(evaluateBest < tempValue) {
				evaluateBest = tempValue;
				move = super.moves.get(nodeIDs.get(idx));
				//System.out.println("Move has " + move + " for the value " + tempValue + " for the boad " + b + " and ID " + nodeIDs.get(idx));
				idx++;
			}else {
					idx++;
				
			}
		}
		return move;
	}
	
	/**
	 * This methods calculates the best next position by using min max search with alpha beta pruning
	 * @param node The first node
	 * @param depth The current depth within the graph
	 * @param maxPlayer The layer of the current player
	 * @param alpha The alpha value the node player wants to max
	 * @param beta The beta value the opponent wants to max
	 * @return the best playing value for the KI to chose
	 */
	@SuppressWarnings({ "PMD.ExcessiveParameterList", "PMD.AvoidReassigningParameters" }) // The algorithm needs these five parameters.
	private double minMaxSearch(Board node, int depth, boolean maxPlayer, double alpha, double beta) {
		if (depth == super.getDepthForAI()) {
			return super.calculateValueOfNode(node, this.weights);
		}
		if(maxPlayer) {
			double best = Double.MIN_VALUE;
			List<Board> children = new ArrayList<Board>();
			children = getChildrenOfNode(this.nodes.indexOf(node));
			for(Board b: children) {
				double value = minMaxSearch(b, depth+1, false, alpha, beta);
				best = Math.max(best, value);
				alpha = Math.max(alpha, best);
				if(beta <= alpha) {
					break;
				} 
			}
			return best;
		}
		else {
			double best = Double.MAX_VALUE;
			List<Board> children = new ArrayList<Board>();
			children = getChildrenOfNode(this.nodes.indexOf(node));
			for(Board b: children) {
				double value = minMaxSearch(b, depth+1, true, alpha, beta);
				best = Math.min(best, value);
				beta = Math.min(beta, best);
				if(beta <= alpha) {
					break;
				}
			}
			return best;	
		}
	}
	
	/**
	 * This function gets the children of a specific node by using the adjacency matrix.
	 * @param positionOfNode The current position of the node within the ArrayList nodes to get the line of the adjacency matrix.
	 * @return An array list of a all children nodes of the parent node.
	 */
	protected List<Board> getChildrenOfNode(int positionOfNode){
		List<Board> list = new ArrayList<Board>();
		for (int index: super.adjacencyList.get(positionOfNode)) {
			list.add(super.nodes.get(index));
		}
		return list;
	}
	
	/**
	 * This method is for the communication of the AI with the player.
	 */
	private void communicateWithPlayer(Game game) {
		if(game.getViews().get(0).getClass().equals(view.Console.class)) {
			if(this.count % 50 == 0) {
				System.out.println();
			}
			if(this.count == 0) {
				System.out.print(Constants.THINKING_AI[game.getLanguage()]);
			}else if(this.count % 2 == 1) {
				System.out.print(".");
			}else {
				System.out.print("..\n");
			}
			this.count++;
		}
	}
	
	/**
	 * Method to adjust the weights of each part for the value of board function.
	 * @param game The current game. 
	 * @return A vector with weights for each evaluation factor.
	 */
	private int[] setWeights(Game game) {
		
		String boardString = BoardStorage.tilesToString(game.getBoard().getTiles());
		this.figCount = boardString.length();
		int[] weights;
		for(int i = 0; i < boardString.length(); i++) {
			if(boardString.charAt(i) == '-' || !super.isColor() && Character.isUpperCase(boardString.charAt(i))) {
				figCount = figCount - 1;
			}else if(boardString.charAt(i) == '-' || super.isColor() && !Character.isUpperCase(boardString.charAt(i))) {
				figCount = figCount - 1;
			}
		}
		if(figCount >= 4) {
			// We want to max the score:
			// Distance my fig to enemy king (0) should be small -> 1/count: 2 better than 4, but 1/2 > 1/4
			// Distance enemy fig to my king (1) should be large -> count/1: 4 better than 2
			// Count my figs (2) should be large -> count/1: 4 better than 2
			// Count figs of enemy (3) should be small ->  1/count: n better than n+1, but 1/n > 1/(n+1)
			// Enemy king cannot move anymore (4) should be there
			// Distance pawns to end to morph (5) should be small -> 1/count: n better than n+1, but 1/n > 1/(n+1), but we have 8 pawns
			//int[] temp = {100, 4, 1000, 10000, 100, 10000};
			int[] temp = {1, 10, 10, 9999, 1000, 100};
			weights = temp;
		}else {
			int[] temp = {100, 10, 1000, 1000, 1, 10000};
			weights = temp;
		}
		return weights;
	}
	
	@Override
	public boolean isThinking() {return thinking;}
	@Override
	public void setThinking(boolean thinking) {this.thinking = thinking;}
}