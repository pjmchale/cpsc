import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class DiceTest{
	@Test
	public void test_setSidesPositive() {
		Dice t = new Dice();
		t.setSides(10);
		assertEquals("Tries to set sides to 10",10,t.getSides());
	}
	@Test
	public void test_setSidesNegative() {
		Dice t = new Dice();
		t.setSides(-3);
		assertEquals("Tries to set sides to -3",6,t.getSides());
	}
}