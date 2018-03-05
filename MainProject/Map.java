import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Map {

	private ArrayList<Country> countries;
	private Pane root = new Pane();

	/**
	 * Used to get the pane
	 * @return the root pane displaying the maps
	*/
	public Pane getPane() {
		return root;
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

		// North America
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2,22));
		buildMap(1, neighbours, "Alaska");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		buildMap(2, neighbours, "Canada");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,9));
		buildMap(3, neighbours, "Green Land");

		neighbours = new ArrayList<Integer>(Arrays.asList(2,5));
		buildMap(4, neighbours, "USA");

		neighbours = new ArrayList<Integer>(Arrays.asList(4,6));
		buildMap(5, neighbours, "Mexico");
		
		// South America
		neighbours = new ArrayList<Integer>(Arrays.asList(5,7,8));
		buildMap(6, neighbours, "Peru");

		neighbours = new ArrayList<Integer>(Arrays.asList(6,8,13));
		buildMap(7, neighbours, "Brazil");

		neighbours = new ArrayList<Integer>(Arrays.asList(6,7));
		buildMap(8, neighbours, "Argentina");

		// Europe
		neighbours = new ArrayList<Integer>(Arrays.asList(3,10));
		buildMap(9, neighbours, "Iceland");

		neighbours = new ArrayList<Integer>(Arrays.asList(9,11,12));
		buildMap(10, neighbours, "Scandinavia");

		neighbours = new ArrayList<Integer>(Arrays.asList(10,12,13,16));
		buildMap(11, neighbours, "Western Europe");

		neighbours = new ArrayList<Integer>(Arrays.asList(10,11,16,17));
		buildMap(12, neighbours, "Eastern Europe");

		// Africa
		neighbours = new ArrayList<Integer>(Arrays.asList(11,14,16));
		buildMap(13, neighbours, "North Africa");

		neighbours = new ArrayList<Integer>(Arrays.asList(13,15,16));
		buildMap(14, neighbours, "Central Africa");
		
		neighbours = new ArrayList<Integer>(Arrays.asList(14));
		buildMap(15, neighbours, "South Africa");
			

		// Asia
		neighbours = new ArrayList<Integer>(Arrays.asList(11,12,13,14,17,18,19));
		buildMap(16, neighbours, "Middle East");

		neighbours = new ArrayList<Integer>(Arrays.asList(12,16,18,19));
		buildMap(17, neighbours, "Urul");

		neighbours = new ArrayList<Integer>(Arrays.asList(17,19,20,21));
		buildMap(18, neighbours, "Siberia");

		neighbours = new ArrayList<Integer>(Arrays.asList(16,17,18,20));
		buildMap(19, neighbours, "China");

		neighbours = new ArrayList<Integer>(Arrays.asList(18,19,21,22));
		buildMap(20, neighbours, "Mongolia");

		neighbours = new ArrayList<Integer>(Arrays.asList(18,20,22));
		buildMap(21, neighbours, "Yakutsk");

		neighbours = new ArrayList<Integer>(Arrays.asList(1,20,21));
		buildMap(22, neighbours, "Kamchatka");

		// Oceania 
		neighbours = new ArrayList<Integer>(Arrays.asList(19,24,25));
		buildMap(23, neighbours, "Indonesia");

		neighbours = new ArrayList<Integer>(Arrays.asList(23,25,26));
		buildMap(24, neighbours, "New Guinea");	

		neighbours = new ArrayList<Integer>(Arrays.asList(23,24,26));
		buildMap(25, neighbours, "Western Australia");

		neighbours = new ArrayList<Integer>(Arrays.asList(24,25));
		buildMap(26, neighbours, "Eastern Australia");		

		// Add the connections
		String imagePathConnections = "mapAssets/Connections.png";
		Image imageConnections = new Image(imagePathConnections);
		ImageView imageViewConnections = new ImageView();
		imageViewConnections.setImage(imageConnections);
		imageViewConnections.setPreserveRatio(true);
		imageViewConnections.setSmooth(true);
		imageViewConnections.setLayoutX(0);
		imageViewConnections.setLayoutY(0);
		imageViewConnections.toFront();

		root.getChildren().add(imageViewConnections);

		// Add the boarders
		String imagePathBoarders = "mapAssets/Boarders.png";
		Image imageBoarders = new Image(imagePathBoarders);
		ImageView imageViewBoarders = new ImageView();
		imageViewBoarders.setImage(imageBoarders);
		imageViewBoarders.setPreserveRatio(true);
		imageViewBoarders.setSmooth(true);
		imageViewBoarders.setLayoutX(0);
		imageViewBoarders.setLayoutY(0);
		imageViewBoarders.toFront();

		root.getChildren().add(imageViewBoarders);
	}

	/**
	 * Used to build the map
	 * @param a int to set the id of the country
	 * @param an ArrayList of type Interger, with the id's of neighbouring countries
	 * @param a String to be the name of the country
	*/
	private void buildMap(int id, ArrayList<Integer> neighbours, String name) {
		Country country = new Country(id, neighbours, name, root);
		if (countries == null){
			countries = new ArrayList<Country>(Arrays.asList(country));		
		} else {
			countries.add(country);
		}
	}

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


	/**
	 * THIS METHOD IS NO LONGER NEEDED IN THE GUI VERSION OF THE GAME
	 * Used to print the ASCII Map
	*/
	public void printMap(){
		System.out.print("");
	// 	System.out.println(
	//   "	  |-----------___                                       | \n"
	// + "	  |        |      \\------------------------------------/ \n"
	// + "	 /         |                 \\                       /  \n"
	// + "	 |  CANADA  \\                 |                      |  \n"
	// + "	/            |     SWEDEN     \\        RUSSIA        |  \n"
	// + "	|----__      |                |                      |  \n"
	// + "	|      |_____|                \\                      |  \n"
	// + "	|            -----------       \\                     | \n"
	// + "	 |             |       |        |                    |\n"
	// + "	 |             |       |        |                    |\n"
	// + "	  |    USA     | CHINA |--------|____________________|_\n"
	// + "	  |_           |       |         |                   /\n"
	// + "	    |          |       |         |     AUSTRALIA    /\n"
	// + "	    |____      |       |         |                 /\n"
	// + "	         |___  | ______|  JAPAN  |                /\n"
	// + "	             |__|   \\           _|______________/\n"
	// + "	                     \\        /                \n"
	// + "	                       \\     /                 \n"
	// + "	                        \\    |                   \n"
	// + "	                         |___| \n");
	}

}
