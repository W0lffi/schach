package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import schach.Constants;
import stubs.BoardStub;

/**
 * Test the methods from the King-movement class. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class KingMovementTest {
	
	BoardStub board = new BoardStub();
	Tile[][] tiles = board.getTiles();
	
	/**
	 * Test whether the king detect enemy's and free fields.
	 */
	@Test
	void testTheKingHasToMove() {
		board.createKings(4, 4, false);
		board.createPawns(4, 5, true);
		String[][] possibilities = tiles[4][4].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_ENEMY, possibilities[4][5]);
		assertEquals(Constants.HAS_MOVE, possibilities[4][3]);
		board.removeMyFigure(4, 5);
		possibilities = tiles[4][4].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_MOVE, possibilities[4][5]);
	}
	
	/**
	 * Test all four castlings. 
	 */
	@Test
	void testCastlingForKing() {
		board.createKings(7, 4, true);
		board.createRooks(7, 0, true);
		board.createRooks(7, 7, true);
		board.createKings(0, 4, false);
		board.createRooks(0, 0, false);
		board.createRooks(0, 7, false);
		String[][] possibilities = tiles[7][4].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_NO_MOVE, possibilities[7][6]);
		assertEquals(Constants.HAS_NO_MOVE, possibilities[7][2]);
		possibilities = tiles[0][4].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_NO_MOVE, possibilities[0][6]);
		assertEquals(Constants.HAS_NO_MOVE, possibilities[0][2]);
	}
}
