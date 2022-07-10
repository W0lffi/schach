package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import schach.Constants;
import stubs.BoardStub;

/**
 * Test the methods from the knight, bishop and rook class. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class MovementTest {
	
	BoardStub board = new BoardStub();
	Tile[][] tiles = board.getTiles();
	Movement figureMovement;
	
	/**
	 * Create some figures before each test.
	 */
	@BeforeEach
	void weNeedAKingAndAnArmy() {
		board.createKings(4, 4, false);
		board.createBishops(3,5, false);
		board.createBishops(1,7, true);
		board.createRooks(1, 2, false);
		board.createRooks(1, 0, true);
		board.createKnights(4, 2, false);
		board.clearMovePos();
	}
	
	/**
	 * Test the rook enemy and free field detection, without looking if the king would be in check after it.
	 */
	@Test
	void testForRookMovements() {
		figureMovement = new RookMovement();
		String[][] positions = figureMovement.move(tiles, tiles[1][2].getFigure(), false);
		assertEquals(Constants.HAS_ENEMY, positions[1][7]);
		assertEquals(Constants.HAS_ENEMY, positions[1][0]);
		assertEquals(Constants.HAS_MOVE, positions[3][2]);
		assertEquals(Constants.HAS_MOVE, positions[0][2]);
		assertEquals(Constants.HAS_NO_MOVE, positions[6][4]);
	}
	
	/**
	 * Test the rook enemy and free field detection, with looking if the king would be in check after it and if he would deny the specific moves.
	 */
	@Test
	void testForRookMovementsWithAKing() {
		figureMovement = new RookMovement();
		String[][] positions = figureMovement.move(tiles, tiles[1][2].getFigure(), true);
		assertEquals(Constants.HAS_ENEMY, positions[1][7]);
		assertEquals(Constants.HAS_ENEMY, positions[1][0]);
		assertEquals(Constants.HAS_MOVE, positions[3][2]);
		//assertEquals(Constants.HAS_MOVE, positions[0][2]);
		board.createPawns(0, 2, false);
		positions = figureMovement.move(tiles, tiles[1][2].getFigure(), true);
		assertEquals("p", positions[0][2]);
		assertEquals(Constants.HAS_NO_MOVE, positions[6][4]);
	}
	
	/**
	 * Test the bishop enemy and free field detection, without looking if the king would be in check after it.
	 */
	@Test
	void testForBishopMovements() {
		figureMovement = new BishopMovement();
		String[][] positions = figureMovement.move(tiles, tiles[3][5].getFigure(), false);
		assertEquals(Constants.HAS_MOVE, positions[0][2]);
		assertEquals(Constants.HAS_ENEMY, positions[1][7]);
		assertEquals(Constants.HAS_MOVE, positions[5][7]);
		assertEquals(Constants.HAS_NO_MOVE, positions[7][1]);
		assertEquals("k", positions[4][4]);
	}
	
	/**
	 * Test the bishop enemy and free field detection, with looking if the king would be in check after it and if he would deny the specific moves.
	 */
	@Test
	void testForBishopMovementsWithAKing() {
		figureMovement = new BishopMovement();
		String[][] positions = figureMovement.move(tiles, tiles[3][5].getFigure(), true);
		assertEquals(Constants.HAS_NO_MOVE, positions[0][2]);
		assertEquals(Constants.HAS_ENEMY, positions[1][7]);
		assertEquals(Constants.HAS_NO_MOVE, positions[5][7]);
		//assertEquals(Constants.HAS_NO_MOVE, positions[7][1]);
		//assertEquals(Constants.HAS_NO_MOVE, positions[6][4]);
		board.removeMyFigure(1, 7);
		board.clearMovePos();
		positions = figureMovement.move(tiles, tiles[3][5].getFigure(), true);
		assertEquals(Constants.HAS_MOVE, positions[0][2]);
		//assertEquals(Constants.HAS_MOVE, positions[1][7]);
		//assertEquals(Constants.HAS_MOVE, positions[5][7]);
		assertEquals(Constants.HAS_NO_MOVE, positions[7][1]);
	}
	
	/**
	 * Test the knight enemy and free field detection without looking if the king would be in check after it. Only moves are tested.
	 */
	@Test
	void testForKnightMovementsNoCheckMove() {
		figureMovement = new KnightMovement();
		String[][] positions = tiles[4][2].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_MOVE, positions[3][0]);
		assertEquals(Constants.HAS_MOVE, positions[5][0]);
		assertEquals(Constants.HAS_MOVE, positions[6][1]);
		assertEquals(Constants.HAS_MOVE,positions[6][3]);
		assertEquals(Constants.HAS_MOVE,positions[2][1]);
	}
	
	/**
	 * Test the knight enemy and free field detection without looking if the king would be in check after it.
	 */
	@Test
	void testForKnightMovementsNoCheckNoMoveOrMove() {
		figureMovement = new KnightMovement();
		String[][] positions = tiles[4][2].getFigure().move(tiles, true);
		assertEquals(Constants.HAS_MOVE,positions[2][3]);
		assertEquals(Constants.HAS_NO_MOVE, positions[6][4]);
		assertEquals(Constants.HAS_NO_MOVE, positions[0][2]);
	}
	
	/**
	 * Test the knight enemy and free field detection with looking if the king would be in check after it for moves.
	 */
	@Test
	void testForKnightMovementsCheckMove() {
		figureMovement = new KnightMovement();
		String[][] positions = tiles[4][2].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_MOVE, positions[3][0]);
		assertEquals(Constants.HAS_MOVE, positions[5][0]);
		assertEquals(Constants.HAS_MOVE, positions[6][1]);
		assertEquals(Constants.HAS_MOVE,positions[6][3]);
		assertEquals(Constants.HAS_MOVE,positions[2][1]);
	}
	
	/**
	 * Test the knight enemy and free field detection with looking if the king would be in check after it for moves and no moves.
	 */
	@Test
	void testForKnightMovementsCheckNoMove() {
		figureMovement = new KnightMovement();
		String[][] positions = tiles[4][2].getFigure().move(tiles, false);
		assertEquals(Constants.HAS_MOVE,positions[2][3]);
		assertEquals(Constants.HAS_NO_MOVE, positions[6][4]);
		assertEquals(Constants.HAS_NO_MOVE, positions[0][2]);
	}
	
	/**
	 * Test whether a figure can the king be in check.
	 */
	@Test
	void testKingIsInDanger() {
		board.createRooks(1, 4, true);
		assertTrue(King.verifyCheckEnemy(tiles, tiles[1][4].getFigure().isColor()));
		board.removeMyFigure(1, 4);
		board.createRooks(1, 5, true);
		assertFalse(King.verifyCheckEnemy(tiles, tiles[1][5].getFigure().isColor()));
	}
	
	/**
	 * Test the copyTheTiles method.
	 */
	@Test
	void testCopyFunctionWorks() {
		Board b = new Board(new Game());
		Tile.copyTheTiles(b.getTiles());
		Tile[][] tiles = b.getTiles();
		assertEquals(Queen.class, tiles[0][3].getFigure().getClass());
	}
	
	/**
	 * Gets the last percent from the rook movement.
	 */
	@Test
	void lastPercentOfRook() {
		BoardStub b = new BoardStub();
		Tile[][] tiles = b.getTiles();
		figureMovement = new RookMovement();
		b.createRooks(4, 4, true);
		b.createPawns(4, 3, true);
		b.createKings(0, 4, false);
		b.createKings(7, 4, true);
		b.clearMovePos();
		b.setMovePos(figureMovement.move(tiles, tiles[4][4].getFigure(), true));
		assertEquals(Constants.HAS_NO_MOVE, b.getMovePos()[4][0]);
		assertEquals(Constants.HAS_MOVE, b.getMovePos()[4][7]);
		assertEquals(Constants.HAS_ENEMY, b.getMovePos()[0][4]);
		assertEquals(Constants.HAS_MOVE, b.getMovePos()[6][4]);
		assertEquals(Constants.HAS_NO_MOVE, b.getMovePos()[3][3]);
	}
}
