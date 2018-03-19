package MapStage;

import java.util.*;
import Player.*;
public class Country {

	private String name = "";
	private int countryID;
	private Player owner;
	private int numUnits;
	private ArrayList<Integer> neighbours;
	private String ownerName;
	private int ownerID;
	private CountryGUI countryGUI;
	private boolean hasGUI;
	private String continent = "";
	private ArrayList<Country> continentCountries; 
	private int continentBonusAmount;

	/**
	 * Constructor to add the country image to the view, set the id, neighbours, name, ownerID, ownerName, amount of units, and pane
	 * @param The country ID
	 * @param The countrys neighbours
	 * @param The country's name
	 * @param Wether to build the GUI or not
	*/
	Country(int id, ArrayList<Integer> newNeighbours, String newName, boolean buildGUI, String newContinent, int newContinentBonusAmount){
		name = newName;
		numUnits = 0;
		ownerName = "OPEN";
		ownerID = 0;
		countryID = id;
		neighbours = newNeighbours;
		hasGUI = buildGUI;
		continent = newContinent;
		continentBonusAmount = newContinentBonusAmount;

		if (buildGUI){
			countryGUI = new CountryGUI(name, numUnits, ownerName, ownerID, this);
		}
	}

	/**
	 * Used to get the name of the country
	 * @return the name
	*/
	public String getName(){
		return name;
	}

	/**
	 * Used to set the name of the country
	 * @param a String to set the name
	*/
	public void setName(String newName){
		name = newName;
	}

	/**
	 * Used to add or remove units 
	 * @param an int to change the units by passing a positive or negative number
	*/
	public void addUnits(int units) {
		numUnits = numUnits + units;
	}

	/**
	 * Used to set the number of units
	 * @param an int to set the number of units
	*/
	public void setUnits(int units){
		numUnits = units;
		// Update the map here

	}

	/**
	 * Used to get the number of units in the country.
	 * @return the number of units
	*/
	public int getUnits(){
		return numUnits;
	}

	/**
	 * Used to set the owner of the country
	 * @param a Player to set the owner
	*/
	public void setOwner(Player player) {
		owner = player;
		ownerName = player.getName();
		ownerID = player.getId();
		player.gainCountry(this);
		if (hasGUI){
			countryGUI.updateOwnerVisual(ownerName, ownerID);
		}
		if(checkIfTheyOwnWholeContinenet()){
			// TELL SOMETHING HERE TO ADD THE BONUS AMOUNT
		}
	}

	/**
	 * Used to get the owner of the country
	 * @return the owner
	*/
	public Player getOwner(){
		return owner;
	}

	/**
	 * Used to get the owner of the country
	 * @return the owner
	*/
	public String getOwnerName(){
		return ownerName;
	}

	/**
	 * Used to get the owner of the country
	 * @return the owner
	*/
	public int getOwnerID(){
		return ownerID;
	}

	/**
	 * Used to set the id of the country
	 * @param an int to set the id
	*/
	public void setCountryID(int id) {
		countryID = id;
	}

	/**
	 * Used to get the id of the country
	 * @return the id
	*/
	public int getCountryID(){
		return countryID;
	}

	/**
	 * Used to get neighbour ID's
	 * @return the neighbour ID's
	*/
	public ArrayList<Integer> getNeighbours(){
		return neighbours;
	}

	/**
	 * Used to get continent
	 * @return the continent
	*/
	public String getContinent(){
		return continent;
	}


	public void setContinentCountries(ArrayList<Country> newCountries){
		continentCountries = newCountries;
	}

	public boolean checkIfTheyOwnWholeContinenet(){
		boolean output = true;

		for (Country i: continentCountries){
			output = output&&(owner == i.getOwner());
		}

		return output;
	}

	/**
	 * Used to check who the owner of the country is
	 * @param a Player to check if they are the owner
	 * @return a boolean if the player is the owner or not
	*/
	public boolean isOwner(Player player) {
		return owner == player;
	}

	/**
	 * Used to check if the country is a neighbour to another
	 * @param a Country to see if it is a neighbour to the current one
	 * @return a boolean if the country passed is a neighbour or not
	*/
	public boolean isNeighbour(Country country) {
		int possibleNeighbour = country.getCountryID();
		return neighbours.contains(possibleNeighbour);
	}

	/**
	 * Used to set the opactity of a country image
	 * @param a boolean to set the state
	*/
	public void setClickable(boolean state){
		if (hasGUI){
			if (state) {
				countryGUI.setImageOpacity(1);
			} else {
				countryGUI.setImageOpacity(0.1);
			}
		}
	}

	/**
	 * THIS METHOD IS NO LONGER NEEDED IN THE GUI VERSION OF THE GAME
	 * Used to ask user how many units they want to send
	 * @return int the amount of units that have chosen to send
	*/
	public int selectUnitAmount(){
		System.out.println("How many units do you want to send? ");
		Scanner kb = new Scanner(System.in);
		int input = kb.nextInt();
		return input;
	}

	/**
	 * THIS METHOD IS NO LONGER NEEDED IN THE GUI VERSION OF THE GAME
	 * Used to get the countries stats and present them
	 * @return String of nicely formated information
	*/
	public String toString(){
		String numUnitsString = Integer.toString(numUnits);
		String countryIDString = Integer.toString(countryID);
		return "Name: " + name + "(" + countryIDString +  ")" + "\n" + "Owner: " + owner.getName() + "\n" + "Units: " + numUnitsString;
	}

}