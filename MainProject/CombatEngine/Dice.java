package CombatEngine;
import java.util.Random;
/**
 * Simulates a dice. With amount of sides to roll from
 */
public class Dice{
	private Random random;
	private int sides;
	
	/**
	 * Creates the dice with sides
	 * @param sides how many sides the dice will have
	 */
	Dice(int sides){
		random = new Random();
		this.sides = sides;
	}
	/**
	* Creates a dice with 6 sides
	*/
	Dice(){
		random = new Random();
		this.sides = 6;
	}
	/**
	 * Sets the amount of sides  
	 */
	public void setSides(int sides){
		this.sides = sides;
	}
	
	public int getSides() {
		return sides;
	}
	
	/**
	 * Roll's a number of dice
	 * @param numDice how much dice to roll
	 */
	public int[] rollDices(int numDice) {
		int [] dices = new int[numDice];
		for (int i = 0; i < dices.length; i++) {
			dices[i] = random.nextInt(sides)+1;
		}
		return dices;
	}
	
}
