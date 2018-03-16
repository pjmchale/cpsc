import java.util.*;

public class Map {

	private ArrayList<Country> countries;

	/**
	 * Used to get the countries
	 * @return the ArrayList of countries
	*/
	public ArrayList<Country> getCountries(){
		return countries;
	}
	
	/**
	 * Used to set the amount of units in a country
	 * @param a Country to set the number of units of
	 * @param the number of units
	*/
	public void setUnitsTo(Country country, int numUnits) {
		country.setUnits(numUnits);
	}

	/**
	 * Used to set the owner of a country
	 * @param a Country to set the owner of
	 * @param a Player to be the owner 
	*/
	public void setOwnerTo(Country country, Player player) {
		country.setOwner(player);
	}

	/**
	 * Initialize the map
	 * This is blank for now but may contain code to help build the gui
	*/
	public void initializeMap() {

	}

	/**
	 * Contructor used to build the map
	*/
	Map(){		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2,4));
		buildMap(1, neighbours, "CANADA");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,5,6,3));
		buildMap(2, neighbours, "SWEDEN");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,6,7));
		buildMap(3, neighbours, "RUSSIA");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,2,5));
		buildMap(4, neighbours, "USA");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,2,6));
		buildMap(5, neighbours, "CHINA");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,5,7));
		buildMap(6, neighbours, "JAPAN");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,3,6));
		buildMap(7, neighbours, "AUSTRALIA");
	}

	/**
	 * Used to build the map
	 * @param a int to set the id of the country
	 * @param an ArrayList of type Interger, with the id's of neighbouring countries
	 * @param a String to be the name of the country
	*/
	private void buildMap(int id, ArrayList<Integer> neighbours, String name) {
		Country country = new Country(id, neighbours, name);
		if (countries == null){
			countries = new ArrayList<Country>(Arrays.asList(country));
			
		} else {
			countries.add(country);
		}
	}

	/**
	 * Used to print the ASCII Map
	*/
	public void printMap(){
		System.out.println(
	  "	  |-----------___                                       | \n"
	+ "	  |        |      \\------------------------------------/ \n"
	+ "	 /         |                 \\                       /  \n"
	+ "	 |  CANADA  \\                 |                      |  \n"
	+ "	/            |     SWEDEN     \\        RUSSIA        |  \n"
	+ "	|----__      |                |                      |  \n"
	+ "	|      |_____|                \\                      |  \n"
	+ "	|            -----------       \\                     | \n"
	+ "	 |             |       |        |                    |\n"
	+ "	 |             |       |        |                    |\n"
	+ "	  |    USA     | CHINA |--------|____________________|_\n"
	+ "	  |_           |       |         |                   /\n"
	+ "	    |          |       |         |     AUSTRALIA    /\n"
	+ "	    |____      |       |         |                 /\n"
	+ "	         |___  | ______|  JAPAN  |                /\n"
	+ "	             |__|   \\           _|______________/\n"
	+ "	                     \\        /                \n"
	+ "	                       \\     /                 \n"
	+ "	                        \\    |                   \n"
	+ "	                         |___| \n");
	}

}
