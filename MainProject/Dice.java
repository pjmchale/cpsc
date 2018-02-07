import java.util.Random;

public class Dice{
	private Random random;
	private int sides;
	Dice(int sides){
		random = new Random();
		this.sides = sides;
	}
	Dice(){
		random = new Random();
		this.sides = 0;
	}
	public void setSides(int sides){
		this.sides = sides;
	}
	public int[] rollDices(int numDice) {
		int [] dices = new int[numDice];
		for (int i = 0; i < dices.length; i++) {
			dices[i] = random.nextInt(sides)+1;
		}
		return dices;
	}
	
}
