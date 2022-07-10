package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Normal;
import schach.Constants;
import stubs.ConsoleStub;
import stubs.ControllerStub;
import view.ViewInterface;

/**
 * JUnit test class to test for console input parameters. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class Controller3Test {
	
	private ConsoleController consoleController;
	
	/**
	 * Test to check if the keyword "undo" is recognized correctly 
	 */
	@Test
	void testUserActionCheckUndo() {
		reInitControllerWithInput("undo");
		assertFalse(consoleController.userAction());
	}
	
	/**
	 * Test to check if the keyword "redo" is recognized correctly 
	 */
	@Test
	void testUserActionCheckRedo() {
		String s = System.lineSeparator();
		String turns = "redo" + s + "a2-a4" + s + "undo" + s + "redo" + s + "b7-b5";
		reInitControllerWithMultiInput(turns);
		assertFalse(consoleController.userAction());
		assertTrue(consoleController.userAction());
		assertTrue(consoleController.userAction());
		assertFalse(consoleController.userAction());
		assertFalse(consoleController.userAction());
	}
	
	/**
	 * Test to check if the keyword "draw" is recognized correctly 
	 */
	@Test
	void testUserActionCheckDraw() {
		reInitControllerWithInput("draw");
		assertFalse(consoleController.userAction());
	}
	
	/**
	 * Test to check if the keyword "surrender" is recognized correctly 
	 */
	@Test
	void testUserActionCheckSurrender() {
		reInitControllerWithInput("surrender");
		consoleController.getGame().setGamemode(new Normal(consoleController.getGame()));
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		consoleController.getGame().setViews(views);
		assertFalse(consoleController.userAction());
	}
	
	/**
	 * Test to check if the keyword "l" to switch the language is recognized correctly
	 */
	@Test
	void testUserActionCheckLanguage() {
		reInitControllerWithInput("language");
		consoleController.getGame().setGamemode(new Normal(consoleController.getGame()));
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		consoleController.getGame().setViews(views);
		assertFalse(consoleController.userAction());
		assertEquals(Constants.SWITCH_LANGUAGE[0], ((ControllerStub) consoleController).getGame().getOutput());
	}
	
	/**
	 * Initializes the console input manually with expected input
	 * @param input A movement string input of the form a1-a2, etc
	 */
	void reInitControllerWithInput(String input) {
		String init = input;
		InputStream inputstream = new ByteArrayInputStream(init.getBytes());
		System.setIn(inputstream);
		consoleController = new ControllerStub();
	}
	
	/**
	 * Initialized the console input manually with multiple inputs
	 * @param input A concatenated movement string input 
	 */
	void reInitControllerWithMultiInput(String input) {
		String simulatedUserInput = input;
		System.setIn(new ByteArrayInputStream(simulatedUserInput.getBytes()));
		consoleController = new ControllerStub();
		consoleController.getGame().setPlayer2(2, 1);
		consoleController.getGame().attach(new ConsoleStub());
		consoleController.getGame().start();
	}
	
}
