import java.util.*;

// Public first 
// Private second
public class Country {
	private String name;
	private int countryID;
	private Player owner;
	private int numUnits;
	private ArrayList<Integer> neighbours;

	Country(int id, ArrayList<Integer> newNeighbours, String, newName){
		// Set neighbours
		neighbours = newNeighbours;
		// Set Name 
		name = newName;
		// Set ID
		countryID = id;
	}

	public String getName(){
		return name;
	}
	public setName(String newName){
		name = newName;
	}

	public void addUnits(int units) {
		numUnits = numUnits + units;
	}

	public void setUnits(int units){
		numUnits = units;
	}

	public int getUnits(){
		return numUnits;
	}

	public void setOwner(Player player) {
		owner = player;
	}

	public Player getOwner(){
		return owner;
	}

	public void setCountryID(int id) {
		countryID = id;
	}

	public int getCountryID(){
		return countryID;
	}

	public boolean isOwner(Player player) {
		return Player.equals(player);
	}

	public boolean isNeighbour(Country country) {
		boolean isANeighbour = false;
		// Check if country.countryID is in the list of Map.countries.get(countryID);
		// Check if possibleNeighbour is isn the list countryNeighbours
		ArrayList<Integer> countryNeighbours = Map.countries.get(countryID);
		int possibleNeighbour = country.countryID;

		if(Arrays.asList(countryNeighbours).contains(possibleNeighbour)){
			// This is a neighbour
			return true;
		} else {
			return false;
		}

		return Arrays.asList(countryNeighbours).contains(possibleNeighbour);
	}


	public int selectUnitAmount(){
		System.out.println("How many units do you want to send? ");
		Scanner kb = new Scanner(System.in);
		int input = kb.nextInt();
		if (input <= numUnits){
			return input;
		} else {
			System.out.println("Not enough unit.");
			return 0;
		}
	}

	public String toString(){
		// Name
		// Owner
		// Units
		String numUnitsString = Integer.toString(numUnits);
		String countryIDString = Integer.toString(countryID);
		return "Name: " + name + "(" + countryIDString +  ")" + "\n" + "Owner: " + owner.name + "\n" + "Units: " + numUnitsString;
	}

}