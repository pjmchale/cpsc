import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
public class Combat{
	private Dice dice;
	private Player attacker;
	private Player defender;
	private Country attackingCountry;
	private Country defendingCountry;
	private int numAttackers;
	private int numDefenders;
	Combat(Country attackingCountry, Country defendingCountry){
		dice = new Dice();
		this.attackingCountry = attackingCountry;
		this.defendingCountry = defendingCountry;
		attacker = attackingCountry.getOwner();
		defender = defendingCountry.getOwner();
		
		
	}
	public boolean attackerWin(int attackerDice, int defenderDice) {
		boolean attackWin = false;
		if(attackerDice > defenderDice) {
			attackWin = true;
		}
		return attackWin;
	}
	public int[] rollDice(int numDice) {
		int dices[] = dice.rollDices(numDice);
		return dices;
		
	}
	public void countryLose(Country country , Player attacker) {
		Player player = country.getOwner();
		System.out.println("      ---------       ");
		System.out.println(country.getName() + "["+player.getName()+"] lost a unit");
		System.out.println("      ---------       ");
		
		country.addUnits(-1);
		if(country.getUnits() <= 0) {
			System.out.println(player.getName()+" has lost " + country.getName() +
					"/n New owner is " + attacker.getName());
			System.out.println("      ---------       ");
			player.loseCountry(country);
			attacker.gainCountry(country);
			country.setOwner(attacker);
			attacker.moveUnits(attackingCountry, defendingCountry);
		}
	}
	public int[] sortFromHighest(int[]arr) {
		Arrays.sort(arr);
		int arrLength = arr.length;
		int[]desendingArray = new int[arrLength];
		for (int i = 0; i < desendingArray.length; i++) {
			desendingArray[i] = arr[arrLength-i-1];
		}
		return desendingArray;
		
	}
	public void simulateBattle() {
		System.out.println(attackingCountry.getName().toUpperCase() + " ATTACKING "+defendingCountry.getName().toUpperCase());
		//prompt attacker and defender to select amount of units
		System.out.print(attacker.getName() + " (attacker): ");
		numAttackers = attackingCountry.selectUnitAmount();
		
		System.out.print(defender.getName() + " (defender): ");
		numDefenders = defendingCountry.selectUnitAmount();
		
		int[] atkDices = rollDice(numAttackers);
		int[] defDices = rollDice(numDefenders);
		int defending[] = sortFromHighest(defDices);
		int attacking[] = sortFromHighest(atkDices);
		int minimumDice = Math.min(defending.length, attacking.length);
		
		for (int i = 0; i <minimumDice; i++) {
			System.out.println("      ---------       ");
			System.out.println(attacker.getName() +" roles: " + attacking[i]);
			System.out.println(defender.getName() +" roles: " + defending[i]);
			if(attackerWin(attacking[i], defending[i])) {
				countryLose(defendingCountry , attacker);
			}else {
				countryLose(attackingCountry , defender);
			}
		}
		
	}
	
	//getters and setters
	public Dice getDice() {
		return dice;
	}
	public void setDice(Dice dice) {
		this.dice = dice;
	}
	public Player getAttacker() {
		return attacker;
	}
	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}
	public Player getDefender() {
		return defender;
	}
	public void setDefender(Player defender) {
		this.defender = defender;
	}
	public Country getAttackingCountry() {
		return attackingCountry;
	}
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}
	public Country getDefendingCountry() {
		return defendingCountry;
	}
	public void setDefendingCountry(Country defendingCountry) {
		this.defendingCountry = defendingCountry;
	}
	public int getNumAttackers() {
		return numAttackers;
	}
	public void setNumAttackers(int numAttackers) {
		this.numAttackers = numAttackers;
	}
	public int getNumDefenders() {
		return numDefenders;
	}
	public void setNumDefenders(int numDefenders) {
		this.numDefenders = numDefenders;
	}
	
}
