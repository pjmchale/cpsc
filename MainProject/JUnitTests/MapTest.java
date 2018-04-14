import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;
import PlayerPackage.*;
import GameEngine.*;
import MapStage.*;

import org.junit.Test;

public class MapTest {

	/**
	 * Test setting and getting the units at 10
	 * Create a new world map and get the first country
	 * set it's units to 10 then check it they are set to 10
	*/
	@Test 
	public void test_setUnitsTo10() {
		WorldMap m = new WorldMap(false);
		Country c = m.getCountries().get(0);
		m.setUnitsTo(c, 10);
		assertEquals("Expected units to be 10", 10, c.getUnits());
	}

	/**
	 * Test setting and getting the owner
	 * Create a new world map and get the first country, also create a new player
	 * set the country's owner to the new player
	 * Then get the owner and compare
	*/
	@Test 
	public void test_setOwnerTo() {
		WorldMap m = new WorldMap(false);
		Player p = new Player("Mark");
		Country c = m.getCountries().get(0);
		m.setOwnerTo(c, p);
		Player cp = c.getOwner();
		assertEquals("Expected owner to be Mark", "Mark", cp.getName());
	}

	/**
	 * Test setting the units to a negative number
	 * Create a new world map and get the first country
	 * set the country's units to -10
	 * Check that the unit amount is still 0
	*/
	@Test 
	public void test_setUnitsToNegtive() {
		WorldMap m = new WorldMap(false);
		Country c = m.getCountries().get(0);
		m.setUnitsTo(c, -10);
		assertEquals("Expected units to be 0", 0, c.getUnits());
	}

	/**
	 * Test setting the units
	 * Create a new world map and get the first country
	 * set the country's units to 10
	 * Check that the unit amount is 10
	*/
	@Test 
	public void test_setUnitsToPositive() {
		WorldMap m = new WorldMap(false);
		Country c = m.getCountries().get(0);
		m.setUnitsTo(c, 10);
		assertEquals("Expected units to be 0", 10, c.getUnits());
	}
}