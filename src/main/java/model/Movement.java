package model;

/**
 * This interface is for the figureMovements.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public interface Movement {
	/**
	 * Calculates the move possibilities for the given figure.
	 * @param tiles The field with the figures.
	 * @param fig The current figure.
	 * @param checkTest To test if the king is in check
	 * @return The move possibilities.
	 */
	String[][] move(Tile[][] tiles, Figure fig, boolean checkTest);
}
