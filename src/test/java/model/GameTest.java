package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import controller.ConsoleController;
import stubs.ConsoleStub;
import stubs.ControllerStub;
import stubs.GameStub;
import view.ViewInterface;

/**
 * Test the methods from the Game class. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class GameTest {
	
	Game game = new Game();
	
	/**
	 * Test the output string of game.
	 */
	@Test
	void testGetOutputForStrings() {
		game.setOutput("Test");
		assertEquals("Test",game.getOutput());
	}
	
	/**
	 * Test the language setting of game.
	 */
	@Test
	void testLanguageOfGame() {
		game.setGamemode(new Normal(game));
		List<ViewInterface> views = new ArrayList<ViewInterface>();
		views.add(new ConsoleStub());
		views.add(new ConsoleStub());
		game.setViews(views);
		game.setLanguage(0);
		assertEquals(0,game.getLanguage());
		game.run();
	}
	
	/**
	 * Test the gamemode of the game.
	 */
	@Test
	void testSetGamemodeNormal() {
		game.setGamemode(new Normal(game));
		RulesInterface gamemode = new Normal(new Game());
		assertEquals(game.getGamemode().getClass(),gamemode.getClass());
	}
	
	/**
	 * Test the type of player two (AI or Human).
	 */
	@Test
	void testCheckForPlayerType() {
		game.setPlayer2(1,1);
		Player player = new Human();
		assertEquals(game.getPlayer2().getClass(),player.getClass());
		assertEquals(game.getPlayer1().getClass(),player.getClass());
		player = new Computer(1);
		game.setPlayer2(2,1);
		assertEquals(game.getPlayer2().getClass(),player.getClass());
	}
	
	/**
	 * Test whether the game is running.
	 */
	@Test
	void testCheckRunningOfGame() {
		Game game = new GameStub();
		assertFalse(game.isRunning());
		game.start();
		assertFalse(game.isRunning());
		game.setRunning(false);
		assertTrue(game.isRunning());
	}
	
	/**
	 * Test the attached observer.
	 */
	@Test
	void testCheckForViewObserver() {
		Game game = new GameStub();
		ConsoleStub console = new ConsoleStub();
		game.attach(console);
		game.viewNotify();
		assertEquals("",game.getOutput());
		game.detach(console);
		assertEquals(0, game.getViews().size());
	}
	
	/**
	 * Test the cemetery-getter of the game class.
	 */
	@Test
	void testCemeteryCheckInGame() {
		assertEquals(Cemetery.class, game.getCemetery().getClass());
		Cemetery cem = new Cemetery();
		game.setCemetery(cem);
		assertEquals(cem, game.getCemetery());
	}
	
	/**
	 * Test the "toggler" for the running boolean in game. 
	 */
	@Test
	void testTurnToggleAndGameStops() {
		assertTrue(game.isTurn());
		game.setTurn(false);
		assertFalse(game.isTurn());
		game.stopGame();
		assertFalse(game.isRunning());
		game.setRunning(true);
		assertTrue(game.isRunning());
	}
	
	/**
	 * Test the controller-getter of the game class.
	 */
	@Test
	void testControllerSetting() {
		ConsoleController consoleController = new ControllerStub();
		game.setController(consoleController);
		assertEquals(consoleController, game.getController());
	}

}
