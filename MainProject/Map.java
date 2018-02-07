import java.util.*;

public class Map {

	// private int[]countries = {};
	// private HashMap<Integer, ArrayList<Integer>> countries = new HashMap<Integer, ArrayList<Integer>>();
	private ArrayList<Country> countries;


	public ArrayList<Country> getCountries(){
		return countries;
	}
	

	public void setUnitsTo(Country country, int numUnits) {
		country.setUnits(numUnits);
	}

	public void setOwnerTo(Country country, Player player) {
		country.getOwner() = player;
	}

	public void initMap() {

	}

	Map(){		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2,4));
		buildMap(1, neighbours, "RUSSIA");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,5,6,3));
		buildMap(2, neighbours, "USA");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,6,7));
		buildMap(3, neighbours, "CANADA");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,2,5));
		buildMap(4, neighbours, "CHINA");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,2,6));
		buildMap(5, neighbours, "SWEDEN");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,5,7));
		buildMap(6, neighbours, "JAPAN");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,3,6));
		buildMap(7, neighbours, "AUSTRALIA");
	}

	private void buildMap(int id, ArrayList<Integer> neighbours, String name) {
		// countries.put(key, value);
		Country country = new Country(id, neighbours, name);
		countries.add(country);
	}

	public void printMap(){
		System.out.println(
	  "	 \\-\\--------___                                       | \n"
	+ "	  |        |   ----------------------------------------/ \n"
	+ "	 /         |                 \\                       /  \n"
	+ "	 |     1    \\        2        |           3          |  \n"
	+ "	/   RUISSIA  |       USA      \\        CANADA        |  \n"
	+ "	|----__      |                |                       |  \n"
	+ "	|      ______|                \\                      |  \n"
	+ "	|            -----------       \\                     | \n"
	+ "	 |             |       |        |                     |\n"
	+ "	 |     4       |   5   |        |                     |\\n"
	+ "	  |  CHINA     | SWEDEN --------| _____________________ /\n"
	+ "	  |_           |       |         |                   /\n"
	+ "	    \\         |       |         |                  /\n"
	+ "	     ----      |       |    6    \\        7       /\n"
	+ "	         ---_  | ______|  JAPAN  |     AUSTRALIA  \\n"
	+ "	             `---  \\            |     __________  \\n"
	+ "	                    \\\\      /--------         |  \\n"
	+ "	                        \\    /                   \\  |\n"
	+ "	                         \\  |                     '\\'\n"
	+ "	                          --- \n");
	}

}
