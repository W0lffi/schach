package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import schach.Constants;
import stubs.BoardStub;

/**
 * JUnit Test Class for the figure Queen
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class QueenTest {
	
	/**
	 * Tests the method to call the move possibilities of the queen, which is a concatenation of 
	 * rook and bishop. This concat method is also tested. 
	 */
	@Test
	void testQueenMovementWillConcat() {
		Board board = new BoardStub();
		Tile[][] tiles = board.getTiles();
		tiles[0][0] = new Tile(new Queen(true,0,0));
		Queen queen = (Queen) tiles[0][0].getFigure();
		board.clearMovePos();
		String[][] movements = board.getMovePos();
		movements = queen.move(tiles, false);
		assertEquals(Constants.HAS_MOVE,movements[0][1]);
		assertEquals(Constants.HAS_MOVE,movements[0][7]);
		assertEquals(Constants.HAS_MOVE,movements[7][7]);
		assertEquals(Constants.HAS_MOVE,movements[7][0]);
	}

}
