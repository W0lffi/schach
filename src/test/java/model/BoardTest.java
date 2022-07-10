package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import stubs.BoardStub;


/**
 * Tests for board.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class BoardTest {
	
	private BoardStub board = new BoardStub();
	
	/**
	 * This method tests setters for Game and BoardStorage.
	 */
	@Test
	void testForSetter() {
		Board board = new Board();
		Game game = new Game();
		BoardStorage bs = new BoardStorage();
		board.setGame(game);
		board.setBoards(bs);
		assertEquals(game, board.getGame());
		assertEquals(bs, board.getBoards());
	}
	
	/**
	 * Place the figures on the board before each test.
	 */
	@BeforeEach
	void setUpBoardSettingForTest() {
		board.createPawns(6,7, true);
		board.createPawns(6,4, true);
		board.createPawns(5,5, true);
		board.createPawns(4,6, true);
		board.createQueens(4, 7, false);
		board.createRooks(7, 7, true);
		board.createKings(7, 4,true);
		board.createQueens(7, 3,true);
		board.createKings(0, 4, false);
		board.createKnights(7, 6, true);
		board.createBishops(7, 5, true);
	}
	
	/**
	 * Test whether the figures standing on the expected fields.
	 */
	@Test
	void testIfFiguresPlacedAsExpected() {
		Tile[][] testMyTiles = board.getTiles();
		assertEquals(testMyTiles[6][7].getFigure().getClass(), Pawn.class);
		assertEquals(testMyTiles[0][4].getFigure().getClass(), King.class);
		assertEquals(testMyTiles[7][6].getFigure().getClass(), Knight.class);
		assertEquals(testMyTiles[7][5].getFigure().getClass(), Bishop.class);
	}
	
	/**
	 * Test whether the color is right.
	 */
	@Test
	void testIfInitIsWorking() {
		String[][] testMove = board.getMovePos();
		Tile[][] testMyTiles = board.getTiles();
		assertEquals(testMyTiles[7][7].getFigure().getClass(), Rook.class);
		assertEquals("false", testMove[7][7]);
		assertTrue(testMyTiles[7][7].getFigure().isColor());
		assertFalse(testMyTiles[0][4].getFigure().isColor());
	}
	
	/**
	 * Test whether a tile return null, if no figure is on it.
	 */
	@Test
	void testNoFigureOnTile() {
		Tile tile = new Tile(null);
		assertNull(tile.getFigure());
	}
	
	/**
	 * Testing of a single tile getter of the board.
	 */
	@Test
	void testForASingleTile() {
		Board board = new Board(new Game());
		Tile testTile = board.getSingleTile(0, 0);
		assertEquals(Rook.class, testTile.getFigure().getClass());
		assertEquals(null,board.getSingleTile(5, 5));
	}
	
	/**
	 * Testing to create a single new figure on a single tile of the board.
	 */
	@Test
	void testForSingleFigure() {
		Board board = new Board(new Game());
		Tile testTile = board.createSingleFigure(new Position(5, 5), 'p');
		assertEquals(Pawn.class, testTile.getFigure().getClass());
		assertFalse(testTile.getFigure().isColor());
	}
	
	/**
	 * This method tests if a single tile with a figure is copied correctly.
	 */
	@Test
	void testForSingleTile() {
		Tile tile = new Tile('H',2,2);
		assertEquals(null, tile.copyMe(2, 2));
	}
	
	/**
	 * This method tests the getter and setter for the last moved figure on the board.
	 */
	@Test
	void testCorrectSetterAndGetterForLastMovedFigure() {
		Board board = new Board(new Game());
		board.setLastMovedFigure(new Pawn(true,1,1));
		assertEquals(board.getLastMovedFigure().getClass(),Pawn.class);
		board.setLastMovedFigure(new Rook(false,7,7));
		assertEquals(board.getLastMovedFigure().getClass(),Rook.class);
	}
	
	/**
	 * Test for the correct conversion of a board into a String
	 */
	@Test
	void testBoardIsTranslatedIntoString() {
		Board board = new Board(new Game());
		String temp = board.getBoards().boardToString(board, false);
		assertEquals("rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR",temp);
	}
	
	/**
	 * Test for correct conversion of a String to a board
	 */
	@Test
	void testStringIsTranslatedIntoBoard() {
		String boardString = "rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR";
		Board board = new Board();
		board = board.getBoards().stringToBoard(boardString);
		assertEquals(Rook.class,board.getTiles()[0][0].getFigure().getClass());
	}
	
	/**
	 * Test the isLastMoveByHuman method
	 */
	@Test
	void testIsLastMoveByHuman() {
		Game game = new Game();
		game.setPlayer2(0, -1);
		game.getPlayer1().setColor(false);
		assertTrue(game.getBoard().isLastMoveByHuman());
		game.getPlayer1().setColor(true);
		assertFalse(game.getBoard().isLastMoveByHuman());
		game.setPlayer2(1, 0);
		game.getBoard().getBoards().setBoards("");
		assertFalse(game.getBoard().isLastMoveByHuman());
		game.getBoard().getBoards().setBoardCount(-1);
		assertFalse(game.getBoard().isLastMoveByHuman());
	}
}
