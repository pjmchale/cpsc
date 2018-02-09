import java.util.*;

public class Country {
	private String name = "";
	private int countryID;
	private Player owner;
	private int numUnits;
	private ArrayList<Integer> neighbours;

	/**
	 * Constructor to set the id, neighbours, and name
	*/
	Country(int id, ArrayList<Integer> newNeighbours, String newName){
		neighbours = newNeighbours;
		name = newName;
		countryID = id;
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
	}

	/**
	 * Used to get the owner of the country
	 * @return the owner
	*/
	public Player getOwner(){
		return owner;
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
	 * Used to ask user how many units they want to send
	 * @return int the amount of units that have chosen to send
	*/
	public int selectUnitAmount(){
		System.out.println("How many units do you want to send? ");
		Scanner kb = new Scanner(System.in);
		int input = kb.nextInt();
		if (input > numUnits){
			System.out.println("Not enough unit.");
			input = 0;
		}
		return input;
	}

	/**
	 * Used to get the countries stats and present them
	 * @return String of nicely formated information
	*/
	public String toString(){
		String numUnitsString = Integer.toString(numUnits);
		String countryIDString = Integer.toString(countryID);
		return "Name: " + name + "(" + countryIDString +  ")" + "\n" + "Owner: " + owner.getName() + "\n" + "Units: " + numUnitsString;
	}

}