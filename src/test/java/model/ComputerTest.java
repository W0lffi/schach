package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test for the Computer as enemy.
 * @author Christin Krause, Jan Rehfeld, Sven Wolff
 */
class ComputerTest {

	/**
	 * Test dummy for AI constructor.
	 */
	@Test
	void testTheBasicInstance() {
		Player pc = new Computer(2);
		assertEquals(AI.class, pc.getIntelligence().getClass());
		pc.setColor(true);
		assertTrue(pc.isColor());
		pc.setColor(false);
		assertFalse(pc.isColor());
		assertEquals(pc.getClass(),Computer.class);
	}
	
	/**
	 * Test for the correct creation of an intelligence object based on choice by a setter.
	 */
	@Test
	void testTheChoiceOfAI() {
		Player pc = new Computer(1);
		assertEquals(SimpleAI.class, pc.getIntelligence().getClass());
		pc = new Computer(2);
		assertEquals(AI.class, pc.getIntelligence().getClass());
	}
}
