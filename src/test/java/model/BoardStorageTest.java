package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This a JUnit test class for the testing of BoardStorage.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class BoardStorageTest {
	
	/**
	 * This is a testing method for the basic functions of BoardStorage.
	 */
	@Test
	void testBoardStorageAdditionOfBoardsToList() {
		Board board = new Board();
		Game game = new Game();
		Cemetery cemetery = game.getCemetery();
		cemetery.setBeaten(new Pawn(true,1,1));
		game.setCemetery(cemetery);
		board.setGame(game);
		
		BoardStorage bs = board.getBoards();
		String addition = "rnbqkbnrppppppp---------------------------------PPPPPPPPRNBQKBNR";
		Board additionBoard = bs.stringToBoard(addition);
		additionBoard.setGame(game);
		bs.boardToString(additionBoard, true);
		addition = "rnbqkbnrpppppp----------------------------------PPPPPPPPRNBQKBNR";
		additionBoard = bs.stringToBoard(addition);
		additionBoard.setGame(game);
		bs.boardToString(additionBoard, true);
		assertEquals("rnbqkbnrppppppp---------------------------------PPPPPPPPRNBQKBNR",bs.getBoardsPartBefore());
		assertEquals("P",bs.getTombsBefore());
		assertEquals("rnbqkbnrpppppp----------------------------------PPPPPPPPRNBQKBNR", bs.getBoardsPartAfter());
		bs.getBoardsPartBefore();
		bs.updateBoardCount();
		assertEquals("",bs.getBoardsPartAfter());
		assertEquals("P",bs.getTombsAfter());
	}
	
	/**
	 * This method tests the clearance function of the board storage.
	 */
	@Test
	void testForClearance() {
		Board board = new Board();
		Game game = new Game();
		Cemetery cemetery = game.getCemetery();
		cemetery.setBeaten(new Pawn(true,1,1));
		game.setCemetery(cemetery);
		board.setGame(game);
		BoardStorage bs = board.getBoards();
		String addition = "rnbqkbnrppppppp---------------------------------PPPPPPPPRNBQKBNR";
		Board additionBoard = bs.stringToBoard(addition);
		additionBoard.setGame(game);
		bs.boardToString(additionBoard, true);
		assertEquals(1,bs.getBoards().size());
		bs.clear();
		assertEquals(0,bs.getBoards().size());
	}
	
	@Test 
	void testForTilesConversion() {
		Board board = new Board();
		Tile[][] tiles = board.getTiles();
		assertEquals("rnbqkbnrpppppppp--------------------------------PPPPPPPPRNBQKBNR",BoardStorage.tilesToString(tiles));
	}

}
