import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Map {
// extends Application {

	private ArrayList<Country> countries;
	private Pane root = new Pane();

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

		// ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2));
		// ArrayList<Integer> titleCordinates = new ArrayList<Integer>(Arrays.asList(135,175));
		// buildMap(1, neighbours, "Canada", titleCordinates);

		// neighbours = new ArrayList<Integer>(Arrays.asList(1,3));
		// titleCordinates = new ArrayList<Integer>(Arrays.asList(165,240));
		// buildMap(2, neighbours, "USA", titleCordinates);

		// neighbours = new ArrayList<Integer>(Arrays.asList(2,4));
		// titleCordinates = new ArrayList<Integer>(Arrays.asList(115,310));
		// buildMap(3, neighbours, "Mexico", titleCordinates);

		// neighbours = new ArrayList<Integer>(Arrays.asList(3,5,6));
		// titleCordinates = new ArrayList<Integer>(Arrays.asList(195,350));
		// buildMap(4, neighbours, "Peru", titleCordinates);

		// neighbours = new ArrayList<Integer>(Arrays.asList(4,6));
		// titleCordinates = new ArrayList<Integer>(Arrays.asList(305,370));
		// buildMap(5, neighbours, "Brazil", titleCordinates);

		// neighbours = new ArrayList<Integer>(Arrays.asList(4,5));
		// titleCordinates = new ArrayList<Integer>(Arrays.asList(190,450));
		// buildMap(6, neighbours, "Argentina", titleCordinates);

		// North America
		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2,22));
		buildMap(1, neighbours, "Alaska", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(1,3,4));
		buildMap(2, neighbours, "Canada", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(2,9));
		buildMap(3, neighbours, "Green Land", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(2,5));
		buildMap(4, neighbours, "USA", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(4,6));
		buildMap(5, neighbours, "Mexico", 0, "FREE", 0);
		
		// South America
		neighbours = new ArrayList<Integer>(Arrays.asList(5,7,8));
		buildMap(6, neighbours, "Peru", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(6,8,13));
		buildMap(7, neighbours, "Brazil", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(6,7));
		buildMap(8, neighbours, "Argentina", 0, "FREE", 0);

		// Europe
		neighbours = new ArrayList<Integer>(Arrays.asList(3,10));
		buildMap(9, neighbours, "Iceland", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(9,11,12));
		buildMap(10, neighbours, "Scandinavia", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(10,12,13,16));
		buildMap(11, neighbours, "Western Europe", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(10,11,16,17));
		buildMap(12, neighbours, "Eastern Europe", 0, "FREE", 0);

		// Africa
		neighbours = new ArrayList<Integer>(Arrays.asList(11,14,16));
		buildMap(13, neighbours, "North Africa", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(13,15,16));
		buildMap(14, neighbours, "Central Africa", 0, "FREE", 0);
		
		neighbours = new ArrayList<Integer>(Arrays.asList(14));
		buildMap(15, neighbours, "South Africa", 0, "FREE", 0);
			

		// Asia
		neighbours = new ArrayList<Integer>(Arrays.asList(11,12,13,14,17,18,19));
		buildMap(16, neighbours, "Middle East", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(12,16,18,19));
		buildMap(17, neighbours, "Urul", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(17,19,20,21));
		buildMap(18, neighbours, "Siberia", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(16,17,18,20));
		buildMap(19, neighbours, "China", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(18,19,21,22));
		buildMap(20, neighbours, "Mongolia", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(18,20,22));
		buildMap(21, neighbours, "Yakutsk", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(1,20,21));
		buildMap(22, neighbours, "Kamchatka", 0, "FREE", 0);

		// Oceania 
		neighbours = new ArrayList<Integer>(Arrays.asList(19,24,25));
		buildMap(23, neighbours, "Indonesia", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(23,25,26));
		buildMap(24, neighbours, "New Guinea", 0, "FREE", 0);	

		neighbours = new ArrayList<Integer>(Arrays.asList(23,24,26));
		buildMap(25, neighbours, "Western Australia", 0, "FREE", 0);

		neighbours = new ArrayList<Integer>(Arrays.asList(24,25));
		buildMap(26, neighbours, "Eastern Australia", 0, "FREE", 0);		

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
	private void buildMap(int id, ArrayList<Integer> neighbours, String name, int ownerID, String ownerName, int numUnits) {
		Country country = new Country(id, neighbours, name, ownerID, ownerName, numUnits, root);
		if (countries == null){
			countries = new ArrayList<Country>(Arrays.asList(country));
			
		} else {
			countries.add(country);
		}
	}

	public void showNeighbours(Country country) {
		for (Country i:countries){
			i.setClickable(country.isNeighbour(i));
		}
	}

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
