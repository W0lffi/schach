package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import stubs.BoardStub;

/**
 * JUnit Test Class to test for the figure pawn.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class PawnTest {

	BoardStub board = new BoardStub(); 
	Tile[][] tiles = board.getTiles();
	
	/**
	 * Test for the correct change of a pawn into a queen, rook, knight or bishop.
	 */
	@Test
	void testTheMetamorphosisOfPawns() {
		tiles[0][1] = new Tile(new Pawn(true,0,1));
		Pawn.transformation(tiles, 0, 1, 'Q');
		assertEquals(Queen.class, tiles[0][1].getFigure().getClass());
		Pawn.transformation(tiles, 0, 1, 'R');
		assertEquals(Rook.class, tiles[0][1].getFigure().getClass());
		Pawn.transformation(tiles, 0, 1, 'N');
		assertEquals(Knight.class, tiles[0][1].getFigure().getClass());
		Pawn.transformation(tiles, 0, 1, 'B');
		assertEquals(Bishop.class, tiles[0][1].getFigure().getClass());
	}
	
	/**
	 * Tests if a pawn is correctly set true and false for hit en passant.
	 */
	@Test
	void testPawnHitsEnPassant() {
		Figure pawn = new Pawn(true,0,0);
		pawn = (Pawn) pawn;
		pawn.setHitEnPassantLeft(true);
		assertTrue(pawn.isHitEnPassantLeft());
		pawn.setHitEnPassantRight(false);
		assertFalse(pawn.isHitEnPassantRight());
	}
	
	/**
	 * Tests if a pawn has a first move and is correctly set false if method is called.
	 */
	@Test
	void testPawnHasAFirstMove() {
		Figure pawn = new Pawn(true,0,0);
		assertTrue(pawn.isFirstMove());
		pawn.setFirstMove(false);
		assertFalse(pawn.isFirstMove());
	}
}
