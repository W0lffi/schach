package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * JUNit Test Class for the position object, which is used in figures to store their position on the board
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class PositionTest {

	/**
	 * Test to check the correctly stored coordinates of the position object. 
	 */
	@Test
	void testPositionHasValues() {
		Position pos = new Position(1,2);
		assertEquals(2, pos.getColumn());
		assertEquals(1, pos.getLine());
		pos.setDestLine(4);
		pos.setDestColumn(3);
		assertEquals(3, pos.getDestColumn());
		assertEquals(4, pos.getDestLine());
		pos.setLine(7);
		pos.setColumn(6);
		assertEquals(6, pos.getColumn());
	}
	
	/**
	 * Tests the distance to the opponent's king.
	 */
	@Test
	void testForThePositionOfKing() {
		Board board = new Board(new Game());
		Tile[][] testTiles = board.getTiles();
		Figure king = Figure.getKingPos(testTiles, true);
		double distance = testTiles[0][0].getFigure().getPosition().distanceToKing(king, board);
		assertEquals(4,distance);
	}
	
	/**
	 * This method tests if a position is within the borders of the board.
	 */
	@Test
	void testIfMyValueIsOnBoard() {
		Position position = new Position(1,1);
		assertFalse(position.isInBorder(1, 9));
		assertTrue(position.isInBorder(7, 7));
	}
	
	/**
	 * This is a testing method to check for correct conversion from a numeric value to a char on the board.
	 */
	@Test
	void testMyCharToValueConversion() {
		Position pos = new Position(1,1);
		assertEquals("?", pos.charToValue(10));
		assertEquals("b", pos.charToValue(1));
	}
	
	/**
	 * Testing method to see if coordinates are on the board. 
	 */
	@Test
	void testIfPositionIsInBorder() {
		Position pos = new Position(1,1);
		assertTrue(pos.isInBorder(0, 0));
		assertFalse(pos.isInBorder(12, 1));
		assertFalse(pos.isInBorder(1, 12));
		assertFalse(pos.isInBorder(-1,-1));
		assertFalse(pos.isInBorder(12,12));
	}
}