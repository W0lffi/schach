package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import schach.Constants;
import stubs.BoardStub;

/**
 * JUnit Test Class for the movement of the figure pawn
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class PawnMovementTest {
	
	/**
	 * Tests for the pawn move possibilities and correct recognition of movements and enemies for black pawns.
	 */
	@Test
	void testPawnMovementInNormalGameplayBlack() {
		BoardStub board = new BoardStub();
		Tile[][] tiles = board.getTiles();
		board.createKings(7, 4, true);
		board.createPawns(6, 4, true);
		board.createPawns(5, 3, false);
		board.createPawns(5, 5, false);
		board.clearMovePos();
		String[][] positions = tiles[6][4].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_MOVE, positions[5][4]);
		assertEquals(Constants.HAS_MOVE, positions[4][4]);
		assertEquals(Constants.HAS_ENEMY, positions[5][3]);
		assertEquals(Constants.HAS_ENEMY, positions[5][5]);
	}

	/**
	 * Tests for the pawn move possibilities and correct recognition of movements and enemies for white pawns.
	 */
	@Test
	void testPawnMovementInNormalGameplayWhite() {
		BoardStub board = new BoardStub();
		Tile[][] tiles = board.getTiles();
		board.createKings(7, 4, true);
		board.createKings(0, 0, false);
		board.createPawns(6, 4, true);
		board.createPawns(5, 3, false);
		board.createPawns(5, 5, false);
		board.clearMovePos();
		String[][] positions = tiles[6][4].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_MOVE, positions[5][4]);
		assertEquals(Constants.HAS_MOVE, positions[4][4]);
		assertEquals(Constants.HAS_ENEMY, positions[5][3]);
		assertEquals(Constants.HAS_ENEMY, positions[5][5]);
	}
	
	/**
	 * Tests for the correct recognition for en passant movements and identification of enemies which hit en passant for black figures.
	 */
	@Test 
	void testPawnHitsAlsoEnPassantBlack() {
		BoardStub board = new BoardStub();
		Tile[][] tiles = board.getTiles();
		board.createKings(0, 0, true);
		board.createKings(0, 4, false);
		board.createPawns(4, 4, true);
		board.createPawns(4, 3, false);
		board.createPawns(4, 5, false);
		tiles[4][3].getFigure().setHitEnPassantRight(true);
		tiles[4][5].getFigure().setHitEnPassantLeft(true);
		String[][] positions = tiles[4][3].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_ENEMY, positions[5][4]);
		positions = tiles[4][3].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_ENEMY, positions[5][4]);
		positions = tiles[4][5].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_ENEMY, positions[5][4]);
		positions = tiles[4][5].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_ENEMY, positions[5][4]);
	}
	
	/**
	 * Tests for the correct recognition for en passant movements and identification of enemies which hit en passant for white figures.
	 */
	@Test
	void testPawnHitsAlsoEnPassantWhite() {
		BoardStub board = new BoardStub();
		Tile[][] tiles = board.getTiles();
		board.createKings(0, 0, false);
		board.createKings(7, 4, true);
		board.createPawns(1, 4, false);
		board.createPawns(3, 3, true);
		board.createPawns(3, 5, true);
		tiles[3][3].getFigure().setHitEnPassantRight(true);
		tiles[3][5].getFigure().setHitEnPassantLeft(true);
		String[][] positions = tiles[3][3].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_ENEMY, positions[2][4]);
		positions = tiles[3][3].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_ENEMY, positions[2][4]);
		positions = tiles[3][5].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_ENEMY, positions[2][4]);
		positions = tiles[3][5].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_ENEMY, positions[2][4]);
	}
}
