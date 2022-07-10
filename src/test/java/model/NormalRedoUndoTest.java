package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import stubs.ConsoleStub;

/**
 * JUnit Test Class to test the Redo and Undo function of normal.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class NormalRedoUndoTest {
	
	private BoardStorage bs;
	
	/**
	 * Tests the Undo and Redo function for the game start and a first move with a human opponent.
	 */
	@Test
	void testForUndoAndRedoWithHuman() {
		Game game = new Game();
		game.setGamemode(new Normal(game));
		game.setPlayer2(1,2);
		assertFalse(game.getGamemode().undo());
		assertFalse(game.getGamemode().redo());
		
		this.bs = new BoardStorage();
		String temp = "rnbqkbnrpppppppp------------------------------P--PPPPPPPRNBQKBNR";
		Board b = bs.stringToBoard(temp);
		b.setGame(game);
		
		game.getBoard().getBoards().boardToString(b, true);
		assertTrue(game.getGamemode().undo());
		assertTrue(game.getGamemode().redo());
	}
	
	/**
	 * Tests the Undo and Redo function for the game start with an AI opponent and a first move.
	 */
	@Test
	void testForUndoAndRedoWithAI() {
		Game game = new Game();
		game.setGamemode(new Normal(game));
		game.setPlayer2(2,1);
		game.getPlayer1().setColor(true);
		game.getPlayer2().setColor(false);
		assertFalse(game.getGamemode().undo());
		assertFalse(game.getGamemode().redo());
		this.bs = new BoardStorage();
		String temp = "rnbqkbnrpppppppp------------------------------P--PPPPPPPRNBQKBNR";
		Board b = bs.stringToBoard(temp);
		b.setGame(game);
		game.getBoard().getBoards().boardToString(b, true);
		assertTrue(game.getGamemode().undo());
		assertTrue(game.getGamemode().redo());
		game.setTurn(game.getPlayer2().isColor());
		game.getGamemode().undo();
		assertTrue(game.getGamemode().redo());
	}
	
	/**
	 * Tests the Undo and Redo function for the game start with an AI opponent and a first move.
	 * Needs Fixes after implementation of Undo/Redo with AI.
	 */
	@Test
	void testForInterrupts() {
		Game game = new Game();
		game.setGamemode(new Normal(game));
		game.setPlayer2(2,1);
		game.getPlayer1().setColor(true);
		game.getPlayer2().setColor(false);
		assertFalse(game.getGamemode().undo());
		assertFalse(game.getGamemode().redo());
		assertTrue(game.getGamemode().isInterrupt());
		game.getGamemode().setInterrupt(false);
		assertFalse(game.getGamemode().isInterrupt());
	}
	
	/**
	 * Test for special situation for bug reproduction of checkTest.
	 */
	@Test
	void testForSpecialSituation() {
		BoardStorage bs = new BoardStorage();
		String situation = "-n---bR-----k--r-p-Brp--qB-N----Pq-Pp--p-------q-BQP------K-----";
		Game game = new Game();
		game.setBoard(bs.stringToBoard(situation));
		String[][] moves = game.getBoard().getTiles()[3][0].getFigure().move(game.getBoard().getTiles(), true);
		assertEquals("false", moves[4][2]);
	}
	
	/**
	 * Test for checkMate situation of the king.
	 */
	@Test
	void testForCheckMateDraw() {
		BoardStorage bs = new BoardStorage();
		String situation = "----k----------------------------------q-----------PP------QKB--";
		Game game = new Game();
		game.setBoard(bs.stringToBoard(situation));
		game.setPlayer2(1, 0);
		game.attach(new ConsoleStub());
		game.setGamemode(new Normal(game));
		game.getGamemode().setLanguage(1);
		game.getBoard().getTiles()[7][4].getFigure().setCheck(true);
		game.getGamemode().setFig(game.getBoard().getTiles()[4][7].getFigure());
		assertTrue(game.getGamemode().callCheckMate());
		game.getBoard().getTiles()[7][4].getFigure().setCheck(false);
		assertTrue(game.getGamemode().callCheckMate());
	}
	
	/**
	 * Test for draw because of special situations.
	 */
	@Test
	void testForSpecialSituationsAndDraw() {
		BoardStorage bs = new BoardStorage();
		String situation = "----k-------------------------------------------------------K---";
		Game game = new Game();
		game.setBoard(bs.stringToBoard(situation));
		game.setPlayer2(1, 0);
		game.attach(new ConsoleStub());
		game.setGamemode(new Normal(game));
		game.getGamemode().setLanguage(1);
		game.getGamemode().setFig(game.getBoard().getTiles()[0][4].getFigure());
		game.getCemetery().setBeatenFromString("pppppppppppppppppppppppppppppp");
		assertTrue(game.getGamemode().callCheckMate());
		
		situation = "b---k-------------------------------------------------------K---";
		game.setBoard(bs.stringToBoard(situation));
		game.getCemetery().setBeatenFromString("ppppppppppppppppppppppppppppp");
		assertTrue(game.getGamemode().callCheckMate());
		
		situation = "B---k-------------------------------------------------------K---";
		game.setBoard(bs.stringToBoard(situation));
		game.getCemetery().setBeatenFromString("ppppppppppppppppppppppppppppp");
		assertTrue(game.getGamemode().callCheckMate());
		
		situation = "--B-k--------------------------------b----------------------K---";
		game.setBoard(bs.stringToBoard(situation));
		game.getCemetery().setBeatenFromString("pppppppppppppppppppppppppppp");
		assertTrue(game.getGamemode().callCheckMate());
	}
}
