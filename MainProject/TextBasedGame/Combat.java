import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
/**
 * Simulates a Combat between 2 country
 */
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
	/**checks to see if the attacking number is higher
	 * @param attackerDice the attacker's number value
	 * @param defenderDice the defender's number value
	 */
	public boolean attackerWin(int attackerDice, int defenderDice) {
		boolean attackWin = false;
		if(attackerDice > defenderDice) {
			attackWin = true;
		}
		return attackWin;
	}
	/**
	 * Roll's a number of dice
	 * @param numDice how much dice to roll
	 */
	public int[] rollDice(int numDice) {
		int dices[] = dice.rollDices(numDice);
		return dices;
		
	}
	/**
	 * decrease one unit from a country. checks if the country has any units left
	 * @param country the country that will lose (1) unit
	 * @param attacker the player who is won the attack
	 */
	public void countryLose(Country country , Player attacker) {
		Player player = country.getOwner();
		System.out.println("-[===> ---------------------- <===]-");
		System.out.println(country.getName() + "["+player.getName()+"] lost a unit");
		System.out.println("-[===> ---------------------- <===]-");
		
		country.addUnits(-1);
		if(country.getUnits() <= 0) {
			System.out.println(player.getName()+" has lost " + country.getName() +
					"/n New owner is " + attacker.getName());
			
			System.out.println("-[===> ---------------------- <===]-");
			player.loseCountry(country);
			attacker.gainCountry(country);
			country.setOwner(attacker);
			attacker.moveUnits(attackingCountry, defendingCountry);
		}
	}
	/**
	 * sorts an array from highest to lowest
	 * @param arr the unsorted array
	 */
	public int[] sortFromHighest(int[]arr) {
		Arrays.sort(arr);
		int arrLength = arr.length;
		int[]desendingArray = new int[arrLength];
		for (int i = 0; i < desendingArray.length; i++) {
			desendingArray[i] = arr[arrLength-i-1];
		}
		return desendingArray;
		
	}
	/**
	 * starts the battle between 2 countries
	 */
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
			System.out.println("-[===> ---------------------- <===]-");
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
	/**
	 * returns the dice
	 */
	public Dice getDice() {
		return dice;
	}
	/**
	 * sets the dice
	 * @param dice the dice to set
	 */
	public void setDice(Dice dice) {
		this.dice = dice;
	}
	/**
	 * returns the attacker
	 */
	public Player getAttacker() {
		return attacker;
	}
	/**
	 * sets who is the attacker
	 * @param the player who will become the attacker
	 */
	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}
	/**
	 * returns the defender
	 */
	public Player getDefender() {
		return defender;
	}
	/**
	 * sets who is the defender
	 * @param defender the player who will become the defender
	 */
	public void setDefender(Player defender) {
		this.defender = defender;
	}
	/**
	 * return the country that is attacking
	 */
	public Country getAttackingCountry() {
		return attackingCountry;
	}
	/**
	 * sets the attacking country
	 * @param attackingCountry the country that is attacking
	 */
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}
	/**
	 * returns the country that is defending
	 */
	public Country getDefendingCountry() {
		return defendingCountry;
	}
	/**
	 * sets the defending country
	 * @param defendingCountry the country that is defending
	 */
	public void setDefendingCountry(Country defendingCountry) {
		this.defendingCountry = defendingCountry;
	}
	/**
	 * returns the amount of attackers
	 */
	public int getNumAttackers() {
		return numAttackers;
	}
	/**
	 * sets the amount of attackers
	 * @param numAttackers how much units are attacking
	 */
	public void setNumAttackers(int numAttackers) {
		this.numAttackers = numAttackers;
	}
	/**
	 * returns the number of defenders
	 */
	public int getNumDefenders() {
		return numDefenders;
	}
	/**
	 * sets the amount of defenders
	 * @param numDefenders how much units are attacking
	 */
	public void setNumDefenders(int numDefenders) {
		this.numDefenders = numDefenders;
	}
	
}
