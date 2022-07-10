package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import schach.Constants;
import stubs.BoardStub;
import stubs.ConsoleStub;
import stubs.GameStub;
import view.ViewInterface;

/**
 * JUnit Test Class to test the Intelligence of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class IntelligenceTest {
	
	/**
	 * This method tests the prediction of the advanced AI for movements.
	 */
	@Test
	void testCheckTheAdvancedAI() {
		Intelligence computer = new AI();
		Game game = new Game();
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		views.add(new ConsoleStub());
		game.setViews(views);
		String evaluate = computer.makeMove(game);
		assertEquals("e7-e5", evaluate);
		BoardStub board = new BoardStub(game);
		for(int c = 0; c < Constants.BOARD_DIMENSIONS; c++) {
			for(int l = 0; l < Constants.BOARD_DIMENSIONS; l++) {
				board.removeMyFigure(l, c);
			}
		}
		board.createKings(1, 1, true);
		board.createKings(7, 7, false);
		board.createPawns(5, 5, true);
		board.createPawns(4, 4, false);
		board.createPawns(1, 3, true);
		board.createPawns(6, 4, false);
		game.setBoard(board);
		evaluate = computer.makeMove(game);
		assertEquals("e4-f3", evaluate);
	}
	
	/**
	 * This method tests the generation of the first movement string if the AI is the white player.
	 */
	@Test
	void testAIGeneratesAMovementStringIfFirst() {
		Intelligence computer = new SimpleAI();
		computer.setColor(true);
		Game game = new Game();
		String evaluate = computer.makeMove(game);
		assertEquals("e2-e4", evaluate);
	}
	
	/**
	 * This method tests the recognition and generation of an empty move or only one movement left.
	 */
	@Test
	void testAIGeneratesAnEmptyOrOnlyOneMovementString() {
		Intelligence computer = new SimpleAI();
		Game game = new Game();
		BoardStorage bs = new BoardStorage();
		game.setBoard(bs.stringToBoard("----------------------------------------------------------------"));
		assertEquals("", computer.makeMove(game));
		game.setBoard(bs.stringToBoard("kQ------------------------------------------------------------K"));
		game.getBoard().getTiles()[0][0].getFigure().setFirstMove(false);
		assertEquals("a8-b8", computer.makeMove(game));
	}
	
	/**
	 * This method test the correct setting of the forecast depth. Used later for advanced AI.
	 */
	@Test
	void testForTheDepthSetting() {
		Intelligence computer = new SimpleAI();
		Game game = new GameStub();
		game.start();
		computer.setDepthForAI(3);
		assertEquals(3,computer.getDepthForAI());
	}
	
	/**
	 * This method tests the cleaning of each list before movement calculation.
	 */
	@Test
	void testTheSetUp() {
		Intelligence computer = new SimpleAI();
		Game game = new Game();
		assertEquals(0,computer.getBoardList().size());
		assertEquals(0,computer.getMovementList().size());
		assertEquals(0,computer.getValueList().size());
		computer.makeMove(game);
		assertEquals(21,computer.getBoardList().size());
	}
	
	/**
	 * Test for the correct color set of the AI.
	 */
	@Test
	void testTheSetUpColor() {
		Intelligence computer = new SimpleAI();
		Game game = new GameStub();
		game.getClass();
		computer.setColor(true);
		assertTrue(computer.isColor());
		computer.setColor(false);
		assertFalse(computer.isColor());
	}
	
	/**
	 * This method tests the correct update of every list for movement generation.
	 * First move is 2*8 pawns (16) + 2*2 knights (4) + base board (1) = 20 movements.
	 */
	@Test
	void testTheUpdateOfLists() {
		Intelligence computer = new SimpleAI();
		Game game = new Game();
		computer.makeMove(game);
		assertEquals(21,computer.getBoardList().size());
		assertEquals(21,computer.getMovementList().size());
		assertEquals(21,computer.getValueList().size());
	}
	
	/**
	 * This method tests the setter and getter for the boolean which indicates the choice of a random first move.
	 */
	@Test
	void testIfWeHaveAFirstMove() {
		Intelligence computer = new SimpleAI();
		Game game = new Game();
		game.getClass();
		computer.setFirstMove();
		assertTrue(computer.isFirstMove());
		assertFalse(computer.makeMove(game).isEmpty());
		computer.firstMoveWasSet();
		assertFalse(computer.isFirstMove());
	}
	
	/**
	 * This methods tests the setter and getter for the interrupt boolean.
	 */
	@Test
	void testForInterrupt() {
		Intelligence ai = new AI();
		assertFalse(ai.isInterrupt());
		ai.setInterrupt(true);
		assertTrue(ai.isInterrupt());
		Game game = new Game();
		game.setLanguageTrue(1);
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		views.add(new ConsoleStub());
		game.setViews(views);
		String move = ai.makeMove(game);
		assertEquals("Interrupt", move);
	}
	
	/**
	 * This method tests the beta branch of the Min Max Search in AI for black player. Only available for higher depth than used for the game.
	 */
	@Test
	void testForMinMaxBlack() {
		Intelligence computer = new AI();
		computer.setColor(false);
		Game game = new Game();
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		views.add(new ConsoleStub());
		game.setViews(views);
		BoardStub board = new BoardStub(game);
		for(int c = 0; c < Constants.BOARD_DIMENSIONS; c++) {
			for(int l = 0; l < Constants.BOARD_DIMENSIONS; l++) {
				board.removeMyFigure(l, c);
			}
		}
		board.createKings(1, 1, true);
		board.createKings(7, 7, false);
		board.createPawns(5, 5, true);
		game.setBoard(board);
		computer.setDepthForAI(5);
		String move = computer.makeMove(game);
		assertEquals("h1-g2", move);
	}
	
	/**
	 * This method tests the beta branch of the Min Max Search in AI for white player. Only available for higher depth than used for the game.
	 */
	@Test
	void testForMinMaxWhite() {
		Intelligence computer = new AI();
		computer.setColor(true);
		Game game = new Game();
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		views.add(new ConsoleStub());
		game.setViews(views);
		BoardStub board = new BoardStub(game);
		for(int c = 0; c < Constants.BOARD_DIMENSIONS; c++) {
			for(int l = 0; l < Constants.BOARD_DIMENSIONS; l++) {
				board.removeMyFigure(l, c);
			}
		}
		board.createKings(1, 1, false);
		board.createKings(7, 7, true);
		board.createPawns(5, 5, false);
		game.setBoard(board);
		computer.setDepthForAI(5);
		String move = computer.makeMove(game);
		assertEquals("h1-g1", move);
	}
	
	/**
	 * Test the thinking getter.
	 */
	@Test
	void testThinking() {
		AI ai = new AI();
		assertFalse(ai.isThinking());
		SimpleAI sAi = new SimpleAI();
		assertFalse(sAi.isThinking());
	}
}
