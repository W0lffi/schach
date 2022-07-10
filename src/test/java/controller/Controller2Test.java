package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import model.King;
import stubs.ConsoleStub;
import stubs.ControllerStub;
import stubs.GameStub;

/**
 * JUnit Test Class with more methods to test the Controller of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
public class Controller2Test {
	
	private ConsoleController consoleController;
	private ConsoleStub view;
	
	/**
	 * Test to check if game recognizes that the game has finished after only two kings and bishops, and 28 chars on cemetery are left
	 */
	@Test
	void testGameDrawWithSpecialSituations() {
		GameStub game = new GameStub();
		game.imitateGameEnding();
		assertEquals(King.class,game.getBoard().getSingleTile(0, 0).getFigure().getClass());
		assertEquals(28,game.getCemetery().getBeaten().size());
		reInitControllerWithInput("a8-a7");
		((ControllerStub) consoleController).setGame(game);
		assertTrue(consoleController.userAction());
	}
	
	/**
	 * Test to see if position of b on field is recognized correctly. This move is also not allowed. 
	 */
	@Test
	void testMoveAllPawnsToCheckLetterConversion() {
		String s = System.lineSeparator();
		String turns = "b7-b6" + s + "c7-c6" + s+ "d7-d6" + s + "e7-e6" + s + "f7-f6";
		reInitControllerWithMultiInput(turns);
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
	}
		
	/**
	 * Test to see if position of h on field is recognized correctly. This move is also not allowed. 
	 */
	@Test
	void testMoveAllPawnsToCheckLetterConversionH() {
		reInitControllerWithInput("h7-h6");
		assertFalse(consoleController.userAction());
	}
	
	/**
	 * Test to see, if hit en passent is recognized correctly
	 */
	@Test
	void testGameHitEnPassant() {
		String s = System.lineSeparator();
		String turns = "d2-d4" + s + "h7-h6" + s + "d4-d5" + s + "e7-e5" + s + "d5-e6";
		reInitControllerWithMultiInput(turns);
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		assertTrue(consoleController.userAction());
	}
	
	/**
	 * Test to see, if hit en passent is recognized correctly from the other side
	 */
	@Test
	void testGameHitEnPassantOtherSide() {
		String s = System.lineSeparator();
		String turns = "h2-h3" + s + "b7-b5" + s + "h3-h4" + s + "b5-b4" + s + "a2-a4" + s + "b4-a3";
		reInitControllerWithMultiInput(turns);
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		assertTrue(consoleController.userAction());
	}
	
	/**
	 * Test to check if white castling can be recognized 
	 */
	@Test
	void testKingInCastlingWhiteSmall() {
		String s = System.lineSeparator();
		String turns = "e2-e3" + s + "a7-a6" + s + "f1-d3" + s + "a6-a5" + s + "g1-f3" + s + "a5-a4" + s + "e1-g1";
		reInitControllerWithMultiInput(turns);
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		assertTrue(consoleController.userAction());
	}
	
	/**
	 * Test to check if black castling can be recognized 
	 */
	@Test
	void testKingInCastlingBlackSmall() {
		String s = System.lineSeparator();
		String turns = "e2-e3" + s + "e7-e6" + s + "e3-e4" + s + "f8-d6" + s + "e4-e5" + s + "g8-h6" + s + "b2-b3" + s + "e8-g8";
		reInitControllerWithMultiInput(turns);
		assertTrue(consoleController.userAction());
		consoleController.userAction();
		consoleController.userAction();
		consoleController.userAction();
		assertTrue(consoleController.userAction());
		assertTrue(consoleController.userAction());
		assertTrue(consoleController.userAction());
		assertTrue(consoleController.userAction());
	}
	
	/**
	 * Test the getCharacterValue method.
	 */
	@Test
	void testGetCharacterValue() {
		consoleController = new ControllerStub();
		consoleController.setCharacterValue('a');
		assertEquals(0,consoleController.getCharacterValue());
		consoleController.setCharacterValue('h');
		assertEquals(7,consoleController.getCharacterValue());
		consoleController.setCharacterValue('v');
		assertEquals(-1,consoleController.getCharacterValue());
	}
	
	/**
	 * Test the getMainMenu returns null for console game.
	 */
	@Test
	void testDummyMethodsDoNothing() {
		consoleController = new ConsoleController();
		assertEquals(null,consoleController.getMainMenu());
	}
	
	/**
	 * Initializes the console input manually with expected input
	 * @param input A movement string input of the form a1-a2, etc
	 */
	void reInitControllerWithInput(String input) {
		String init = input;
		InputStream inputstream = new ByteArrayInputStream(init.getBytes());
		System.setIn(inputstream);
		view = new ConsoleStub();
		consoleController = new ControllerStub();
	}
	
	/**
	 * Initialized the console input manually with multiple inputs
	 * @param input A concatenated movement string input 
	 */
	void reInitControllerWithMultiInput(String input) {
		String simulatedUserInput = input;
		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
		view = new ConsoleStub();
		view.init(new GameStub());
		consoleController = new ControllerStub();
	}
}
