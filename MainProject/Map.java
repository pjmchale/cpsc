import java.util.*;

public class Map {

	private int[]countries = {};
	private HashMap<Integer, ArrayList<Integer>> countries = new HashMap<Integer, ArrayList<Integer>>();


	public void setUnitsTo(Country country, int numUnits) {
		country.setUnits(numUnits);
	}

	public void setOwnerTo(Country country, Player player) {

	}

	public void initMap() {
		

	}

	Map(){		
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(11,13,41));
		buildMap(11, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(11,13,15,16));
		buildMap(12, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(11,12,15));
		buildMap(13, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(13,15,18));
		buildMap(14, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(12,13,14,16,18));
		buildMap(15, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(12,15,17,19));
		buildMap(16, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(15,17,18,19));
		buildMap(16, neighbours);
		
		neighbours = new ArrayList<Integer>(Arrays.asList(15,16,18,19));
		buildMap(17, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(14,15,17));
		buildMap(18, neighbours);

		neighbours = new ArrayList<Integer>(Arrays.asList(16,17));
		buildMap(19, neighbours);
	}

	private void buildMap(int key, ArrayList<Integer> value) {
		countries.put(key, value);
	}

	public static void main(String[] args) {
		new Map();
	}
}
