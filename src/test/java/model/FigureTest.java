package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import stubs.BoardStub;
import stubs.FigureStub;

/**
 * Test the methods from the Figure class. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class FigureTest {
	
	/**
	 * Test the location of the figures with the search method.
	 */
	@Test
	void testFantasticFiguresAndWhereToFindThem() {
		BoardStub board = new BoardStub();
		Tile[][] tiles = board.getTiles();
		board.createPawns(1, 1, true);
		board.createRooks(0, 0, true);
		Figure fig = tiles[1][1].getFigure();
		assertTrue(fig.searchFigure(tiles, 'P', 'R'));
		assertFalse(fig.searchFigure(tiles, 'p', 'r'));
		assertFalse(fig.searchFigure(tiles, 'P', 'P'));
		board.createBishops(2, 3, true);
		board.createBishops(2, 4, true);
		assertFalse(fig.searchFigure(tiles, 'B', 'B'));
		assertTrue(fig.searchFigure(tiles, 'B', 'z'));
	}
	
	/**
	 * Test the move Possibilities of a pawn in the super class figure.
	 */
	@Test
	void testCallTheMovementStrings() {
		Figure fig = new FigureStub(0, 0, false, 'P');
		Board board = new BoardStub();
		String[][] test = fig.move(board.getTiles(), true);
		assertEquals("false",test[0][0]);
	}
	
	/**
	 * Test the color of the field where the bishop can move on.
	 */
	@Test
	void testBishopWantsAFieldColorForDraw() {
		Figure bishop = new Bishop(false,0,0); 
		bishop = (Bishop) bishop;
		assertFalse(bishop.isFieldColor());
		bishop.setFieldColor(true);
		assertTrue(bishop.isFieldColor());
	}
	
	/**
	 * Test the firstMove boolean for bishops.
	 */
	@Test
	void testCheckForBishopFirstMove() {
		Figure bishop = new Bishop(false,0,0); 
		bishop = (Bishop) bishop;
		assertTrue(bishop.isFirstMove());
		bishop.setFirstMove(false);
		assertFalse(bishop.isFirstMove());
	}
	
	/**
	 * Test the firstMove boolean for knights.
	 */
	@Test
	void testCheckForKnightFirstMove() {
		Figure knight = new Knight(false,0,0); 
		knight = (Knight) knight;
		assertTrue(knight.isFirstMove());
		knight.setFirstMove(false);
		assertFalse(knight.isFirstMove());
	}
	
	/**
	 * Test the firstMove boolean for kings.
	 */
	@Test
	void testCheckForKingFirstMove() {
		Figure king = new King(false,0,0); 
		king = (King) king;
		assertTrue(king.isFirstMove());
		king.setFirstMove(false);
		assertFalse(king.isFirstMove());
	}
	
	/**
	 * Test the firstMove boolean for rooks.
	 */
	@Test
	void testCheckForRookFirstMove() {
		Figure rook = new Rook(false,0,0); 
		rook = (Rook) rook;
		assertTrue(rook.isFirstMove());
		rook.setFirstMove(false);
		assertFalse(rook.isFirstMove());
	}

	/**
	 * Test the firstMove boolean for queens.
	 */
	@Test
	void testCheckForQueenFirstMove() {
		Figure queen = new Queen(false,0,0); 
		queen = (Queen) queen;
		assertTrue(queen.isFirstMove());
		queen.setFirstMove(false);
		assertFalse(queen.isFirstMove());
	}
	
	/**
	 * Test the firstMove boolean for pawns.
	 */
	@Test
	void testCheckForFirstMove() {
		Figure fig = new FigureStub(0, 0, false, 'P');
		assertFalse(fig.isFirstMove());
		fig.setFirstMove(true);
		assertTrue(fig.isFirstMove());
	}
	
	/**
	 * Test the beaten boolean for figures.
	 */
	@Test
	void testBishopCanBeCreated() {
		Figure fig = new FigureStub(0,0,true,' ');
		assertTrue(fig.isColor());
		assertFalse(fig.isBeaten());
		fig.setBeaten(true);
		assertTrue(fig.isBeaten());
	}
	
	/**
	 * Test the firstMove boolean for figures.
	 */
	@Test
	void testFigureStubDummyCheck() {
		Figure fig = new FigureStub(0,0,true,' ');
		assertFalse(fig.isFirstMove());
		fig.setFirstMove(true);
		assertTrue(fig.isFirstMove());
		fig.setFirstMove(false);
		assertFalse(fig.isFirstMove());
	}
	
	/**
	 * Test the return value if no king is on the field.
	 */
	@Test
	void testKingPosition() {
		Tile[][] tiles = new Tile[8][8];
		for(int l=0;l<8;l++) {
			for(int c=0;c<8;c++) {
				tiles[l][c] = null;
			}
		}
		assertNull(Pawn.getKingPos(tiles, true));
	}

}
