package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import schach.Constants;

/**
 * An interface for the AI.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public abstract class Intelligence {

	protected List<Board> nodes;
	
	protected List<String> moves;
	protected List<Double> values;
	protected List<List<Integer>> adjacencyList;
	
	protected int[][] adjacency;
	protected int depth = 3;
	protected boolean color;
	protected boolean interrupt;
	
	private boolean firstMove;
	
	/**
	 * Super constructor for any intelligence inherited instance
	 */
	public Intelligence(){
		interrupt = false;
		adjacencyList = new ArrayList<List<Integer>>();
		nodes = new ArrayList<Board>();
		moves = new ArrayList<String>();
		values = new ArrayList<Double>();
	}
	
	/**
	 * Calculates the answer of the AI for draw.
	 * @param game The current game.
	 * @return The answer.
	 */
	public boolean drawRequest(Game game) {
		int myDeadFigs = game.getCemetery().countFigsByColor(color);
		int enemyDeadFigs = game.getCemetery().countFigsByColor(!color);
		return myDeadFigs - 4 > enemyDeadFigs;
	}
	
	/**
	 * This method clears the board array list and move array list for the calculation of the movement
	 */
	protected void setUpMovementCalculation() {
		values.clear();
		nodes.clear();
		//nodesString.clear();
		moves.clear();
		adjacencyList.clear();
	}
	
	/**
	 * This method returns the move calculated by either an intelligent or a simple AI
	 * @param game The current game.
	 * @return A string with the move.
	 */
	public abstract String makeMove(Game game);
	
	/**
	 * This method generates a Graph for movement decision.
	 * @param node The current board of the game.
	 * @param depth The depth of move forecast.
	 * @param parent The position of the parent node to look at
	 * @param color The Player to look at for the current forecast.
	 */
	protected void generateGraph(Board node, int depth, int parent, boolean color){
		if(depth == 0) {
			this.nodes.add(node);
			this.moves.add("start");
		}
		if(depth == this.depth) {
			List<Integer> tempInnerForAddition = new ArrayList<Integer>();
			this.adjacencyList.add(parent,tempInnerForAddition);
			return;
		}
		else{
			Tile[][] tiles = node.getTiles();
			List<Integer> tempInner = new ArrayList<Integer>();
			this.adjacencyList.add(parent,tempInner);
			for(int c=0; c < Constants.BOARD_DIMENSIONS; c++) {
				for(int l = 0; l < Constants.BOARD_DIMENSIONS; l ++) {
					// We found a figure
					if (this.foundFigure(tiles[l][c], color)) {
						Figure fig = tiles[l][c].getFigure();
						// Check all movements of this figure
						node.clearMovePos();
						String[][] movementsCheck = fig.move(tiles, true);
						node.setMovePos(movementsCheck);
						// Hover through movementsCheck to find spots to move
						for (int cm = 0; cm < Constants.BOARD_DIMENSIONS; cm++) {
							for(int lm = 0; lm < Constants.BOARD_DIMENSIONS; lm++) {
								if(this.foundTileToMove(movementsCheck[lm][cm])) {
									// Create a temporal board object to add
									Board tempSetting = new Board();
									// Copy all tiles and figures
									Tile[][] tempTiles = Tile.copyTheTiles(tiles);
									tempTiles[lm][cm] = new Tile(fig.getName(), lm, cm);
									tempTiles[l][c] = null;
									tempSetting.setTiles(tempTiles);
									this.nodes.add(tempSetting);
									Position tempPos = new Position(-1,-1);
									// calculate the move as a string for input
									this.moves.add(this.nodes.indexOf(tempSetting),tempPos.charToValue(c)+Integer.toString(Constants.BOARD_DIMENSIONS-l)+"-"+ tempPos.charToValue(cm)+Integer.toString(Constants.BOARD_DIMENSIONS-lm));									
									tempInner = this.adjacencyList.get(parent);
									tempInner.add(this.nodes.indexOf(tempSetting));
									this.adjacencyList.remove(parent);
									this.adjacencyList.add(parent,tempInner);
									generateGraph(tempSetting, depth+1, this.nodes.indexOf(tempSetting), !color);
								}	
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * This function calculates the score of a game field setup by a heuristic function:
	 * Calculate the sum over the ratio between my and opponent's figures, and the Manhattan 
	 * distance between all own figures and the opponent's king, as well between my king and the opponent's figures
	 * @param node The current state to calculate win chances.
	 * @param weights The weights of each heuristic value. 
	 * @return a heuristic value for this state.
	 */
	protected double calculateValueOfNode(Board node, int[] weights) {
		Tile[][] tiles = node.getTiles();
		int myFigures = 0;
		int enemyFigures = 0;
		double distance = 0;
		double distanceEnemyToMyKing = 0;
		double kingHasNoChanceToEscape = 0;
		double noQueen = -1;
		double pawnTransformation = 0;
		
		Figure king = Figure.getKingPos(tiles, this.isColor());
		Figure enemyKing = Figure.getKingPos(tiles, !this.isColor());

		// We need our figure count and the distance to the enemy's king, as well the enemy's figure count
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {		
				if(tiles[l][c] != null) {
				// Get my Own Figures
					if(tiles[l][c].getFigure().isColor() == this.color) {
						myFigures+= countAndValueFigures(tiles[l][c].getFigure());
						distance += tiles[l][c].getFigure().getPosition().distanceToKing(king, node);
					}else {
					// Get figures of my enemy
						enemyFigures+= countAndValueFigures(tiles[l][c].getFigure());
						distanceEnemyToMyKing += tiles[l][c].getFigure().getPosition().distanceToKing(enemyKing, node);
					}
					// Get whether king will be in check
					if(!kingHasEscape(tiles[l][c].getFigure(), tiles)) {
						kingHasNoChanceToEscape = 1;
					}
					// If we ever find our queen, we will ignore this
					if(weHaveAQueen(tiles[l][c].getFigure())) {
						noQueen = 0;
					}
					// Get whether pawn can be transformed into queen
					if(tiles[l][c].getFigure().getClass().equals(Pawn.class) && tiles[l][c].getFigure().isColor() == this.color) {
						pawnTransformation += distanceToEnd(tiles[l][c].getFigure());
					}
				}
			}
		}
		double evaluate = weights[0]/(distance+1) + (distanceEnemyToMyKing)*((double)1/weights[1]) + (myFigures+1)*weights[2] + weights[3]/(enemyFigures+1) + (kingHasNoChanceToEscape+1)*weights[4] + weights[5]/(pawnTransformation+1) + noQueen*100;
		return evaluate;
	}
	
	private double countAndValueFigures(Figure fig) {
		switch(Character.toUpperCase(fig.getName())) {
		case 'P':
			return 1;
		case 'K':
			return 10;
		case 'Q':
			return 8;
		case 'R':
			return 5;
		default:
			return 3; // Bishop and Knight
		}
	}
	
	/**
	 * Method to estimate the distance of a pawn to the other side of the board for transformation
	 * @param fig The pawn figure of the AI
	 * @return A distance value used in heuristic
	 */
	private double distanceToEnd(Figure fig) {
		double distance = Double.MIN_VALUE;
		if(Character.isUpperCase(fig.getName())) {
			distance = fig.getPosition().getLine() + 1; 
		}
		else {
			distance = Constants.BOARD_DIMENSIONS - (fig.getPosition().getLine())
;		}
		// if pawn is at the bottom, it will transform into a queen, which is a high value figure
		if(distance == 1) {
			return 1/1000;
		}
		return distance;
	}
	
	/**
	 * Method to check if king has any possibility to escape his death.
	 * @param king The king figure
	 * @param tiles The tiles of the board to look at
	 * @return True if the king still can move, else false
	 */
	private boolean kingHasEscape(Figure fig, Tile[][] tiles) {
		if(fig.isColor() == this.color || !fig.getClass().equals(King.class)) {
			return false;
		}
		String[][] kingMoves = fig.move(tiles, true);
		int count = 0;
		for(int l=0;l<Constants.BOARD_DIMENSIONS;l++) {
			for(int c=0;c<Constants.BOARD_DIMENSIONS;c++) {	
				if(kingMoves[l][c] == Constants.HAS_MOVE || kingMoves[l][c] == Constants.HAS_ENEMY) {
					count++;
				}
			}
		}
		return count > 0;
	}
	
	/**
	 * Method to evaluate if we have a queen on the field, who is apparently a valuable figure.
	 * @param fig A figure from the field.
	 * @return True if this is the AI's queen, else false.
	 */
	private boolean weHaveAQueen(Figure fig) {
		return fig.getClass().equals(Queen.class) && fig.isColor() == this.color;
	}
	
	// Save Check 
	
	/**
	 * Calculates the win values of each possible turn, sorts them for max values and chooses a max score turn.
	 * @param weights The weights of each part of the heuristic. 
	 * @return The index for the moves ArrayList with the highest number
	 */
	public int getNodeValues(int[] weights) {
		// For each possible setup, calculate a respective value
		
		for (Board b : nodes) {
			if(nodes.indexOf(b) == 0) {
				values.add(Double.MIN_VALUE);
			}else {
				values.add(calculateValueOfNode(b,weights));
			}
		}
		// Find max value and every turn which results into this value
		double maxValueOfList = Collections.max(values);
		return values.indexOf(maxValueOfList);
	}
	
	/**
	 * Method to check whether there is a figure and if the figure has the correct color.
	 * @param tile The current tile to look at.
	 * @param color The color of desire for the check.
	 * @return True if there is a figure of color, else false.
	 */
	protected boolean foundFigure(Tile tile, boolean color) {
		return tile != null && tile.getFigure().isColor() == color && !interrupt;
	}
	
	/**
	 * Method to check whether there is a spot to move by checking for "true" or "enemy"
	 * @param movement The String with information about movement.
	 * @return True if the String contains "true" or "enemy", else false.
	 */
	protected boolean foundTileToMove(String movement) {
		return (movement.equals(Constants.HAS_MOVE) || movement.equals(Constants.HAS_ENEMY)) && !interrupt;
	}
	
	/**
	 * Is changing the first move boolean when the first move was set.
	 */
	public void firstMoveWasSet() {this.firstMove = false;}
	
	//Setter and getter
	
	/**
	 * Getter for thinking.
	 * @return Thinking boolean.
	 */
	public abstract boolean isThinking();
	
	/**
	 * Setter for thinking.
	 * @param thinking The boolean.
	 */
	public abstract void setThinking(boolean thinking);
	
	/**
	 * Select the grade of intelligence
	 * @param depth The depth for forecast
	 */
	public void setDepthForAI(int depth) {this.depth = depth;}
	
	/**
	 * Getter for the depth of advanced AI
	 * @return The depth of the advanced AI for forecasting moves.
	 */
	public int getDepthForAI() {return this.depth;}
	
	/**
	 * Setter for the first move of the AI in a game. Is used once in a game when AI and game are created.
	 */
	public void setFirstMove() {this.firstMove = true;}
	
	/**
	 * Getter for the first move boolean of the AI in a game.
	 * @return A boolean value if the AI performed its first move.
	 */
	public boolean isFirstMove() {return this.firstMove;}
	
	/**
	 * Setter for the color of the computer opponent
	 * @param color The color of the opponent 
	 */
	public void setColor(boolean color) {this.color = color;}
	
	/**
	 * Getter for the color of the computer opponent
	 * @return False for black and true for white figures.
	 */
	public boolean isColor() {return this.color;}
	
	/**
	 * Setter if an interrupt has to occur.
	 * @param interrupt Boolean value if interrupt occured during thinking of AI.
	 */
	public void setInterrupt(boolean interrupt) {this.interrupt = interrupt;}
	
	/**
	 * Getter if an interrupt occurred.
	 * @return True if the AI has to interrupt its calculations else false.
	 */
	public boolean isInterrupt() {return interrupt;}
	
	/**
	 * The getter of the boardList for JUnit testing.
	 * @return The list of nodes.
	 */
	public List<Board> getBoardList(){return this.nodes;}
	
	/**
	 * The getter of the list of movement strings for each element of node used in JUnit testing.
	 * @return The list of movements.
	 */
	public List<String> getMovementList(){return this.moves;}
	
	/**
	 * The getter of the value list of each node used in JUnit testing.
	 * @return The list of node values calculated by the heuristic.
	 */
	public List<Double> getValueList(){return this.values;}
}