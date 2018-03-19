package CombatEngine;
import Map.*;
import gameEngine.*;
import Player.*;
import java.lang.Math;
import java.util.Arrays;
import java.util.Scanner;
import javafx.scene.layout.Pane;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Label;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Simulates a Combat between 2 country
 */
public class Combat {
	private Dice dice;
	private Player attacker;
	private Player defender;
	private Country attackingCountry;
	private Country defendingCountry;
	private int numAttackers;
	private int numDefenders;
	private Pane pane;
	private CombatGUI gui;

	Combat(Country attackingCountry, Country defendingCountry) {
		dice = new Dice();
		this.attackingCountry = attackingCountry;
		this.defendingCountry = defendingCountry;
		attacker = attackingCountry.getOwner();
		defender = defendingCountry.getOwner();
		gui = new CombatGUI(this);
		// initPane();

	}

	/**
	 * checks to see if the attacking number is higher
	 * 
	 * @param attackerDice
	 *            the attacker's number value
	 * @param defenderDice
	 *            the defender's number value
	 */
	public boolean attackerWin(int attackerDice, int defenderDice) {
		boolean attackWin = false;
		if (attackerDice > defenderDice) {
			attackWin = true;
		}
		return attackWin;
	}

	/**
	 * Roll's a number of dice
	 * 
	 * @param numDice
	 *            how much dice to roll
	 */
	public int[] rollDice(int numDice) {
		int dices[] = dice.rollDices(numDice);
		return dices;

	}

	/**
	 * decrease one unit from a country. checks if the country has any units left
	 * 
	 * @param country
	 *            the country that will lose (1) unit
	 * @param attacker
	 *            the player who has won the attack
	 */
	public void countryLose(Country country, Player attacker) {
		Player player = country.getOwner();
		System.out.println("-[===> ---------------------- <===]-");
		System.out.println(country.getName() + "[" + player.getName() + "] lost a unit");
		System.out.println("-[===> ---------------------- <===]-");

		if (country.getUnits() > 0)
			country.addUnits(-1);

		/*
		 * if (country.getUnits() <= 0) { System.out.println( player.getName() +
		 * " has lost " + country.getName() + "/n New owner is " + attacker.getName());
		 * 
		 * System.out.println("-[===> ---------------------- <===]-");
		 * player.loseCountry(country); attacker.gainCountry(country);
		 * country.setOwner(attacker); attacker.moveUnits(attackingCountry,
		 * defendingCountry); }
		 */
	}

	/**
	 * sorts an array from highest to lowest
	 * 
	 * @param arr
	 *            the unsorted array
	 */
	public int[] sortFromHighest(int[] arr) {
		Arrays.sort(arr);
		int arrLength = arr.length;
		int[] desendingArray = new int[arrLength];
		for (int i = 0; i < desendingArray.length; i++) {
			desendingArray[i] = arr[arrLength - i - 1];
		}
		return desendingArray;

	}

	/**
	 * simulates the battle between 2 countries
	 */
	 public void simulateBattle() {
	 System.out.println(
	 attackingCountry.getName().toUpperCase() + " ATTACKING " +
	 defendingCountry.getName().toUpperCase());
	 // prompt attacker and defender to select amount of units
	 System.out.print(attacker.getName() + " (attacker): ");
	 numAttackers = attackingCountry.selectUnitAmount();
	
	 System.out.print(defender.getName() + " (defender): ");
	 numDefenders = defendingCountry.selectUnitAmount();
	 startBattle();
	
	 }

	/**
	 * starts the battle
	 */
	public void startBattle() {
		int[] atkDices = rollDice(numAttackers);
		int[] defDices = rollDice(numDefenders);
		int defending[] = sortFromHighest(defDices);
		int attacking[] = sortFromHighest(atkDices);
		int minimumDice = Math.min(defending.length, attacking.length);
		for (int i = 0; i < minimumDice; i++) {
			System.out.println("-[===> ---------------------- <===]-");
			System.out.println(attacker.getName() + " roles: " + attacking[i]);
			System.out.println(defender.getName() + " roles: " + defending[i]);
			if (attackerWin(attacking[i], defending[i])) {
				countryLose(defendingCountry, attacker);
			} else {
				countryLose(attackingCountry, defender);
			}
		}
		gui.displayBattle(attacking, defending);

	}
	public Pane getPane() {
		return gui.getPane();
	}


	// getters and setters
	/**
	 * returns the dice
	 */
	public Dice getDice() {
		return dice;
	}

	/**
	 * sets the dice
	 * 
	 * @param dice
	 *            the dice to set
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
	 * 
	 * @param the
	 *            player who will become the attacker
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
	 * 
	 * @param defender
	 *            the player who will become the defender
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
	 * 
	 * @param attackingCountry
	 *            the country that is attacking
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
	 * 
	 * @param defendingCountry
	 *            the country that is defending
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
	 * 
	 * @param numAttackers
	 *            how much units are attacking
	 */
	public void setNumAttackers(int numAttackers) {
		if(numAttackers > 0)this.numAttackers = numAttackers;
	}

	/**
	 * returns the number of defenders
	 */
	public int getNumDefenders() {
		return numDefenders;
	}

	/**
	 * sets the amount of defenders
	 * 
	 * @param numDefenders
	 *            how much units are attacking
	 */
	public void setNumDefenders(int numDefenders) {
		if(numDefenders > 0)this.numDefenders = numDefenders;
	}

}