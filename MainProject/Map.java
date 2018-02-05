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
		country.owner = player;
	}

	public void initMap() {

	}

	Map(){		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(11,13,41));
		buildMap(11, neighbours, "RUSSIA");

		neighbours = new ArrayList<Integer>(Arrays.asList(11,13,15,16));
		buildMap(12, neighbours, "USA");

		neighbours = new ArrayList<Integer>(Arrays.asList(11,12,15));
		buildMap(13, neighbours, "CANADA");

		neighbours = new ArrayList<Integer>(Arrays.asList(13,15,18));
		buildMap(14, neighbours, "CHINA");

		neighbours = new ArrayList<Integer>(Arrays.asList(12,13,14,16,18));
		buildMap(15, neighbours, "HONG KONG");

		neighbours = new ArrayList<Integer>(Arrays.asList(12,15,17,19));
		buildMap(16, neighbours, "JAPAN");

		neighbours = new ArrayList<Integer>(Arrays.asList(15,17,18,19));
		buildMap(16, neighbours, "AUSTRALIA");
		
		neighbours = new ArrayList<Integer>(Arrays.asList(15,16,18,19));
		buildMap(17, neighbours, "MEXICO");

		neighbours = new ArrayList<Integer>(Arrays.asList(14,15,17));
		buildMap(18, neighbours, "BRAZIL");

		neighbours = new ArrayList<Integer>(Arrays.asList(16,17));
		buildMap(19, neighbours, "AFRICA");
	}

	private void buildMap(int id, ArrayList<Integer> neighbours, String name) {
		// countries.put(key, value);
		Country name = new Country(id, neighbours, name);
		countries.add(name);
	}

	private void printMap(){
	+ "	 \\~\\|  ~~---___                                        | \n"
	+ "	  |        |   ----------------------------------------/ \n"
	+ "	 /         |                 \\                       /  \n"
	+ "	 |          \\                 |                      |  \n"
	+ "	/            |                \\                      |  \n"
	+ "	|----__      |                |                       |  \n"
	+ "	|      ______|                \\                      |  \n"
	+ "	|            -----------       \\                     | \n"
	+ "	',             |       |        |                     |\n"
	+ "	 |             |       |        |                     |\\n"
	+ "	 ',            |      -+--------_____________________/\n"
	+ "	  '_           |      |         |                   /\n"
	+ "	    \\         |      |         |                  /\n"
	+ "	     ~~~-      |      |          \\                /\n"
	+ "	         '-,_  | _____|          |               \\n"
	+ "	             `~'~  \\             |     ,~~~~-~~,  \\n"
	+ "	                    \\\\      /~~~`---`         |  \\n"
	+ "	                        \    /                   \  |\n"
	+ "	                         \  |                     '\'\n"
	+ "	                          `~' \n"
	}

}
