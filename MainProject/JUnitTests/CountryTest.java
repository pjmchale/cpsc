import java.util.*;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.Test;


public class CountryTest {
	ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));;
	public class CT extends Country {
		public CT(){
			super(2, neighbours, "Canada", false);
		}
	}

	// Test accessors and modifiers
	@Test
	public void test_setName() {
		Country c = new CT();
		c.setName("USA");
		assertEquals( "Tried set name to USA", "USA", c.getName());
	}

	@Test
	public void test_addUnitsPositive() {
		Country c = new CT();
		c.addUnits(2);
		c.addUnits(3);
		c.addUnits(5);
		assertEquals("Tried add 2, 3, and 5 units", 10, c.getUnits());
	}

	@Test
	public void test_addUnitsNegative() {
		Country c = new CT();
		c.addUnits(-3);
		c.addUnits(-4);
		c.addUnits(-7);
		assertEquals("Tried add -3, -4, and -7 units", -14, c.getUnits());
	}

	@Test
	public void test_setUnits() {
		Country c = new CT();
		c.setUnits(13);
		assertEquals("Tried to set the units to 13", 13, c.getUnits());
	}

	@Test
	public void test_getOwnerName() {
		Country c = new CT();
		Player p = new Player("Mark");
		c.setOwner(p);
		assertEquals("Tried to get owner name", "Mark", c.getOwnerName());
	}

	@Test
	public void test_getOwner() {
		Country c = new CT();
		Player p = new Player("Mark");
		c.setOwner(p);
		Player cp = c.getOwner();
		assertEquals("Tried to get owner name", "Mark", cp.getName());
	}

	@Test
	public void test_getOwnerID() {
		Country c = new CT();
		Player p = new Player("Mark");
		c.setOwner(p);
		assertEquals("Tried to get the owner ID", 1, c.getOwnerID());
	}

	@Test
	public void test_getCountryID() {
		Country c = new CT();
		c.setCountryID(5);
		assertEquals("Tried to get the country ID", 5, c.getCountryID());
	}

	@Test
	public void test_getNeighbours() {
		Country c = new CT();
		ArrayList<Integer> testNeighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		assertEquals("Tried to get the country neighbours", testNeighbours, c.getNeighbours());
	}
	// CHAGNE THIS ONE
	@Test
	public void test_isNeighbourTrue() {
		Country c = new CT();
		ArrayList<Integer> testNeighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		ArrayList<Integer> cp = c.getNeighbours();
		assertEquals("Country should be a neighbour", testNeighbours, cp);
	}

	@Test
	public void test_isNeighbourFalse() {
		Country c = new CT();
		Country cp = new CT();
		assertEquals("Country should be a neighbour", false, c.isNeighbour(cp));
	}

	// @Test
	// public void test_setClickable() {
	// 	Country c = new CT();
	// 	assertEquals("Country should be a neighbour", false, c.isNeighbour(cp), );
	// }


	
}






















