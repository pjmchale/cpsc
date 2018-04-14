import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;
import PlayerPackage.*;
import MapStage.*;
import GameEngine.*;

public class CountryTest {

	/**
	 * Create a class to build a country with default values
	*/
	ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));;
	public class CT extends Country {
		public CT(){
			super(2, neighbours, "Canada", false, 10, 10);
		}
	}

	// Test accessors and modifiers

	/**
	 * Test setting the name of a country
	 * Create a new country and set the name to "USA"
	 * check that the name is "USA"
	*/
	@Test
	public void test_setName() {
		Country c = new CT();
		c.setName("USA");
		assertEquals( "Tried set name to USA", "USA", c.getName());
	}

	/**
	 * Test adding a positive amount of units
	 * Create a new country and add 2, 3, and 5 units
	 * Check that the amout of units is now 10
	*/
	@Test
	public void test_addUnitsPositive() {
		Country c = new CT();
		c.addUnits(2);
		c.addUnits(3);
		c.addUnits(5);
		assertEquals("Tried add 2, 3, and 5 units", 10, c.getUnits());
	}

	/**
	 * Test adding a negative amount of units
	 * Create a new country and add -2, -4, and -7 units
	 * Check that the amout of units is now -14
	*/
	@Test
	public void test_addUnitsNegative() {
		Country c = new CT();
		c.addUnits(-3);
		c.addUnits(-4);
		c.addUnits(-7);
		assertEquals("Tried add -3, -4, and -7 units", -14, c.getUnits());
	}

	/**
	 * Test setting the units to 13
	 * Create a new country and set the units to 13
	 * Check that the amout of units is now 13
	*/
	@Test
	public void test_setUnits() {
		Country c = new CT();
		c.setUnits(13);
		assertEquals("Tried to set the units to 13", 13, c.getUnits());
	}


	/**
	 * Test getting the owners name
	 * Create a new country and player, and set it as it's owner
	 * Check that the owner has the same name
	*/
	@Test
	public void test_getOwnerName() {
		Country c = new CT();
		Player p = new Player("Mark");
		c.setOwner(p);
		assertEquals("Tried to get owner name", "Mark", c.getOwnerName());
	}

	/**
	 * Test getting the owner
	 * Create a new country and player, and set it as it's owner
	 * Check that the owner is now the same player
	*/
	@Test
	public void test_getOwner() {
		Country c = new CT();
		Player p = new Player("Mark");
		c.setOwner(p);
		Player cp = c.getOwner();
		assertEquals("Tried to get owner name", "Mark", cp.getName());
	}

	/**
	 * Test getting the owners ID
	 * Create a new country and player, and set it as it's owner
	 * Check that the owner has the same ID
	*/
	@Test
	public void test_getOwnerID() {
		Country c = new CT();
		Player p = new Player("Mark");
		c.setOwner(p);
		assertEquals("Tried to get the owner ID", 1, c.getOwnerID());
	}

	/**
	 * Test getting the country ID
	 * Create a new country and set the country ID to 5
	 * Check that the country has the same ID
	*/	
	@Test
	public void test_getCountryID() {
		Country c = new CT();
		c.setCountryID(5);
		assertEquals("Tried to get the country ID", 5, c.getCountryID());
	}

	/**
	 * Test getting the neighbours
	 * Create a new country and set it's neighbours
	 * Check that the country has the same neighbours
	*/
	@Test
	public void test_getNeighbours() {
		Country c = new CT();
		ArrayList<Integer> testNeighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		assertEquals("Tried to get the country neighbours", testNeighbours, c.getNeighbours());
	}

	/**
	 * Test that country is a neighbour
	 * Create a new country and set it's neighbours
	 * Check that the country has the same neighbours
	*/
	@Test
	public void test_isNeighbourTrue() {
		Country c = new CT();
		ArrayList<Integer> testNeighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		ArrayList<Integer> cp = c.getNeighbours();
		assertEquals("Country should be a neighbour", testNeighbours, cp);
	}

	/**
	 * Test that country is not a neighbour
	 * Create two new countrys
	 * Check that the country is not a neighbour of the other
	*/
	@Test
	public void test_isNeighbourFalse() {
		Country c = new CT();
		Country cp = new CT();
		assertEquals("Country should be a neighbour", false, c.isNeighbour(cp));
	}
	
}

