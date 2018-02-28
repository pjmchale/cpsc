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


public class Map extends Application {

	private ArrayList<Country> countries;
	private Pane root = new Pane();

	public getPane() {
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

		ArrayList<Integer> neighbours = new ArrayList<Integer>(Arrays.asList(2));
		ArrayList<Integer> titleCordinates = new ArrayList<Integer>(Arrays.asList(135,175));
		buildMap(1, neighbours, "Canada", titleCordinates);

		neighbours = new ArrayList<Integer>(Arrays.asList(1,3));
		titleCordinates = new ArrayList<Integer>(Arrays.asList(165,240));
		buildMap(2, neighbours, "USA", titleCordinates);

		neighbours = new ArrayList<Integer>(Arrays.asList(2,4));
		titleCordinates = new ArrayList<Integer>(Arrays.asList(115,310));
		buildMap(3, neighbours, "Mexico", titleCordinates);

		neighbours = new ArrayList<Integer>(Arrays.asList(3,5,6));
		titleCordinates = new ArrayList<Integer>(Arrays.asList(195,350));
		buildMap(4, neighbours, "Peru", titleCordinates);

		neighbours = new ArrayList<Integer>(Arrays.asList(4,6));
		titleCordinates = new ArrayList<Integer>(Arrays.asList(305,370));
		buildMap(5, neighbours, "Brazil", titleCordinates);

		neighbours = new ArrayList<Integer>(Arrays.asList(4,5));
		titleCordinates = new ArrayList<Integer>(Arrays.asList(190,450));
		buildMap(6, neighbours, "Argentina", titleCordinates);
	}

	/**
	 * Used to build the map
	 * @param a int to set the id of the country
	 * @param an ArrayList of type Interger, with the id's of neighbouring countries
	 * @param a String to be the name of the country
	*/
	private void buildMap(int id, ArrayList<Integer> neighbours, String name, ArrayList<Integer> titleCordinates) {
		Country country = new Country(id, neighbours, name, root, titleCordinates);
		if (countries == null){
			countries = new ArrayList<Country>(Arrays.asList(country));
			
		} else {
			countries.add(country);
		}
	}

	public Country showNeighbours(Country country) {
		for (int i:countries){
			i.setClickable(country.isNeighbour(i));
		}
	}

	public ArrayList<Country> getNeighbours(Country country) {
		ArrayList<Country> output = new ArrayList<Country>();
		for (int i:countries){
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
