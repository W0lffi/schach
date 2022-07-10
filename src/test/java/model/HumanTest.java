package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test the methods from the Human class. 
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class HumanTest {
	
	/**
	 * Test the constructor of a human player.
	 */
	@Test
	void testTheInstanceOfHumanPlayer() {
		Human human = new Human();
		human.setColor(true);
		assertTrue(human.isColor());
		human.setColor(false);
		assertFalse(human.isColor());
		assertEquals(null,human.getIntelligence());
	}
	

}
