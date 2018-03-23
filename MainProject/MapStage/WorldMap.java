package MapStage;

import java.util.*;
import PlayerPackage.*;

public class WorldMap {

	private ArrayList<Country> countries = new ArrayList<Country>(Arrays.asList());
	private ArrayList<Country> northAmerica = new ArrayList<Country>(Arrays.asList());
	private ArrayList<Country> southAmerica = new ArrayList<Country>(Arrays.asList());
	private ArrayList<Country> africa = new ArrayList<Country>(Arrays.asList());
	private ArrayList<Country> europe = new ArrayList<Country>(Arrays.asList());
	private ArrayList<Country> oceania = new ArrayList<Country>(Arrays.asList());
	private ArrayList<Country> asia = new ArrayList<Country>(Arrays.asList());
	private boolean buildGUI = true;
	private String currentContinent = "";
	/**
	 * Contructor used to build the map
	*/
	public WorldMap(boolean newBuildGUI){
		buildGUI = newBuildGUI;
		currentContinent = "North America";
		// North America
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2,22));
		buildMap(1, neighbours, "Alaska", 70, 170);

		neighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		buildMap(2, neighbours, "Canada", 170, 200);

		neighbours = new ArrayList<Integer>(Arrays.asList(2,9));
		buildMap(3, neighbours, "Green Land", 350, 125);

		neighbours = new ArrayList<Integer>(Arrays.asList(2,5));
		buildMap(4, neighbours, "USA", 190, 250);

		neighbours = new ArrayList<Integer>(Arrays.asList(4,6));
		buildMap(5, neighbours, "Mexico", 202, 312);
		
		currentContinent = "South America";
		// South America
		neighbours = new ArrayList<Integer>(Arrays.asList(5,7,8));
		buildMap(6, neighbours, "Peru", 260, 360);

		neighbours = new ArrayList<Integer>(Arrays.asList(6,8,13));
		buildMap(7, neighbours, "Brazil", 325, 390);

		neighbours = new ArrayList<Integer>(Arrays.asList(6,7));
		buildMap(8, neighbours, "Argentina", 283, 454);

		currentContinent = "Europe";
		// Europe
		neighbours = new ArrayList<Integer>(Arrays.asList(3,10));
		buildMap(9, neighbours, "Iceland", 452, 159);

		neighbours = new ArrayList<Integer>(Arrays.asList(9,11,12));
		buildMap(10, neighbours, "Scandinavia", 496, 168);

		neighbours = new ArrayList<Integer>(Arrays.asList(10,12,13,16));
		buildMap(11, neighbours, "Western Europe", 496, 228);

		neighbours = new ArrayList<Integer>(Arrays.asList(10,11,16,17));
		buildMap(12, neighbours, "Eastern Europe", 565, 200);

		currentContinent = "Africa";
		// Africa
		neighbours = new ArrayList<Integer>(Arrays.asList(11,14,16));
		buildMap(13, neighbours, "North Africa", 460, 350);

		neighbours = new ArrayList<Integer>(Arrays.asList(13,15,16));
		buildMap(14, neighbours, "Central Africa", 525, 355);
		
		neighbours = new ArrayList<Integer>(Arrays.asList(14));
		buildMap(15, neighbours, "South Africa", 510, 425);	

		currentContinent = "Asia";
		// Asia
		neighbours = new ArrayList<Integer>(Arrays.asList(11,12,13,14,17,18,19));
		buildMap(16, neighbours, "Middle East", 600, 265);

		neighbours = new ArrayList<Integer>(Arrays.asList(12,16,18,19));
		buildMap(17, neighbours, "Urul", 620, 180);

		neighbours = new ArrayList<Integer>(Arrays.asList(17,19,20,21));
		buildMap(18, neighbours, "Siberia", 687, 170);

		neighbours = new ArrayList<Integer>(Arrays.asList(16,17,18,20));
		buildMap(19, neighbours, "China", 705, 275);

		neighbours = new ArrayList<Integer>(Arrays.asList(18,19,21,22));
		buildMap(20, neighbours, "Mongolia", 704, 220);

		neighbours = new ArrayList<Integer>(Arrays.asList(18,20,22));
		buildMap(21, neighbours, "Yakutsk", 765, 161);

		neighbours = new ArrayList<Integer>(Arrays.asList(1,20,21));
		buildMap(22, neighbours, "Kamchatka", 830, 170);

		currentContinent = "Oceania";
		// Oceania 
		neighbours = new ArrayList<Integer>(Arrays.asList(19,24,25));
		buildMap(23, neighbours, "Indonesia", 725, 370);

		neighbours = new ArrayList<Integer>(Arrays.asList(23,25,26));
		buildMap(24, neighbours, "New Guinea", 788, 368);	

		neighbours = new ArrayList<Integer>(Arrays.asList(23,24,26));
		buildMap(25, neighbours, "Western Australia", 768, 424);

		neighbours = new ArrayList<Integer>(Arrays.asList(24,25));
		buildMap(26, neighbours, "Eastern Australia", 815, 430);	

		// assignContinenets();
	}

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
		if (numUnits >= 0){
			country.setUnits(numUnits);
		}	
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
	 * Used to build the map
	 * @param a int to set the id of the country
	 * @param an ArrayList of type Interger, with the id's of neighbouring countries
	 * @param a String to be the name of the country
	*/
	private void buildMap(int id, ArrayList<Integer> neighbours, String name, int centerX, int centerY) {
		Country country = new Country(id, neighbours, name, buildGUI, centerX, centerY);
		countries.add(country);
		
		switch(currentContinent){
			case "North America":
				northAmerica.add(country);
			break;
			case "South America": 
				southAmerica.add(country);
				
			break;
			case "Europe": 
				europe.add(country);
				
			break;
			case "Africa": 
				africa.add(country);
			break;
			case "Asia": 
				asia.add(country);
				
			break;
			case "Oceania": 
				oceania.add(country);
			break;
		}
	}

	/**
	 * Used get a countrys neighbours
	 * @param a Country to show its neighbours 
	 * @return an ArrayList of type country of the neighbours
	*/
	public ArrayList<Country> getNeighbours(Country country) {
		ArrayList<Country> output = new ArrayList<Country>();
		for (Country i:countries){
			if (country.isNeighbour(i)){
				output.add(i);
			}
		}
		return output;
	}

	// public ArrayList<Country> getContinentCountries(String continent){
	// 	ArrayList<Country> output;

	// 	switch(continent){
	// 		case "North America":
	// 			output = northAmerica;
	// 		break;
	// 		case "South America": 
	// 			output = southAmerica;
	// 		break;
	// 		case "Europe": 
	// 			output = europe;
	// 		break;
	// 		case "Africa": 
	// 			output = africa;
	// 		break;
	// 		case "Asia": 
	// 			output = asia;
	// 		break;
	// 		case "Oceania": 
	// 			output = oceania;
	// 		break;
	// 		default:
	// 			output = new ArrayList<Country>();
	// 	}

	// 	return output;
	// }

	// public void assignContinenets(){
	// 	for (Country i: countries){
	// 		i.setContinentCountries(getContinentCountries(i.getContinent()));
	// 	}
	// }

	public int getContinentBonus(Player playerToCheckBonus){
		int bonusTotal = 0;

		boolean ownsNorthAmerica = true;
		for (Country x: northAmerica){
			ownsNorthAmerica = ownsNorthAmerica&&(x.getOwner() == playerToCheckBonus);
		}
		if (ownsNorthAmerica){
			bonusTotal += 5;
		}

		boolean ownsSouthAmerica = true;
		for (Country x: southAmerica){
			ownsSouthAmerica = ownsSouthAmerica&&(x.getOwner() == playerToCheckBonus);
		}
		if (ownsSouthAmerica){
			bonusTotal += 2;
		}

		boolean ownsEurope = true;
		for (Country x: europe){
			ownsEurope = ownsEurope&&(x.getOwner() == playerToCheckBonus);
		}
		if (ownsEurope){
			bonusTotal += 5;
		}

		boolean ownsAfrica = true;
		for (Country x: africa){
			ownsAfrica = ownsAfrica&&(x.getOwner() == playerToCheckBonus);
		}
		if (ownsAfrica){
			bonusTotal += 3;
		}

		boolean ownsAsia = true;
		for (Country x: asia){
			ownsAsia = ownsAsia&&(x.getOwner() == playerToCheckBonus);
		}
		if (ownsAsia){
			bonusTotal += 7;
		}

		boolean ownsOceania = true;
		for (Country x: oceania){
			ownsOceania = ownsOceania&&(x.getOwner() == playerToCheckBonus);
		}
		if (ownsOceania){
			bonusTotal += 7;
		}

		return bonusTotal;
	}

	// public boolean checkIfTheyOwnWholeContinenet(Country country){
	// 	boolean output = true;
	// 	Player owner = country.getOwner();
	// 	String contientName = country.getContinent();
		
	// 	for (Country i: getContinentCountries(contientName)){
	// 		output = output&&(owner == i.getOwner());
	// 	}

	// 	return output;
	// }


	/**
	 * Used to highlight only a country's neighbours
	 * @param a Country to show its neighbours 
	*/
	public void showNeighbours(Country country) {
		
		for (Country i:countries){
			i.setClickable(country.isNeighbour(i));
		}
	}

	/**
	 * Used to highlight only a country's neighbours
	 * @param a Country to show its neighbours 
	*/
	public void showNeighboursOwner(Country country, Player owner) {
		for (Country i:countries){
			i.setClickable(i.isOwner(owner) && country.isNeighbour(i));
		}
	}

	/**
	 * Used to reset the highlighted country's
	*/
	public void hideNeighbours() {
		for (Country i:countries){
			i.setClickable(true);
		}
	}
	
	/**
	 * THIS METHOD IS NO LONGER NEEDED IN THE GUI VERSION OF THE GAME
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
