package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Computer;
import model.Human;
import model.Normal;
import schach.Constants;
import stubs.ConsoleStub;
import stubs.GameStub;

/**
 * JUnit Test Class for the ViewInterface Console
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 *
 */
class ConsoleTest {

	/**
	 * Test which checks if player classes are correctly set and whether the view is attached on the game object.
	 * Checks also for toggle language option.
	 */
	@Test
	void testPlayerClassAndAttached() {
		Console console = new Console(true);
		assertEquals(Human.class,console.getGame().getPlayer2().getClass());
		assertEquals(Normal.class,console.getGame().getGamemode().getClass());
		assertEquals(Console.class,console.getGame().getViews().get(0).getClass());
		
	}
	
	/**
	 * Test which checks if the players have the right color and language if the game is started simple.
	 */
	@Test
	void testColorAndLanguage() {
		Console console = new Console(true);
		assertTrue(console.getGame().getPlayer1().isColor());
		assertFalse(console.getGame().getPlayer2().isColor());
		assertEquals(1,console.getLanguage());
		console.setLanguage(0);
		assertEquals(0,console.getLanguage());
		assertEquals(null,console.getBtnLanguage());
	}
	
	/**
	 * Tests the initConsoleOut method.
	 */
	@Test
	void testInitConsoleOut() {
		InputStream inputstream = new ByteArrayInputStream("1".getBytes());
		System.setIn(inputstream);
		Console console = new Console(true);
		assertEquals(1, console.getInitConsoleOut(Constants.CHOOSE_ENEMY, Constants.CHOOSE_ENEMY.length));
		inputstream = new ByteArrayInputStream("2".getBytes());
		System.setIn(inputstream);
		console = new Console(true);
		assertEquals(2, console.getInitConsoleOut(Constants.CHOOSE_ENEMY, Constants.CHOOSE_ENEMY.length));
		String input = "a" + System.lineSeparator() + "5" + System.lineSeparator() + "0" + System.lineSeparator() + "2";
		inputstream = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputstream);
		console = new Console(true);
		console.setLanguage(-1);
		assertEquals(2, console.getInitConsoleOut(Constants.CHOOSE_ENEMY, Constants.CHOOSE_ENEMY.length));
		console = new Console(true);
		input = "a" + System.lineSeparator() + "5" + System.lineSeparator() + "0" + System.lineSeparator() + "1";
		inputstream = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputstream);
		console = new Console(true);
		assertEquals(1, console.getInitConsoleOut(Constants.CHOOSE_COLOR, Constants.CHOOSE_COLOR.length));
		Console temp = console;
		console.gameEnd("Test");
		assertEquals(temp,console);
	}
	
	/**
	 * Test for the simple mode to skip question about a AI player.
	 */
	@Test
	void testStartInSimpleMode() {
		Console console = new Console(true);
		assertEquals(Human.class, console.getGame().getPlayer2().getClass());
	}
	
	/**
	 * Test for the not simple mode and creation of AI player in console. 
	 */
	@Test
	void testStartInNotSimpleModeWithAI() {
		InputStream inputstream = new ByteArrayInputStream("1".getBytes());
		System.setIn(inputstream);
		String input = "2" + System.lineSeparator() + "1" + System.lineSeparator() + "2" + System.lineSeparator() + "3"  + System.lineSeparator() +"1" +  System.lineSeparator() + "a2-a4";
		inputstream = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputstream);
		Console console = new Console(false);
		assertEquals(Computer.class, console.getGame().getPlayer2().getClass());
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(console);
		console.getGame().setViews(views);
		console.getGame().getPlayer2().getIntelligence().setDepthForAI(5);
		console.getGame().getPlayer2().getIntelligence().makeMove(console.getGame());
		assertFalse(console.getGame().getPlayer2().isColor());
		assertTrue(console.getGame().getPlayer1().isColor());
	}
	
	/**
	 * Test for the not simple mode and creation of second human player in console. 
	 */
	@Test
	void testStartInNotSimpleModeWithHuman() {
		InputStream inputstream = new ByteArrayInputStream("1".getBytes());
		System.setIn(inputstream);
		String input = "1" + System.lineSeparator() + "1" + System.lineSeparator() + "1" + System.lineSeparator() + "a2-a4" + System.lineSeparator() + "a7-a6";
		inputstream = new ByteArrayInputStream(input.getBytes());
		System.setIn(inputstream);
		Console console = new Console(false);
		assertEquals(Human.class, console.getGame().getPlayer2().getClass());
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(console);
		console.getGame().setViews(views);
		assertFalse(console.getGame().getPlayer2().isColor());
		assertTrue(console.getGame().getPlayer1().isColor());
	}
	
	/**
	 * Tests if the console can update and notify its view correctly, as well nothing happens for other update methods.
	 */
	@Test
	void testUpdateFromGameToConsoleAndUpdateFeatures() {
		Console console = new Console(true);
		console.getGame().setOutput("Test");
		console.getGame().viewNotify();
		assertEquals("", console.getGame().getOutput());
		Console console2 = console;
		console.updateBoard();
		assertEquals(console,console2);
	}
	
	/**
	 * Test only its dummy, not used in finished game but for testing.
	 */
	@Test
	void testConsoleDummyCheck() {
		ConsoleStub console = new ConsoleStub();
		console.updateBoard();
		assertEquals(GameStub.class,console.getGame().getClass());
		console.gameEnd("Test");
		assertEquals(null, console.getBtnLanguage());
		assertEquals(0,console.initConsoleOut(null, 0));
	}
	
	/**
	 * This method is used to test the simple flag, which is again used for testing.
	 */
	@Test
	void testSimpleFlagForConsoleSet() {
		Console console = new Console(true);
		assertTrue(console.isSimple());
		console.setSimple(false);
		assertFalse(console.isSimple());
	}
}
