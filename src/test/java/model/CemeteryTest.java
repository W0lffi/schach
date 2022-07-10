package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Test(s) for the cemetery.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class CemeteryTest {
	
	/**
	 * Test for the String with figure char names to figure list.
	 */
	@Test
	void testAStringToAListOfFigs() {
		Cemetery cem = new Cemetery();
		String lotsOfFigs = "pPpPQkKRBNX";
		List<Figure> list = cem.translateFromStringToList(lotsOfFigs);
		assertEquals(Pawn.class,list.get(0).getClass());
		assertFalse(list.get(5).isColor());
		assertEquals(Knight.class,list.get(list.size()-2).getClass());
		assertEquals(lotsOfFigs.length(),list.size());
		cem.setBeatenFromString(lotsOfFigs);
		assertEquals(lotsOfFigs.length(),cem.getBeaten().size());
	}
	
	/**
	 * Test the setter and getter from the cemetery.
	 */
	@Test
	void testTheListOfBeatenChars() {
		Cemetery cem = new Cemetery();
		cem.setBeaten(new Pawn(true, 0 , 0));
		cem.setBeaten(new Pawn(false, 0, 0));
		assertEquals(cem.getBeaten().get(1).getName(), 'p');
		assertEquals(cem.getBeaten().get(0).getName(),'P');
	}
	
	/**
	 * Test for the correct count of white and black figures
	 */
	@Test
	void testCountingOfFiguresOnDeathList() {
		Cemetery cem = new Cemetery();
		int white = 0;
		while(white < 5) {
			cem.setBeaten(new Pawn(true, white , 0));
			white++;
		}
		int black = 0;
		while(black < 3) {
			cem.setBeaten(new Pawn(false, black , 1));
			black++;
		}
		assertEquals(white, cem.countFigsByColor(true));
		assertEquals(black, cem.countFigsByColor(false));
	}
	
	/**
	 * Test for the color of the last figure which was added.
	 */
	@Test
	void testLastFigureOnDeathList() {
		Cemetery cem = new Cemetery();
		cem.setBeaten(new Pawn(true, 0 , 0));
		cem.setNewDead(cem.getBeaten().get(0).isColor());
		assertTrue(cem.isNewDead());
		cem.setBeaten(new Pawn(false, 0 , 0));
		cem.setNewDead(cem.getBeaten().get(1).isColor());
		assertFalse(cem.isNewDead());
	}
	
	/**
	 * Tests if a figure is on the cemetery list.
	 */
	@Test
	void testFigureIsOnCemetery() {
		Cemetery cem = new Cemetery();
		Figure p = new Pawn(true, 0 , 0);
		Figure q = new Queen(true, 1 , 1);
		cem.setBeaten(p);
		assertTrue(cem.isDead(p));
		assertFalse(cem.isDead(q));
	}
	
	/**
	 * Test whether the cemetery is rightfully cleared.
	 */
	@Test
	void testClear() {
		Cemetery cem = new Cemetery();
		Figure p = new Pawn(true, 0 , 0);
		cem.setBeaten(p);
		assertEquals(1, cem.getBeaten().size());
		cem.clear();
		assertEquals(0, cem.getBeaten().size());
	}

}
