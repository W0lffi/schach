package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import stubs.BoardStub;
import stubs.ConsoleStub;
import stubs.GameStub;
import stubs.NormalStub;

/**
 * JUnit test class for testing of methods of the normal game mode 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
public class NormalTests {

	private Normal normal;
	
	/**
	 * Test for the move function of a normal gameplay
	 */
	@Test
	void testMoveTheFigure(){
		normal = new NormalStub(new Game()); 
		normal.setCurrentLine(6);
		normal.setCurrentColumn(0);
		normal.setDestinationLine(4);
		normal.setDestinationColumn(0);
		normal.setFig(new Queen(true,5,5));
		assertTrue(normal.moveTheFigure('Q'));
	}
	
	/**
	 * Test whether a beaten enemy will be add to the beaten list.
	 */
	@Test
	void testBeatAnEnemy() {
		normal = new NormalStub(new Game()); 
		normal.setCurrentLine(5);
		normal.setCurrentColumn(5);
		normal.setDestinationLine(1);
		normal.setDestinationColumn(5);
		normal.setFig(new Queen(true,5,5));
		assertEquals(0,normal.getGame().getCemetery().getBeaten().size());
		normal.moveTheFigure('Q');
		assertEquals(1,normal.getGame().getCemetery().getBeaten().size());
	}
	
	/**
	 * Test the pawn transformation
	 */
	@Test
	void testTransformation() {
		normal = new Normal(new Game());
		normal.setCurrentLine(1);
		normal.setCurrentColumn(5);
		normal.setDestinationLine(0);
		normal.setDestinationColumn(5);
		normal.setFig(new Pawn(true,1,5));
		normal.moveTheFigure('Q');
		assertEquals(Queen.class,normal.getFig().getClass());
		normal.setDestinationLine(7);
		normal.setFig(new Pawn(true,5,5));
		normal.moveTheFigure('N');
		assertEquals(Knight.class,normal.getFig().getClass());
	}
	
	/**
	 * Test for a full cemetery
	 */
	@Test
	void testSpecialCasesLotsOfBeatenChars() {
		GameStub game = new GameStub();
		assertEquals(0,game.getCemetery().getBeaten().size());
		game.fillMyCemetery(30);
		assertEquals(30,game.getCemetery().getBeaten().size());
		BoardStub board = new BoardStub();
		board.createKings(2, 2, true);
		board.createKings(1, 1, false);
		board.createQueens(5, 5, false);
		board.createQueens(4, 4, true);
		assertEquals(King.class,board.getTiles()[2][2].getFigure().getClass());
		game.getBoard().setTiles(board.getTiles());
		game.getGamemode().setFig(board.getTiles()[5][5].getFigure());
		game.getGamemode().setGame(game);
		game.attach(new ConsoleStub());
		assertTrue(game.getGamemode().moveTheFigure('Q'));
	}
	
	/**
	 * Test for surrounded king
	 */
	@Test
	void testSpecialCasesKingSurrounded() {
		GameStub game = new GameStub();
		game.fillMyCemetery(28);
		assertEquals(28,game.getCemetery().getBeaten().size());
		BoardStub board = new BoardStub();
		board.createKings(7, 7, true);
		board.createQueens(6, 7, false);
		board.createPawns(7, 6, true);
		board.createPawns(5, 6, true);
		board.createPawns(6, 6, true);
		board.createPawns(6, 5, true);
		board.createKings(1, 1, false);
		board.createQueens(6, 3, false);
		game.getBoard().setTiles(board.getTiles());
		game.getGamemode().setFig(board.getTiles()[6][3].getFigure());
		game.getGamemode().setGame(game);
		assertTrue(game.getGamemode().moveTheFigure('Q'));
	}
	
	/**
	 * Test for the JUnit implemented getter
	 */
	@Test
	void testCheckTheJUnitSetter() {
		Game game = new Game();
		ArrayList<Figure> hitEnPassantList = new ArrayList<Figure>();
		normal = new NormalStub(game);
		assertEquals(game,normal.getGame());
		normal.setHitEnPassantList(hitEnPassantList);
		assertEquals(0,normal.getHitEnPassantList().size());
		hitEnPassantList.add(new Pawn(true,6,0));
		normal.setHitEnPassantList(hitEnPassantList);
		assertEquals(1,normal.getHitEnPassantList().size());
		hitEnPassantList.add(new Queen(true,2,4));
		normal.setHitEnPassantList(hitEnPassantList);
		assertEquals(2,normal.getHitEnPassantList().size());
		normal.setLanguage(0);
		assertEquals(0,normal.getLanguage());
	}
}
