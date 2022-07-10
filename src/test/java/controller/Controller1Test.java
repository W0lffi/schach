package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import model.Pawn;
import stubs.ConsoleStub;
import stubs.ControllerStub;
import stubs.GameStub;
import view.ViewInterface;

/**
 * JUnit Test Class to test the Controller of the game.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class Controller1Test {
	
	private ConsoleController consoleController;
	private ConsoleStub view;
	
	/**
	 * Test of the constructor of the controller
	 */
	@Test
	void testIfTheControllerIsConstructed() {
		String s = System.lineSeparator();
		String turns = "e2-e3" + s + "e7-e6";
		reInitControllerWithMultiInput(turns);
		ViewInterface view = new ConsoleStub();
		ConsoleController consoleController = new ConsoleController(view);
		assertEquals(GameStub.class,consoleController.getGame().getClass());
	}
	
	/**
	 * Test to generate the list of beaten figures by adding chars and reading the output
	 */
	@Test
	void testListOfBeatenChars() {
		consoleController = new ControllerStub();
		consoleController.setListOfBeaten("");
		String listOfBeaten = consoleController.getListOfBeaten();
		assertEquals(0,listOfBeaten.length());
		((ControllerStub) consoleController).getGame().getCemetery().setBeaten(new Pawn(true, 0, 0));
		consoleController.setListOfBeaten("");
		assertEquals("P",consoleController.getListOfBeaten());
		((ControllerStub) consoleController).getGame().getCemetery().setBeaten(new Pawn(false, 0, 0));
		consoleController.setListOfBeaten("");
		assertEquals("P, p",consoleController.getListOfBeaten());
	}
	
	/**
	 * Test to check if the keyword "beaten" is recognized correctly 
	 */
	@Test
	void testUserActionCheckBeaten() {
		reInitControllerWithInput("beaten");
		assertFalse(consoleController.userAction());
	}
	
	
	
	/**
	 * Test to validate that the program recognizes the correct input for a move
	 */
	@Test
	void testUserActionCheckMoveInput() {
		String s = System.lineSeparator();
		String turns = "a2-a3" + s + "a3-a2" + s + "i7-g9" + s + "i9#a7" + s + "a9-a7";
		reInitControllerWithMultiInput(turns);
		assertTrue(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
	}
	
	
	/**
	 * Test to validate that the program will not allow black for first move
	 */
	@Test
	void testUserActionCheckMoveInputInvalidTurn() {
		String s = System.lineSeparator();
		String turns = "g7-g6" + s + "g4-g3";
		reInitControllerWithMultiInput(turns);
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
	}
	
	
	/**
	 * Test to ensure the second number in movement string and length of string is correct
	 */
	@Test
	void testUserActionCheckMoveInputInvalidInput() {
		String s = System.lineSeparator();
		String turns = "a9-a666" + s + "a2.a3" + s + "a2-x3" + s + "a2-a9";
		reInitControllerWithMultiInput(turns);
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
	}
	
	/**
	 * Test to check for correct transition char in movement string
	 */
	@Test
	void testUserActionCheckMoveInputTransWrong() {
		reInitControllerWithInput("a2-a3Z");
		assertFalse(consoleController.userAction());
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
	
	/**
	 * Test the remaining getters.
	 */
	@Test
	void testGetters() {
		ControllerStub c = new ControllerStub();
		assertNull(c.getBoardPos());
		assertNull(c.getLastImages());
		assertNull(c.getPlayground());
		c.setSettings(new boolean[0]);
		assertEquals(0, c.getSettings().length);
	}
}
