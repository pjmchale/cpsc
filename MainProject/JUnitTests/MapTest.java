import static org.junit.Assert.*;
import org.junit.Test;

public class MapTest {
	@Test 
	public void test_setUnitsTo10() {
		Map m = new Map(false);
		Country c = m.getCountries().get(0);
		m.setUnitsTo(c, 10);
		assertEquals("Expected units to be 10", 10, c.getUnits());
	}

	@Test 
	public void test_setOwnerTo() {
		Map m = new Map(false);
		Player p = new Player("Mark");
		Country c = m.getCountries().get(0);
		m.setOwnerTo(c, p);
		Player cp = c.getOwner();
		assertEquals("Expected owner to be Mark", "Mark", cp.getName());
	}

	@Test 
	public void test_getNeighbours() {
		Map m = new Map(false);
		Player p = new Player("Tommy");
		Country c = m.getCountries().get(0);
		m.setOwnerTo(c, p);
		Player cp = c.getOwner();
		assertEquals("Expected owner to be Tommy", "Tommy", cp.getName());
	}

	@Test 
	public void test_setUnitsToNegtive() {
		Map m = new Map(false);
		Country c = m.getCountries().get(0);
		m.setUnitsTo(c, -10);
		assertEquals("Expected units to be 0", 0, c.getUnits());
	}

}